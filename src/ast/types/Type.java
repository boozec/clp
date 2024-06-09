package com.clp.project.ast.types;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.nodes.*;

/**
 * A node which represents a type class.
 */
public class Type implements Node {
    public boolean isEqual(Type A, Type B) {
        if (A.getClass().equals(B.getClass()))
            return true;
        else
            return false;
    }

    public String toPrint(String s) {
        return s;
    }

    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        // It is never invoked
        return null;
    }

    @Override
    public Type typeCheck() {
        // It is never invoked
        return null;
    }

    @Override
    public String codeGeneration() {
        // It is never invoked
        return "";
    }

}
