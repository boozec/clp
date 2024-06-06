package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TrailerNode implements Node {
    private Node arglist;
    private ArrayList<Node> exprs;
    private TerminalNode methodCall;
    private boolean isEmpty;

    public TrailerNode(Node arglist, ArrayList<Node> exprs, TerminalNode methodCall) {
        this.arglist = arglist;
        this.exprs = exprs;
        this.methodCall = methodCall;

        this.isEmpty = (this.arglist == null && this.exprs.size() == 0 && this.methodCall == null);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (arglist != null) {
            errors.addAll(arglist.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
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
        String result = s + "TrailerNode\n";

        if (arglist != null) {
            result += arglist.toPrint(s + "  ");
        }

        for (var expr : exprs) {
            result += expr.toPrint(s + "  ");
        }

        if (methodCall != null) {
            result += s + "  Method(" + methodCall + ")\n";
        }

        if (isEmpty) {
            result += s + "  ()\n";
        }

        return result;
    }

}
