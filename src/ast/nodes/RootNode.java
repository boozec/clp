package ast.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import semanticanalysis.*;
import ast.types.*;

/**
 * Node for the `root` statement of the grammar.
 */
public class RootNode implements Node {
    // stms and compundStmts are protected because they are reused for a
    // BlockNode
    protected ArrayList<Node> stmts;
    protected ArrayList<Node> compoundStmts;

    public RootNode(ArrayList<Node> stmts, ArrayList<Node> compoundStmts) {
        this.stmts = stmts;
        this.compoundStmts = compoundStmts;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        HashMap<String, STentry> HM = new HashMap<String, STentry>();

        ST.add(HM);

        for (Node stmt : compoundStmts) {
            errors.addAll(stmt.checkSemantics(ST, _nesting));
        }

        for (Node stmt : stmts) {
            errors.addAll(stmt.checkSemantics(ST, _nesting));
        }

        ST.remove();

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: Code generation for RootNode
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = "Root\n";

        prefix += "  ";

        for (Node stmt : stmts) {
            str += stmt.toPrint(prefix);
        }
        for (Node stmt : compoundStmts) {
            str += stmt.toPrint(prefix);
        }

        return str;
    }

}
