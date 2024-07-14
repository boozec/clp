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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        return new ArrayList<>();
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        // We're just considering the `=` operation for this language.
        return "";
    }

    @Override
    public String printAST(String prefix) {
        return prefix + "Augassign(" + val + ")\n";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + val.toString();
        return str;
    }
}
