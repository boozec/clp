package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class AssignmentNode implements Node {
    private Node lhr;
    private Node assign;
    private Node rhr;

    public AssignmentNode(Node _lhr, Node _assign, Node _rhr) {
        lhr = _lhr;
        assign = _assign;
        rhr = _rhr;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(lhr.checkSemantics(ST, _nesting));
        errors.addAll(assign.checkSemantics(ST, _nesting));
        errors.addAll(rhr.checkSemantics(ST, _nesting));

        return errors;
    }

    public Type typeCheck() {
        return new IntType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        return s + "Assignment\n" + lhr.toPrint(s + "  ") + assign.toPrint(s + "  ") + rhr.toPrint(s + "  ");
    }

}
