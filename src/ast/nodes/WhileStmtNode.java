package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `while_stmt` statement of the grammar.
 */
public class WhileStmtNode implements Node {

    private final Node expr;
    private final Node block;

    public WhileStmtNode(Node expr, Node block) {
        this.expr = expr;
        this.block = block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(expr.checkSemantics(ST, _nesting, ft));
        errors.addAll(block.checkSemantics(ST, _nesting, ft));

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add cgen per while (but it's not requested from the exercise)
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "While\n" + expr.toPrint(prefix + "  ") + block.toPrint(prefix + "  ");
    }
}
