package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class CompoundNode implements Node {
    private Node ifNode;
    private Node funcDef;

    public CompoundNode(Node _ifNode, Node _funcDef) {
        ifNode = _ifNode;
        funcDef = _funcDef;
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

        return result;
    }

}
