package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.List;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `expr_list` statement of the grammar.
 */
public class ExprListNode implements Node {

    private final List<Node> exprs;

    public ExprListNode(List<Node> exprs) {
        this.exprs = exprs;

        // FIXME: remove this print
        // System.out.println(exprs);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    public int getSize() {
        return exprs.size();
    }

    /**
     * Returns the i-th expressions of `exprs` field. If the index is greater or
     * equals than the size return `null`.
     */
    public Node getElem(int i) {
        if (i >= this.exprs.size()) {
            return null;
        }
        return exprs.get(i);
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: code generation for expr list
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "ExprList\n";

        prefix += "  ";
        for (var param : exprs) {
            str += param.toPrint(prefix);
        }

        return str;
    }

    public List<Node> getExprs() {
        return exprs;
    }

}
