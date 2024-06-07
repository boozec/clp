package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Node for the `if` statement of the grammar.
 */
public class IfNode implements Node {
    private Node guard;
    private Node thenbranch;
    private Node elsebranch;

    public IfNode(Node guard, Node thenbranch, Node elsebranch) {
        this.guard = guard;
        this.thenbranch = thenbranch;
        this.elsebranch = elsebranch;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(guard.checkSemantics(ST, _nesting));
        errors.addAll(thenbranch.checkSemantics(ST, _nesting));
        if (elsebranch != null) {
            errors.addAll(elsebranch.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    // FIXME: fix the if statement
    @Override
    public Type typeCheck() {
        if (guard.typeCheck() instanceof BoolType) {
            Type thenexp = thenbranch.typeCheck();
            Type elseexp = elsebranch.typeCheck();
            if (thenexp.getClass().equals(elseexp.getClass()))
                return thenexp;
            else {
                System.out.println("Type Error: incompatible types in then and else branches");
                return new ErrorType();
            }
        } else {
            System.out.println("Type Error: non boolean condition in if");
            return new ErrorType();
        }
    }

    // TODO: add code generation for if
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "If\n" + guard.toPrint(prefix + "  ") + thenbranch.toPrint(prefix + "  ");

        if (elsebranch != null) {
            str += elsebranch.toPrint(prefix + "  ");
        }

        return str;
    }

}
