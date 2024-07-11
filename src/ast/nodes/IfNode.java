package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import codegen.Label;

/**
 * Node for the `if` statement of the grammar.
 */
public class IfNode implements Node {

    private final Node guard;
    private final Node thenBranch;
    private final Node elseBranch;

    public IfNode(Node guard, Node thenBranch, Node elseBranch) {
        this.guard = guard;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(guard.checkSemantics(ST, _nesting, ft));
        errors.addAll(thenBranch.checkSemantics(ST, _nesting, ft));
        if (elseBranch != null) {
            errors.addAll(elseBranch.checkSemantics(ST, _nesting, ft));
        }

        return errors;
    }

    // FIXME: fix the if statement
    @Override
    public Type typeCheck() {
        if (guard.typeCheck() instanceof BoolType) {
            Type thenexp = thenBranch.typeCheck();
            Type elseexp = elseBranch.typeCheck();
            if (thenexp.getClass().equals(elseexp.getClass())) {
                return thenexp;
            } else {
                System.out.println("Type Error: incompatible types in then and else branches.");
                return new ErrorType();
            }
        } else {
            System.out.println("Type Error: non boolean condition in if.");
            return new ErrorType();
        }
    }

    @Override
    public String codeGeneration() {
        String thenLabel = Label.newBasic("then");
        String endLabel = Label.newBasic("end");

        String guardS = guard.codeGeneration();
        String thenS = thenBranch.codeGeneration();
        String elseS = "";
        if (elseBranch != null) {
            elseS = elseBranch.codeGeneration();
        }

        // We're assuming that the guard is boolean or an operation which puts a
        // true (1) or false (0) into A0.
        return guardS +
                "storei T1 1\n" +
                // Check if A0 = true
                "beq A0 T1 " + thenLabel + "\n" +
                elseS +
                "b " + endLabel + "\n" +
                thenLabel + ":\n" +
                thenS +
                endLabel + ":\n";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "If\n" + guard.printAST(prefix + "  ") + thenBranch.printAST(prefix + "  ");

        if (elseBranch != null) {
            str += elseBranch.printAST(prefix + "  ");
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "if ";
        str += guard.toPrint("") + ":\n";
        str += thenBranch.toPrint(prefix + "\t") + "\n";
        if (elseBranch != null) {
            str += prefix + "else:\n";
            str += elseBranch.toPrint(prefix + "\t") + "\n";
        }
        return str;
    }

}
