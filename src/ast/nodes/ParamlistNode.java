package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class ParamlistNode implements Node {
    private ArrayList<Node> params;

    public ParamlistNode(ArrayList<Node> _params) {
        params = _params;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (var param : params) {
            errors.addAll(param.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    public Type typeCheck() {
        // FIXME: wtf is that?
        return new VoidType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        String result = s + "Paramlist\n";

        for (var param : params) {
            result += param.toPrint(s + "  ");
        }

        return result;
    }

}
