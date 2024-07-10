package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Node for the `trailer` statement of the grammar.
 */
public class TrailerNode implements Node {

    private final Node arglist;
    private final ArrayList<Node> exprs;
    private final TerminalNode methodCall;
    private final boolean isParenthesis;
    private final boolean isEmpty;

    public TrailerNode(Node arglist, ArrayList<Node> exprs, TerminalNode methodCall, boolean isParenthesis) {
        this.arglist = arglist;
        this.exprs = exprs;
        this.methodCall = methodCall;
        this.isParenthesis = isParenthesis;

        this.isEmpty = (this.arglist == null && this.exprs.isEmpty() && this.methodCall == null);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (arglist != null) {
            errors.addAll(arglist.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    public int getArgumentNumber() {
        if (arglist == null) {
            return 0;
        }

        return ((ArglistNode) arglist).getArgumentNumber();
    }

    public boolean isParenthesis() {
        return this.isParenthesis;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for trailer node
    @Override
    public String codeGeneration() {
        if (arglist != null) {
            return arglist.codeGeneration();
        }
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "TrailerNode\n";

        prefix += "  ";

        if (arglist != null) {
            str += arglist.toPrint(prefix);
        }

        for (var expr : exprs) {
            str += expr.toPrint(prefix);
        }

        if (methodCall != null) {
            str += prefix + "Method(" + methodCall + ")\n";
        }

        if (isEmpty) {
            str += prefix + "()\n";
        }

        return str;
    }

}
