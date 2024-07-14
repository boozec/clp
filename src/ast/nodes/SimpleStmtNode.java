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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (assignment != null) {
            errors.addAll(assignment.checkSemantics(ST, _nesting, ft));
        }

        if (expr != null) {
            errors.addAll(expr.checkSemantics(ST, _nesting, ft));
        }

        if (returnStmt != null) {
            errors.addAll(returnStmt.checkSemantics(ST, _nesting, ft));
        }

        if (importStmt != null) {
            errors.addAll(importStmt.checkSemantics(ST, _nesting, ft));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        if (assignment != null) {
            return assignment.codeGeneration();
        }

        if (expr != null) {
            return expr.codeGeneration();
        }

        if (returnStmt != null) {
            return returnStmt.codeGeneration();
        }

        // Not supported
        // if (importStmt != null) {
        // return importStmt.codeGeneration();
        // }

        return "Error: everything is null in Compound node";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "SimpleStmt\n";

        prefix += "  ";

        if (assignment != null) {
            str += assignment.printAST(prefix);
        } else if (expr != null) {
            str += expr.printAST(prefix);
        } else if (returnStmt != null) {
            str += returnStmt.printAST(prefix);
        } else if (importStmt != null) {
            str += importStmt.printAST(prefix);
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        
        String str = prefix;

        if (assignment != null) {
            str += assignment.toPrint("");
        } else if (expr != null) {
            str += expr.toPrint("");
        } else if (returnStmt != null) {
            str += returnStmt.toPrint("");
        } else if (importStmt != null) {
            str += importStmt.toPrint("");
        }

        return str;
    }

    public Node getImportStmt() {
        return importStmt;
    }

    public Node getReturnStmt() {
        return returnStmt;
    }
    
    public Node getAssignment() {
        return assignment;
    }

    public Node getExpr() {
        return expr;
    }

}
