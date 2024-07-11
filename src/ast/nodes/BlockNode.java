package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for `block` statement of the grammar. It extends the `RootNode`.
 */
public class BlockNode extends RootNode {

    public BlockNode(ArrayList<Node> childs) {
        super(childs);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // Check semantics for each child
        for (Node child : childs) {
            errors.addAll(child.checkSemantics(ST, _nesting, ft));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        String str = "";

        for (Node child : childs) {
            str += child.codeGeneration();
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Block\n";

        prefix += "  ";
        for (Node child : childs) {
            str += child.toPrint(prefix);
        }

        return str;
    }

}
