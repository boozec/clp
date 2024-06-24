package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Node for the `funcdef` statement of the grammar.
 */
public class FuncdefNode implements Node {
    private TerminalNode name;
    private Node paramlist;
    private Node block;

    public FuncdefNode(TerminalNode name, Node paramlist, Node block) {
        this.name = name;
        this.paramlist = paramlist;
        this.block = block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (paramlist != null) {
            errors.addAll(paramlist.checkSemantics(ST, _nesting));
        }

        errors.addAll(block.checkSemantics(ST, _nesting));

        return errors;
    }

    // FIXME: this type must be the same of the return stmt variable
    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: code generation for funcdef
    @Override
    public String codeGeneration() {
        return "";
    }

    public String toPrint(String prefix) {
        String str = prefix + "Funcdef(" + name + ")\n";

        prefix += "  ";

        if (paramlist != null) {
            str += paramlist.toPrint(prefix);
        }

        str += block.toPrint(prefix);

        return str;
    }

}
