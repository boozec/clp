package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class RootNode implements Node {
    protected ArrayList<Node> stmts;
    protected ArrayList<Node> compoundStmts;

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
        String result = "Root\n";

        for (Node stmt : stmts) {
            result += stmt.toPrint(s + "  ");
        }
        for (Node stmt : compoundStmts) {
            result += stmt.toPrint(s + "  ");
        }

        return result;
    }

}
