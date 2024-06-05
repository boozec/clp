package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class ArglistNode implements Node {
    protected ArrayList<Node> arguments;

    public ArglistNode(ArrayList<Node> arguments) {
        this.arguments = arguments;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (var arg : arguments) {
            errors.addAll(arg.checkSemantics(ST, _nesting));
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
        String result = s + "ArglistNode\n";

        for (Node arg : arguments) {
            result += arg.toPrint(s + "  ");
        }

        return result;
    }

}
