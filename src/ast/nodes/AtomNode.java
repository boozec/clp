package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `atom` statement of the grammar.
 */
public class AtomNode implements Node {
    protected String val;

    public AtomNode(String val) {
        this.val = val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        return new ArrayList<SemanticError>();
    }

    // FIXME: this type for atom
    @Override
    public Type typeCheck() {
        return new AtomType();
    }

    // TODO: add code generation for atom node
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        if (val != null) {
            return prefix + "Atom(" + val + ")\n";
        }

        return prefix + "Atom(null)\n";

    }
}
