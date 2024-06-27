package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Node for the `dooted_name` statement of the grammar.
 */
public class DottedNameNode implements Node {
    protected ArrayList<TerminalNode> names;

    public DottedNameNode(ArrayList<TerminalNode> names) {
        this.names = names;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (int i = 0; i < names.size(); ++i) {
            ST.insert(names.get(i).toString(), this.typeCheck(), _nesting, null);
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new ImportType();
    }

    // NOTE: we do not provide code generation for this node in the same way
    // we do not want to do this for the import stm.
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "DottedName\n";

        prefix += "  ";
        for (var name : names) {
            str += prefix + name.toString() + "\n";
        }

        return str;
    }

}
