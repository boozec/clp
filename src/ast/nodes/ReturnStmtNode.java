package ast.nodes;

import ast.types.*;
import codegen.Label;

import java.util.ArrayList;

import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `return_stmt` statement of the grammar.
 */
public class ReturnStmtNode implements Node {

    private final Node exprList;

    // VERY scatchy
    private int paramNumber;

    public ReturnStmtNode(Node exprList) {
        this.exprList = exprList;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        String id = Label.getFuntrace();
        STentry ftEntry = ST.lookup(id);
        FunctionType ft = (FunctionType) ftEntry.getType();
        this.paramNumber = ft.getParamNumber();
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

    @Override
    public String codeGeneration() {
        String expS = exprList.codeGeneration();
        return expS +
                "popr RA\n" +
                "addi SP " + paramNumber + "\n" +
                "pop\n" +
                "store FP 0(FP)\n" +
                "move FP AL\n" +
                "subi AL 1\n" +
                "pop\n" +
                "rsub RA\n";
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
