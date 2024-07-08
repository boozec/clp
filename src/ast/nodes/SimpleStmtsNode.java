package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `simple_stmts` statement of the grammar.
 */
public class SimpleStmtsNode implements Node {

    private final ArrayList<Node> stmts;

    public SimpleStmtsNode(ArrayList<Node> stmts) {
        this.stmts = stmts;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (Node stmt : stmts) {
            errors.addAll(stmt.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        String str = "";

        for (Node stmt : stmts) {
            str += stmt.codeGeneration();
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "SimpleStmts\n";

        prefix += "  ";

        for (Node stmt : stmts) {
            str += stmt.toPrint(prefix);
        }

        return str;
    }

}
