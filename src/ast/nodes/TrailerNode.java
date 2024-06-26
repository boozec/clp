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

    private Node arglist;
    private ArrayList<Node> exprs;
    private TerminalNode methodCall;
    private boolean isEmpty;

    public TrailerNode(Node arglist, ArrayList<Node> exprs, TerminalNode methodCall) {
        this.arglist = arglist;
        this.exprs = exprs;
        this.methodCall = methodCall;

        this.isEmpty = (this.arglist == null && this.exprs.size() == 0 && this.methodCall == null);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (arglist != null) {
            errors.addAll(arglist.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    public int getArgumentNumber() {
        return ((ArglistNode) arglist).getArgumentNumber();
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for trailer node
    @Override
    public String codeGeneration() {
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
