package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class CompoundNode implements Node {
    private Node ifNode;
    private Node funcDef;
    private Node forStmt;
    private Node whileStmt;

    public CompoundNode(Node ifNode, Node funcDef, Node forStmt, Node whileStmt) {
        this.ifNode = ifNode;
        this.funcDef = funcDef;
        this.forStmt = forStmt;
        this.whileStmt = whileStmt;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (ifNode != null) {
            errors.addAll(ifNode.checkSemantics(ST, _nesting));
        }

        if (funcDef != null) {
            errors.addAll(funcDef.checkSemantics(ST, _nesting));
        }

        if (forStmt != null) {
            errors.addAll(forStmt.checkSemantics(ST, _nesting));
        }

        if (whileStmt != null) {
            errors.addAll(whileStmt.checkSemantics(ST, _nesting));
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
        String result = s + "CompoundNode\n";

        if (ifNode != null) {
            result += ifNode.toPrint(s + "  ");
        }

        if (funcDef != null) {
            result += funcDef.toPrint(s + "  ");
        }

        if (forStmt != null) {
            result += forStmt.toPrint(s + "  ");
        }

        if (whileStmt != null) {
            result += whileStmt.toPrint(s + "  ");
        }

        return result;
    }

}
