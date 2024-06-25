package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;

/**
 * Node for the `expr` statement of the grammar.
 */
public class ExprNode implements Node {
    private Node atom;
    private Node compOp;
    private String op;
    private ArrayList<Node> exprs;
    private ArrayList<Node> trailers;

    public ExprNode(Node atom, Node compOp, ArrayList<Node> exprs, String op, ArrayList<Node> trailers) {
        this.atom = atom;
        this.compOp = compOp;
        this.exprs = exprs;
        this.op = op;
        this.trailers = trailers;
    }

    public String getId() {
        return ((AtomNode) this.atom).getId();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        if (atom != null) {
            errors.addAll(atom.checkSemantics(ST, _nesting));
        }

        if (compOp != null) {
            errors.addAll(compOp.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        for (var trailer : trailers) {
            errors.addAll(trailer.checkSemantics(ST, _nesting));
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
    public String toPrint(String prefix) {
        String str = prefix + "Expr\n";

        prefix += "  ";
        if (atom != null) {
            str += atom.toPrint(prefix);
        }

        if (compOp != null) {
            str += compOp.toPrint(prefix);
        }

        for (var expr : exprs) {
            str += expr.toPrint(prefix);
        }

        for (var trailer : trailers) {
            str += trailer.toPrint(prefix);
        }

        if (op != null) {
            str += prefix + "Op(" + op + ")\n";
        }

        return str;
    }

}
