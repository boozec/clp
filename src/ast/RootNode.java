package com.clp.project.ast;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;

public class RootNode implements Node {
    private ArrayList<Node> stmts;
    private ArrayList<Node> compoundStmts;

    public RootNode(ArrayList<Node> _stmts, ArrayList<Node> _compoundStmts) {
        stmts = _stmts;
        compoundStmts = _compoundStmts;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (Node stmt : stmts) {
            errors.addAll(stmt.checkSemantics(ST, _nesting));
        }
        for (Node stmt : compoundStmts) {
            errors.addAll(stmt.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    public Type typeCheck() {
        return new VoidType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        // FIXME: fix
        return "";
    }

}
