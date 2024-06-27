package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `for_stmt` statement of the grammar.
 */
public class ForStmtNode implements Node {

    private ExprListNode exprList;
    private Node block;

    public ForStmtNode(Node exprList, Node block) {
        this.exprList = (ExprListNode) exprList;
        this.block = block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        // ST.insert(expr.getId(), expr.typeCheck(s), _nesting, "");

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
