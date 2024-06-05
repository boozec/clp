package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

public class CompNode implements Node {
    private TerminalNode val;

    public CompNode(TerminalNode _val) {
        val = _val;
    }

    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        return new ArrayList<SemanticError>();
    }

    public Type typeCheck() {
        return new ErrorType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        return s + "CompNode(" + val + ")\n";
    }
}
