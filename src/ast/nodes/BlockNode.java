package ast.nodes;

import java.util.ArrayList;

import ast.types.*;

/**
 * Node for `block` statement of the grammar.
 * It extends the `RootNode`.
 */
public class BlockNode extends RootNode {
    public BlockNode(ArrayList<Node> stmts, ArrayList<Node> compoundStmts) {
        super(stmts, compoundStmts);
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Block\n";

        prefix += "  ";
        for (Node stmt : stmts) {
            str += stmt.toPrint(prefix);
        }
        for (Node stmt : compoundStmts) {
            str += stmt.toPrint(prefix);
        }

        return str;
    }

}
