package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.HashMap;
import semanticanalysis.*;

/**
 * Node for the `root` statement of the grammar.
 */
public class RootNode implements Node {

    static final int maxValue = 100;

    // stms and compundStmts are protected because they are reused for a
    // BlockNode
    protected ArrayList<Node> childs;

    public RootNode(ArrayList<Node> childs) {
        this.childs = childs;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // Create a new HashMap for the current scope
        HashMap<String, STentry> HM = new HashMap<>();

        // Add the HashMap to the SymbolTable
        ST.add(HM);

        // Check semantics for each child
        for (Node child : childs) {
            errors.addAll(child.checkSemantics(ST, _nesting));
        }

        // Remove the HashMap from the SymbolTable
        ST.remove();

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        String fpVal = String.valueOf(this.maxValue);
        String spVal = String.valueOf(this.maxValue - 1);
        String str = "storei FP " + fpVal + "\nstorei SP " + spVal + "\n";

        for (Node child : childs) {
            str += child.codeGeneration();
        }

        return str + "halt";
    }

    @Override
    public String toPrint(String prefix) {
        String str = "Root\n";

        prefix += "  ";

        for (Node child : childs) {
            str += child.toPrint(prefix);
        }

        return str;
    }

}
