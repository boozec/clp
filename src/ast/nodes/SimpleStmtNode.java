package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class SimpleStmtNode implements Node {
    private Node stmt;

    public SimpleStmtNode(Node _stmt) {
        stmt = _stmt;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(stmt.checkSemantics(ST, _nesting));

        return errors;
    }

    public Type typeCheck() {
        return new VoidType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        return s + "SimpleStmt\n" + stmt.toPrint(s + "  ");
    }

}
