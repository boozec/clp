package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `param_list` statement of the grammar.
 */
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

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: code generation for param list
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Paramlist\n";

        prefix += "  ";
        for (var param : params) {
            str += param.toPrint(prefix);
        }

        return str;
    }

}
