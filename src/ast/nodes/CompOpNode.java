package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Node for the `comp_op` statement of the grammar.
 */
public class CompOpNode implements Node {

    private final TerminalNode op;

    public CompOpNode(TerminalNode op) {
        this.op = op;
    }

    public String getOp() {
        return op.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        return new ArrayList<>();
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    /**
     * The code generation for this operation is in `ExprNode`.
     */
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        return prefix + "CompOpNode(" + op + ")\n";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + op;
    }
}
