package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;
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

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
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
