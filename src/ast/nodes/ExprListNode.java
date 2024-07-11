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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting, ft));
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

    @Override
    public String codeGeneration() {
        String str = "";

        for (var exp : exprs) {
            str += exp.codeGeneration();
        }

        return str;
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "ExprList\n";

        prefix += "  ";
        for (var param : exprs) {
            str += param.printAST(prefix);
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {

        // TODO: Diamo per assodato che rimuova le , alla fine
        String str = prefix + exprs.get(0).toPrint("");

        for (int i = 1; i < exprs.size(); ++i) {
            str += ", " + exprs.get(i).toPrint("");
        }

        return str;
    }

    public List<Node> getExprs() {
        return exprs;
    }

}
