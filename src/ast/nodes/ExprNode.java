package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class ExprNode implements Node {
    private Node atom;
    private Node compOp;
    private String op;
    private ArrayList<Node> exprs;
    private ArrayList<Node> trailers;

    public ExprNode(Node atom, Node compOp, ArrayList<Node> exprs, String op, ArrayList<Node> trailers) {
        this.atom = atom;
        this.compOp = compOp;
        this.exprs = exprs;
        this.op = op;
        this.trailers = trailers;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (atom != null) {
            errors.addAll(atom.checkSemantics(ST, _nesting));
        }

        if (compOp != null) {
            errors.addAll(compOp.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        for (var trailer : trailers) {
            errors.addAll(trailer.checkSemantics(ST, _nesting));
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
        String result = s + "Expr\n";

        if (atom != null) {
            result += atom.toPrint(s + "  ");
        }

        if (compOp != null) {
            result += compOp.toPrint(s + "  ");
        }

        for (var expr : exprs) {
            result += expr.toPrint(s + "  ");
        }

        for (var trailer : trailers) {
            result += trailer.toPrint(s + "  ");
        }

        if (op != null) {
            result += s + "  Op(" + op + ")\n";
        }

        return result;
    }

}
