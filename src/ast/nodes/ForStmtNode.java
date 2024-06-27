package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `for_stmt` statement of the grammar.
 */
public class ForStmtNode implements Node {

    private Node exprList;
    private Node block;

    public ForStmtNode(Node exprList, Node block) {
        this.exprList = exprList;
        this.block = block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        var l = (ExprListNode) exprList;
        for (int i = 0; i < l.getSize() - 1; ++i) {
            var e = (ExprNode) l.getElem(i);
            ST.insert(e.getId(), e.typeCheck(), _nesting, "");
        }

        var left = (ExprNode) l.getElem(l.getSize() - 1);
        var atomLeft = (ExprNode) left.getExpr(0);
        ST.insert(atomLeft.getId(), atomLeft.typeCheck(), _nesting, "");

        errors.addAll(exprList.checkSemantics(ST, _nesting));
        errors.addAll(block.checkSemantics(ST, _nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for while
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "For\n" + exprList.toPrint(prefix + "  ") + block.toPrint(prefix + "  ");
    }

}
