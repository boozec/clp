package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class IntNode implements Node {
    private Integer val;

    public IntNode(Integer _val) {
        val = _val;
    }

    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        return new ArrayList<SemanticError>();
    }

    public Type typeCheck() {
        return new IntType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        return s + Integer.toString(val) + "\n";
    }
}
