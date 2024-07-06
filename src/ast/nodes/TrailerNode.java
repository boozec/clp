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

    private String prefix;
    private String suffix;
    private final Node arglist;
    private final ArrayList<Node> exprs;
    private final TerminalNode methodCall;
    private final boolean isEmpty;

    public TrailerNode(Node arglist, ArrayList<Node> exprs, TerminalNode methodCall, String prefix, String suffix){
        this.arglist = arglist;
        this.exprs = exprs;
        this.methodCall = methodCall;
        this.prefix = prefix;
        this.suffix = suffix;

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
    public String printAST(String prefix) {
        String str = prefix + "TrailerNode\n";

        prefix += "  ";

        if (arglist != null) {
            str += arglist.printAST(prefix);
        }

        for (var expr : exprs) {
            str += expr.printAST(prefix);
        }

        if (methodCall != null) {
            str += prefix + "Method(" + methodCall + ")\n";
        }

        if (isEmpty) {
            str += prefix + "()\n";
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + this.prefix;

        if (arglist != null) {
            str += arglist.toPrint("");
        } else if (methodCall != null) {
            str += methodCall.getText();
        } else {
            for (var expr : exprs) {
                str += expr.toPrint("");
                if (exprs.indexOf(expr) != exprs.size() - 1) {
                    str += ", ";
                }
            }
        }

        str += this.suffix;
        return str;
    }

}
