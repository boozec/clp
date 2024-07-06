package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `simple_stmt` statement of the grammar.
 */
public class SimpleStmtNode implements Node {

    private final Node assignment;
    private final Node expr;
    private final Node returnStmt;
    private final Node importStmt;

    public SimpleStmtNode(Node assignment, Node expr, Node returnStmt, Node importStmt) {
        this.assignment = assignment;
        this.expr = expr;
        this.returnStmt = returnStmt;
        this.importStmt = importStmt;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (assignment != null) {
            errors.addAll(assignment.checkSemantics(ST, _nesting));
        }

        if (expr != null) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        if (returnStmt != null) {
            errors.addAll(returnStmt.checkSemantics(ST, _nesting));
        }

        if (importStmt != null) {
            errors.addAll(importStmt.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for SimpleStmtNode
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "SimpleStmt\n";

        prefix += "  ";

        if (assignment != null) {
            str += assignment.toPrint(prefix);
        }

        if (expr != null) {
            str += expr.toPrint(prefix);
        }

        if (returnStmt != null) {
            str += returnStmt.toPrint(prefix);
        }

        if (importStmt != null) {
            str += importStmt.toPrint(prefix);
        }

        return str;
    }

}
