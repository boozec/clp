package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.Arrays;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import codegen.Label;

/**
 * Node for the `expr` statement of the grammar.
 */
public class ExprNode implements Node {

    private final AtomNode atom;
    private final Node compOp;
    private final String op;
    private final ArrayList<Node> exprs;
    private final ArrayList<Node> trailers;

    // VERY scatchy
    private int paramNumber;
    private String funL;

    public ExprNode(Node atom, Node compOp, ArrayList<Node> exprs, String op, ArrayList<Node> trailers) {
        this.atom = (AtomNode) atom;
        this.compOp = compOp;
        this.exprs = exprs;
        this.op = op;
        this.trailers = trailers;
    }

    /**
     * Returns the i-th expressions of `exprs` field. If the index is greater or
     * equals than the size return `null`.
     */
    public Node getExpr(int i) {
        if (i >= this.exprs.size()) {
            return null;
        }

        return this.exprs.get(i);
    }

    /**
     * Returns the identifier of the `AtomNode` if it's not `null`, otherwise
     * returns `null`.
     */
    public String getId() {
        if (atom != null) {
            return ((AtomNode) this.atom).getId();
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        if(op != null) {
            System.out.println("OP" + op);
        }
        // check if the atom is a function
        if (atom != null && !trailers.isEmpty()) {

            // check if the atom is not a built-in function
            if (!Arrays.asList(bif).contains(atom.getId())) {

                errors.addAll(atom.checkSemantics(ST, _nesting));

                TrailerNode trailer = (TrailerNode) trailers.get(0);
                String funName = atom.getId();

                // TODO: it isnt a function, it could be a variable
                STentry fun = ST.lookup(funName);

                if (fun != null && !(fun.getType() instanceof ImportType)) {
                    if (!(fun.getType() instanceof FunctionType)) {
                        System.out.println("ERROR: not a function type");
                        for (var t : trailers) {
                            errors.addAll(t.checkSemantics(ST, _nesting));
                        }

                    } else {
                        FunctionType ft = (FunctionType) fun.getType();
                        paramNumber = ft.getParamNumber();
                        funL = ft.getLabel();
                        int argNumber = trailer.getArgumentNumber();

                        if (paramNumber != argNumber) {
                            errors.add(new SemanticError(funName + "() takes " + String.valueOf(paramNumber)
                                    + " positional arguments but " + String.valueOf(argNumber) + " were given."));
                        }
                        for (var t : trailers) {
                            errors.addAll(t.checkSemantics(ST, _nesting));
                        }
                    }
                }
            } else {
                for (var trailer : trailers) {
                    errors.addAll(trailer.checkSemantics(ST, _nesting));
                }
            }
        } else if (atom != null) {
            errors.addAll(atom.checkSemantics(ST, _nesting));
        }

        if (compOp != null) {
            errors.addAll(compOp.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    // FIXME: type for the expr
    @Override
    public Type typeCheck() {
        if (this.atom != null) {
            return this.atom.typeCheck();
        }

        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        
        // check function call
        if (atom != null && !trailers.isEmpty()) {
            TrailerNode trailer = (TrailerNode) trailers.get(0);
            String trailerS = trailer.codeGeneration();
            // check if the atom is a built-in function
            if (Arrays.asList(bif).contains(atom.getId())) {
                // TODO: AGGIUNGERE COMMENTI PER SPIEGARE GESTIONE PRINT
                return trailerS;
            }

            return "pushr FP\n" +
                    "move SP FP\n" +
                    "addi FP 1\n" +
                    "move AL T1\n" +
                    "pushr T1\n" + // slide 62, non implementiamo il for loop perché non supportiamo funzioni
                                   // annidate
                    trailerS +
                    "move FP AL\n" +
                    "subi AL 1\n" +
                    "jsub " + funL + "\n";
        }

        // check operation
        if (op != null) {
            switch (op) {
                case "+":
                case "-":
                case "*":
                case "/": // divisione intera
                    return intOpCodeGen(exprs.get(0), exprs.get(1), op);
                case "%":
                    return modCodeGen(exprs.get(0), exprs.get(1));
                case "and":
                    return andCodeGen(exprs.get(0), exprs.get(1));
                case "or":
                    return orCodeGen(exprs.get(0), exprs.get(1));
                case "not":
                    return notCodeGen(exprs.get(0));
                default:
                    return "Error: operation " + op + " not supported\n";
            }
        }

        // check comp operation
        if (compOp != null) {
            CompOpNode cmpOp = (CompOpNode) compOp;
            String op = cmpOp.getOp();
            switch (op) {
                case "==":
                    return eqCodeGen(exprs.get(0), exprs.get(1));
                case "!=":
                    return neqCodeGen(exprs.get(0), exprs.get(1));
                case "<":
                    return lsCodeGen(exprs.get(0), exprs.get(1));
                case "<=":
                    return leqCodeGen(exprs.get(0), exprs.get(1));
                case ">":
                    return gtCodeGen(exprs.get(0), exprs.get(1));
                case ">=":
                    return gteCodeGen(exprs.get(0), exprs.get(1));
                default:
                    return "Error: operation " + op + " not supported\n";
            }
        }

        if (atom != null) {
            return atom.codeGeneration();
        }

        if (!exprs.isEmpty()) {
            String str = "";
            for (var expr : exprs) {
                str += expr.codeGeneration();
            }
            return str;
        }

        return "Error: cannot recognize the expression\n";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Expr\n";

        prefix += "  ";
        if (atom != null) {
            str += atom.toPrint(prefix);
        }
        if (compOp != null) {
            str += compOp.toPrint(prefix);
        }

        if (exprs != null) {
            for (var expr : exprs) {
                str += expr.toPrint(prefix);
            }
        }

        if (trailers != null) {
            for (var trailer : trailers) {
                str += trailer.toPrint(prefix);
            }
        }

        if (op != null) {
            str += prefix + "Op(" + op + ")\n";
        }

        return str;
    }

    public String intOpCodeGen(Node leftE, Node rightE, String op) {
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        String ops;
        switch (op) {
            case "+":
                ops = "add";
                break;
            case "-":
                ops = "sub";
                break;
            case "*":
                ops = "mul";
                break;
            case "/":
                ops = "div"; // TODO: controllare che sia divisione intera
                break;
            default:
                ops = "Error: cannot manage op " + op;
        }
        return ls + "pushr A0\n" + rs + "popr T1\n" + ops + " T1 A0\npopr A0\n";
    }

    public String modCodeGen(Node leftE, Node rightE) {
        // String ls = leftE.codeGeneration();
        // String rs = rightE.codeGeneration();

        return "TODO: operazione modulo";
    }

    // ATTENZIONE: per le operazioni booleane assumiammo che False sia sempre 0 e
    // che True sia sempre 1

    // Ottimizzazione: se il valore a sinistra è falso (0), l'espressione è
    // sicuramente falsa, altrimenti sarà il valore della espressione a destra
    public String andCodeGen(Node leftE, Node rightE) {
        String endl = Label.newBasic("endAnd");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "storei T1 0\n" +
                "beq A0 T1 " + endl + "\n" +
                rs +
                endl + ":\n";
    }

    // Ottimizzazione: se il valore a sinistra è vera (1), l'espressione è
    // sicuramente vera, altrimenti sarà il valore della espressione a destra
    public String orCodeGen(Node leftE, Node rightE) {
        String endl = Label.newBasic("endOr");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "storei T1 1\n" +
                "beq A0 T1 " + endl + "\n" +
                rs +
                endl + ":\n";
    }

    // Viene usata l'operazione SUB
    // not True = 1 - 1 = 0 = False
    // not False = 1 - 0 = 1 = True
    // Stessa tabella di verità dell'NOT
    public String notCodeGen(Node expr) {
        String exprs = expr.codeGeneration();
        return exprs +
                "storei T1 1\n" +
                "sub T1 A0\n" +
                "popr A0\n";
    }

    public String eqCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "pushr A0\n" +
                rs +
                "popr T1\n" +
                "beq T1 A0 " + truel + "\n" +
                "storei A0 0\n" +
                "b " + endl + "\n" +
                truel + ":\n" +
                "storei A0 1\n" +
                endl + ":\n";
    }

    public String neqCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "pushr A0\n" +
                rs +
                "popr T1\n" +
                "beq T1 A0 " + truel + "\n" +
                "storei A0 1\n" +
                "b " + endl + "\n" + // storei A0 1 instead of storei A0 0
                truel + ":\n" +
                "storei A0 0\n" + // storei A0 0 instead of storei A0 1
                endl + ":\n";
    }

    public String lsCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String truel2 = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "pushr A0\n" +
                rs +
                "popr T1\n" +
                "bleq T1 A0 " + truel + "\n" +
                "storei A0 0\n" +
                "b " + endl + "\n" +
                truel + ":\n" +
                "beq T1 A0 " + truel2 + "\n" +
                "storei A0 1\n" +
                "b " + endl + "\n" +
                truel2 + ":\n" +
                "storei A0 0\n" +
                "b " + endl + "\n" +
                endl + ":\n";
    }

    public String leqCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "pushr A0\n" +
                rs +
                "popr T1\n" +
                "bleq T1 A0 " + truel + "\n" +
                "storei A0 0\n" +
                "b " + endl + "\n" +
                truel + ":\n" +
                "storei A0 1\n" +
                endl + ":\n";
    }

    public String gtCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "pushr A0\n" +
                rs +
                "popr T1\n" +
                "bleq A0 T1 " + truel + "\n" + // inverto A0 e T1 rispetto a leq
                "storei A0 0\n" +
                "b " + endl + "\n" +
                truel + ":\n" +
                "storei A0 1\n" +
                endl + ":\n";
    }

    public String gteCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String truel2 = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls +
                "pushr A0\n" +
                rs +
                "popr T1\n" +
                "bleq T1 A0 " + truel + "\n" +
                "storei A0 1\n" +
                "b " + endl + "\n" +
                truel + ":\n" +
                "beq T1 A0 " + truel2 + "\n" +
                "storei A0 0\n" +
                "b " + endl + "\n" +
                truel2 + ":\n" +
                "storei A0 1\n" +
                "b " + endl + "\n" +
                endl + ":\n";
    }

}
