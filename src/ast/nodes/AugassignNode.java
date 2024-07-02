package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Node for the `augassign` statement of the grammar.
 */
public class AugassignNode implements Node {

    private final TerminalNode val;

    public AugassignNode(TerminalNode val) {
        this.val = val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        return new ArrayList<>();
    }

    // FIXME: use the right type
    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for augassign node
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "Augassign(" + val + ")\n";
    }
}
