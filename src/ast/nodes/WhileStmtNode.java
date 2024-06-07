package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `while_stmt` statement of the grammar.
 */
public class WhileStmtNode implements Node {
    private Node expr;
    private Node block;

    public WhileStmtNode(Node expr, Node block) {
        this.expr = expr;
        this.block = block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(expr.checkSemantics(ST, _nesting));
        errors.addAll(block.checkSemantics(ST, _nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for while
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "While\n" + expr.toPrint(prefix + "  ") + block.toPrint(prefix + "  ");
    }
}
