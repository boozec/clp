package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AtomNode implements Node {
    private TerminalNode val;

    public AtomNode(TerminalNode _val) {
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
        return s + "Atom(" + val.toString() + ")";

    }
}
