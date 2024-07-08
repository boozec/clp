package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `return_stmt` statement of the grammar.
 */
public class ReturnStmtNode implements Node {

    private final Node exprList;

    public ReturnStmtNode(Node exprList) {
        this.exprList = exprList;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

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
        // dovrei mettere paramNumberString ma non so come recuperarlo
        // "popr RA\naddi SP " + paramNS come sfaccimma facciamo + "\npopr FP\nrsub RA\n"
        return "popr RA\naddi SP 1\npopr FP\nrsub RA\n";
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
