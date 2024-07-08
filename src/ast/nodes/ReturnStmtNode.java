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
    private int pm;

    public ReturnStmtNode(Node exprList) {
        this.exprList = exprList;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        String id = Label.getFuntrace();
        STentry ftEntry = ST.lookup(id);
        FunctionType ft = (FunctionType) ftEntry.getType();
        this.pm = ft.getParamNumber();
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
        String paramNS = String.valueOf(pm);
        return expS + "popr RA\naddi SP " + paramNS + "\npopr FP\nrsub RA\n";
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
