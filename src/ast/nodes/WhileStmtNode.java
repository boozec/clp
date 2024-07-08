package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import codegen.Label;

/**
 * Node for the `while_stmt` statement of the grammar.
 */
public class WhileStmtNode implements Node {

    private final Node expr;
    private final Node block;

    public WhileStmtNode(Node expr, Node block) {
        this.expr = expr;
        this.block = block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(expr.checkSemantics(ST, _nesting));
        errors.addAll(block.checkSemantics(ST, _nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        return "";
        // String startLabel = Label.newBasic("start");
        // String endLabel = Label.newBasic("end");

        // String exprS = expr.codeGeneration();
        // String blockS = block.codeGeneration();

        // // Assumo che l'espressione sia un dato booleano o una operazione booleana che mette in AO il valore true (1) o false (0)
        // return  startLabel + ":\n" +
        //         exprS + "\njeq A0 0 " + endLabel + "\n" +   // Controllo che A0 sia false (0). Se vero faccio jump alla fine,
        //         blockS + "\nb " + startLabel +  "\n" +      // altrimenti eseguo la blockBranch e, finito, jumpo alla all'inzio
        //         endLabel + ":\n";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "While\n" + expr.toPrint(prefix + "  ") + block.toPrint(prefix + "  ");
    }
}
