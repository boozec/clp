package ast.nodes;

import ast.types.*;
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

    public void setExpr(int i, Node expr) {
        this.exprs.set(i, expr);
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

    public Node getCompOp() {
        return compOp;
    }

    public String getOp() {
        return op;
    }

    public ArrayList<Node> getTrailers() {
        return trailers;
    }

    public ArrayList<Node> getExprs() {
        return exprs;
    }

    public AtomNode getAtom() {
        return atom;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

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
                        for (var t : trailers) {
                            errors.addAll(t.checkSemantics(ST, _nesting));
                        }

                    } else {
                        FunctionType ft = (FunctionType) fun.getType();
                        int paramNumber = ft.getParamNumber();
                        int argNumber = trailer.getArgumentNumber();

                        if (paramNumber != argNumber) {
                            errors.add(new SemanticError(funName + "() takes " + String.valueOf(paramNumber)
                                    + " positional arguments but " + String.valueOf(argNumber) + " were given."));
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

    // TODO: add code generation for expr
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "Expr\n";

        prefix += "  ";
        if (atom != null) {
            str += atom.toPrint(prefix);
            
            for (var trailer : trailers) {
                str += trailer.toPrint(prefix);
            }
        } else {
            if (compOp != null) {
                str += compOp.toPrint(prefix);
            }
            
            if (exprs != null) {
                for (var expr : exprs) {
                    str += expr.toPrint(prefix);
                }
            }
            
            if (op != null) {
                str += prefix + "Op(" + op + ")\n";
            }
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix;

        if (atom != null) {
            str += atom.toPrint(prefix);
            for (var trailer : trailers) {
                str += trailer.toPrint(prefix);
            }
        } else {
            if (op == "+" || op == "-" || op == "~") {
                str += prefix + op + exprs.get(0).toPrint("");
            } else {
                str += prefix + exprs.get(0).toPrint("") + " ";
                if (compOp != null) {
                    str += compOp.toPrint("") + " " + exprs.get(1).toPrint("");
                } else {
                    str += op + " " + exprs.get(1).toPrint("");
                }
            }
        }

        return str;
    }

}
