package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import codegen.Label;

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

    @Override
    public String codeGeneration() {
        String startLabel = Label.newBasic("start");
        String endLabel = Label.newBasic("end");

        String exprS = expr.codeGeneration();
        String blockS = block.codeGeneration();

        return startLabel + ":\n" +
                exprS +
                "storei T1 0\n" +
                "beq A0 T1 " + endLabel + "\n" +
                blockS +
                "b " + startLabel + "\n" +
                endLabel + ":\n";
    }

    @Override
    public String printAST(String prefix) {
        return prefix + "While\n" + expr.printAST(prefix + "  ") + block.printAST(prefix + "  ");
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
