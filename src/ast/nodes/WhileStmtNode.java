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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(expr.checkSemantics(ST, _nesting));
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
    public String printAST(String prefix) {
        return prefix + "While\n" + expr.toPrint(prefix + "  ") + block.toPrint(prefix + "  ");
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "while ";
        str += expr.toPrint("") + ":\n";
        str += block.toPrint(prefix + "\t") + "\n";
        return str;
    }

    public Node getBlock() {
        return block;
    }

    public Node getExpr() {
        return expr;
    }

    
}
