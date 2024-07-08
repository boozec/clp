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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(guard.checkSemantics(ST, _nesting));
        errors.addAll(thenBranch.checkSemantics(ST, _nesting));
        if (elseBranch != null) {
            errors.addAll(elseBranch.checkSemantics(ST, _nesting));
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
            }else {
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
        String elseS = elseBranch.codeGeneration();

        // Assumo che la guardia sia un dato booleano o una operazione booleana che mette in A0 il valore true (1) o false (0)
        return  guardS + "storei T1 1\nbeq A0 T1 " + thenLabel + "\n" +     // Controllo che A0 sia true (1). Se vero faccio jump alla
                elseS + "b " + endLabel + "\n" +                // thenBranch, altrimenti eseguo la elseBranch e jumpo alla fine
                thenLabel + ":\n" + thenS + endLabel + ":\n";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "If\n" + guard.toPrint(prefix + "  ") + thenBranch.toPrint(prefix + "  ");

        if (elseBranch != null) {
            str += elseBranch.toPrint(prefix + "  ");
        }

        return str;
    }

}
