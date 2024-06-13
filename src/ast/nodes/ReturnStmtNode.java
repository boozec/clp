package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `return_stmt` statement of the grammar.
 */
public class ReturnStmtNode implements Node {
    private Node exprList;

    public ReturnStmtNode(Node exprList) {
        this.exprList = exprList;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (this.exprList != null) {
            errors.addAll(this.exprList.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        if (this.exprList != null) {
            return this.exprList.typeCheck();
        }

        return new VoidType();
    }

    // TODO: add code generation for return stmt
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "ReturnStmt\n";

        prefix += "  ";
        if (this.exprList != null) {
            str += this.exprList.toPrint(prefix);
        }

        return str;
    }

}
