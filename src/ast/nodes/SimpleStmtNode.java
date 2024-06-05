package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class SimpleStmtNode implements Node {
    private Node assignment;
    private Node expr;

    public SimpleStmtNode(Node assignment, Node expr) {
        this.assignment = assignment;
        this.expr = expr;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (assignment != null) {
            errors.addAll(assignment.checkSemantics(ST, _nesting));
        }

        if (expr != null) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
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
        String result = s + "SimpleStmt\n";

        if (assignment != null) {
            result += assignment.toPrint(s + "  ");
        }

        if (expr != null) {
            result += expr.toPrint(s + "  ");
        }

        return result;
    }

}
