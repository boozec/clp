package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `simple_stmt` statement of the grammar.
 */
public class SimpleStmtNode implements Node {
    private Node assignment;
    private Node expr;
    private Node returnStmt;
    private Node importStmt;

    public SimpleStmtNode(Node assignment, Node expr, Node returnStmt, Node importStmt) {
        this.assignment = assignment;
        this.expr = expr;
        this.returnStmt = returnStmt;
        this.importStmt = importStmt;
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

        if (returnStmt != null) {
            errors.addAll(returnStmt.checkSemantics(ST, _nesting));
        }

        if (importStmt != null) {
            errors.addAll(importStmt.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for SimpleStmtNode
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "SimpleStmt\n";

        prefix += "  ";

        if (assignment != null) {
            str += assignment.toPrint(prefix);
        }

        if (expr != null) {
            str += expr.toPrint(prefix);
        }

        if (returnStmt != null) {
            str += returnStmt.toPrint(prefix);
        }

        if (importStmt != null) {
            str += importStmt.toPrint(prefix);
        }

        return str;
    }

}
