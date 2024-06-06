package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class AtomNode implements Node {
    protected String val;

    public AtomNode(String _val) {
        val = _val;
    }

    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        return new ArrayList<SemanticError>();
    }

    public Type typeCheck() {
        return new AtomType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        if (val != null) {
            return s + "Atom(" + val + ")\n";
        }

        return s + "Atom(null)\n";

    }
}
