package ast.nodes;

import ast.types.*;
import codegen.Label;
import java.util.ArrayList;
import java.util.Arrays;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        // Check if the atom is a function
        if (isFunctionCall()) {

            // Check if the atom is not a built-in function
            if (!Arrays.asList(bif).contains(atom.getId())) {

                errors.addAll(atom.checkSemantics(ST, _nesting, ft));

                TrailerNode trailer = (TrailerNode) trailers.get(0);
                String funName = atom.getId();

                // TODO: it isnt a function, it could be a variable
                STentry fun = ST.lookup(funName);

                if (fun != null && !(fun.getType() instanceof ImportType)) {
                    if ((fun.getType() instanceof FunctionType)) {
                        FunctionType atomFt = (FunctionType) fun.getType();
                        paramNumber = atomFt.getParamNumber();
                        funL = atomFt.getLabel();
                        int argNumber = trailer.getArgumentNumber();

                        if (paramNumber != argNumber) {
                            errors.add(new SemanticError(funName + "() takes " + String.valueOf(paramNumber)
                                    + " positional arguments but " + String.valueOf(argNumber) + " were given."));
                        }
                    }
                    for (var t : trailers) {
                        errors.addAll(t.checkSemantics(ST, _nesting, ft));
                    }
                }
            } else {
                for (var trailer : trailers) {
                    errors.addAll(trailer.checkSemantics(ST, _nesting, ft));
                }
            }
        } else if (atom != null) {
            errors.addAll(atom.checkSemantics(ST, _nesting, ft));
        }

        if (compOp != null) {
            errors.addAll(compOp.checkSemantics(ST, _nesting, ft));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting, ft));
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

    public boolean isFunctionCall() {
        return atom != null && !trailers.isEmpty();
    }

    @Override
    public String codeGeneration() {

        // A function call
        if (isFunctionCall()) {
            TrailerNode trailer = (TrailerNode) trailers.get(0);
            String trailerS = trailer.codeGeneration();

            // If the atom is a built-in function. return the trailer's content
            if (Arrays.asList(bif).contains(atom.getId())) {
                // remove "pushr A0\n" at the end of the trailer because it's useless
                return trailerS.substring(0, trailerS.length() - 9);
            }

            // We're not considering nested functions, so despite of slide 62
            // we do not have a forloop for nesting_level -
            // loopkup(..).nesting_level here.
            return "pushr FP\n"
                    + "move SP FP\n"
                    + "addi FP 1\n"
                    + "move AL T1\n"
                    + "pushr T1\n"
                    + trailerS
                    + "move FP AL\n"
                    + "subi AL 1\n"
                    + "jsub " + funL + "\n";
        }

        // Check operation
        if (op != null) {
            switch (op) {
                case "+":
                case "-":
                case "*":
                // In real Python `/` is a float division but we'll consider the
                // int division here below.
                case "/":
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

        // Check comp operation
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

    private String intOpCodeGen(Node leftE, Node rightE, String op) {
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
                ops = "div";
                break;
            default:
                ops = "Error: cannot manage op " + op;
        }

        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + ops + " T1 A0\n"
                + "popr A0\n";
    }

    private String modCodeGen(Node leftE, Node rightE) {
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();

        // Push the register twice because, for instance,
        // 7 % 2 =
        // 7 / 2 = 3
        // 3 * 2 = 6
        // 7 - 6 = 1 <--- result
        return ls
                + "pushr A0\n"
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + // Divide the two numbers
                "div T1 A0\n"
                + "popr T1\n"
                + // Multiply the division result for the number at the left
                "mul T1 A0\n"
                + "popr A0\n"
                + // Get the previous number at the left from the stack
                "popr T1\n"
                + // Subtracting the two numbers we have the module value
                "sub T1 A0\n"
                + "popr A0\n";
    }

    /**
     * NOTE: for the boolean operation we assume that False = 0, True = 1 NOTE:
     * we should optimize ignoring the the right value if the left value is
     * false.
     */
    private String andCodeGen(Node leftE, Node rightE) {
        String endl = Label.newBasic("endAnd");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "storei T1 0\n"
                + "beq A0 T1 " + endl + "\n"
                + rs
                + endl + ":\n";
    }

    /**
     * NOTE: we should optimize ignoring the right value if the left value is
     * true.
     */
    private String orCodeGen(Node leftE, Node rightE) {
        String endl = Label.newBasic("endOr");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "storei T1 1\n"
                + "beq A0 T1 " + endl + "\n"
                + rs
                + endl + ":\n";
    }

    private String notCodeGen(Node expr) {
        String exprs = expr.codeGeneration();
        // We use the `sub` because:
        // not True = 1 - 1 = 0 = False
        // not False = 1 - 0 = 1 = True
        return exprs
                + "storei T1 1\n"
                + "sub T1 A0\n"
                + "popr A0\n";
    }

    private String eqCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + "beq T1 A0 " + truel + "\n"
                + "storei A0 0\n"
                + "b " + endl + "\n"
                + truel + ":\n"
                + "storei A0 1\n"
                + endl + ":\n";
    }

    private String neqCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + "beq T1 A0 " + truel + "\n"
                + "storei A0 1\n"
                + // storei A0 1 instead of storei A0 0
                "b " + endl + "\n"
                + truel + ":\n"
                + // storei A0 0 instead of storei A0 1
                "storei A0 0\n"
                + endl + ":\n";
    }

    private String lsCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String truel2 = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + "bleq T1 A0 " + truel + "\n"
                + "storei A0 0\n"
                + "b " + endl + "\n"
                + truel + ":\n"
                + "beq T1 A0 " + truel2 + "\n"
                + "storei A0 1\n"
                + "b " + endl + "\n"
                + truel2 + ":\n"
                + "storei A0 0\n"
                + "b " + endl + "\n"
                + endl + ":\n";
    }

    private String leqCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + "bleq T1 A0 " + truel + "\n"
                + "storei A0 0\n"
                + "b " + endl + "\n"
                + truel + ":\n"
                + "storei A0 1\n"
                + endl + ":\n";
    }

    private String gtCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + // Invert A0 and T1 (different than leq)
                "bleq A0 T1 " + truel + "\n"
                + "storei A0 0\n"
                + "b " + endl + "\n"
                + truel + ":\n"
                + "storei A0 1\n"
                + endl + ":\n";
    }

    private String gteCodeGen(Node leftE, Node rightE) {
        String truel = Label.newBasic("true");
        String truel2 = Label.newBasic("true");
        String endl = Label.newBasic("end");
        String ls = leftE.codeGeneration();
        String rs = rightE.codeGeneration();
        return ls
                + "pushr A0\n"
                + rs
                + "popr T1\n"
                + "bleq T1 A0 " + truel + "\n"
                + "storei A0 1\n"
                + "b " + endl + "\n"
                + truel + ":\n"
                + "beq T1 A0 " + truel2 + "\n"
                + "storei A0 0\n"
                + "b " + endl + "\n"
                + truel2 + ":\n"
                + "storei A0 1\n"
                + "b " + endl + "\n"
                + endl + ":\n";
    }

}
