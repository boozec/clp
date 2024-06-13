package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `arglist` statement of the grammar.
 */
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

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for arglist node
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "ArglistNode\n";

        prefix += "  ";
        for (Node arg : arguments) {
            str += arg.toPrint(prefix);
        }

        return str;
    }

}
