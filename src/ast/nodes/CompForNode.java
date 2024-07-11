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
    protected ExprNode expr;
    protected CompIterNode comp_iter;

    public CompForNode(Node exprlist, Node expr, Node comp_iter) {
        this.exprlist = (ExprListNode) exprlist;
        this.expr = (ExprNode) expr;
        this.comp_iter = (CompIterNode) comp_iter;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(exprlist.checkSemantics(ST, _nesting, ft));
        errors.addAll(expr.checkSemantics(ST, _nesting, ft));
        if (comp_iter != null) {
            errors.addAll(comp_iter.checkSemantics(ST, _nesting, ft));
        }
        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    /**
     * We do not want to provide the code generation for the for list comprehension.
     */
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "CompForNode\n";

        prefix += "  ";
        str += exprlist.printAST(prefix);
        str += expr.printAST(prefix);
        str += comp_iter.printAST(prefix);
        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "for";

        for (int i = 0; i < exprlist.getExprs().size(); i++) {
            str += exprlist.getExprs().get(i).toPrint("");
        }

        str += " in " + expr.toPrint("") + "\n";
        str += comp_iter.toPrint(prefix + "\t");

        return str;
    }

}
