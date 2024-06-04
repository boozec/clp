package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class SimpleStmtsNode implements Node {
    private ArrayList<Node> stmts;

    public SimpleStmtsNode(ArrayList<Node> _stmts) {
        stmts = _stmts;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (Node stmt : stmts) {
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
        String result = s + "SimpleStmts\n";
        for (Node stmt : stmts) {
            result += stmt.toPrint(s + "  ");
        }

        return result;
    }

}
