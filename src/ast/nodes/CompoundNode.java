package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `compound_node` statement of the grammar.
 */
public class CompoundNode implements Node {
    private Node ifNode;
    private Node funcDef;
    private Node forStmt;
    private Node whileStmt;

    public CompoundNode(Node ifNode, Node funcDef, Node forStmt, Node whileStmt) {
        this.ifNode = ifNode;
        this.funcDef = funcDef;
        this.forStmt = forStmt;
        this.whileStmt = whileStmt;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (ifNode != null) {
            errors.addAll(ifNode.checkSemantics(ST, _nesting));
        }

        if (funcDef != null) {
            errors.addAll(funcDef.checkSemantics(ST, _nesting));
        }

        if (forStmt != null) {
            errors.addAll(forStmt.checkSemantics(ST, _nesting));
        }

        if (whileStmt != null) {
            errors.addAll(whileStmt.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for CompoundNode
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "CompoundNode\n";

        prefix += "  ";

        if (ifNode != null) {
            str += ifNode.toPrint(prefix);
        }

        if (funcDef != null) {
            str += funcDef.toPrint(prefix);
        }

        if (forStmt != null) {
            str += forStmt.toPrint(prefix);
        }

        if (whileStmt != null) {
            str += whileStmt.toPrint(prefix);
        }

        return str;
    }

}
