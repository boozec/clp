package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `expr_list` statement of the grammar.
 */
public class ExprListNode implements Node {

    private final ArrayList<Node> exprs;

    public ExprListNode(ArrayList<Node> exprs) {
        this.exprs = exprs;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList();

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    public int getSize() {
        return exprs.size();
    }

    public Node getElem(int i) {
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
        String str = prefix + "Paramlist\n";

        prefix += "  ";
        for (var param : exprs) {
            str += param.toPrint(prefix);
        }

        return str;
    }

}
