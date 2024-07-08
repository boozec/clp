package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `comp_for` statement of the grammar. 'for' exprlist 'in' expr
 * comp_iter?
 */
public class CompForNode implements Node {

    protected ExprListNode exprlist;
    protected ExprNode single_expr;
    protected CompIterNode comp_iter;

    public CompForNode(Node exprlist, Node single_expr, Node comp_iter) {
        this.exprlist = (ExprListNode) exprlist;
        this.single_expr = (ExprNode) single_expr;
        this.comp_iter = (CompIterNode) comp_iter;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(exprlist.checkSemantics(ST, _nesting));
        errors.addAll(single_expr.checkSemantics(ST, _nesting));
        if (comp_iter != null) {
            errors.addAll(comp_iter.checkSemantics(ST, _nesting));
        }
        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for comp_for node
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "CompForNode\n";

        prefix += "  ";
        str += exprlist.toPrint(prefix);
        str += single_expr.toPrint(prefix);
        str += comp_iter.toPrint(prefix);
        return str;
    }

}
