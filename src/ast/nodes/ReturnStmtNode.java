package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class ReturnStmtNode implements Node {
    private Node exprList;

    public ReturnStmtNode(Node exprList) {
        this.exprList = exprList;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

            errors.addAll(this.exprList.checkSemantics(ST, _nesting));

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
        String result = s + "ReturnStmt\n" + this.exprList.toPrint(s + "  ");

        return result;
    }

}
