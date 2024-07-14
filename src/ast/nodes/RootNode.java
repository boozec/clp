package ast.nodes;

import ast.types.*;
import codegen.Label;
import java.util.ArrayList;
import java.util.HashMap;
import semanticanalysis.*;

/**
 * Node for the `root` statement of the grammar.
 */
public class RootNode implements Node {

    // stms and compundStmts are protected because they are reused for a
    // BlockNode
    protected ArrayList<Node> childs;

    public RootNode(ArrayList<Node> childs) {
        this.childs = childs;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // Create a new HashMap for the current scope
        HashMap<String, STentry> HM = new HashMap<>();

        // Add the HashMap to the SymbolTable
        ST.add(HM);

        // Check semantics for each child
        for (Node child : childs) {
            errors.addAll(child.checkSemantics(ST, _nesting, ft));
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
        // Workaround per SP = MEM - 1
        String str = "pushr FP\npushr AL\n";

        for (Node child : childs) {
            str += child.codeGeneration();
        }

        for (int i = 0; i < Label.getGlobalVarNum() - 1; i++) {
            str += "pop\n";
        }

        return str + "pop\npop\nhalt\n" + Label.getFunDef();
    }

    @Override
    public String printAST(String prefix) {
        String str = "Root\n";

        prefix += "  ";

        for (Node child : childs) {
            str += child.printAST(prefix);
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix;
        for (Node child : childs) {
            str += child.toPrint("");
        }
        return str;
    }

    public ArrayList<Node> getChilds() {
        return childs;
    }

    public Node getChild(int i) {
        return childs.get(i);
    }
}
