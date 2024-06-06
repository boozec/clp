package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

public class FuncdefNode implements Node {
    private TerminalNode name;
    private Node paramlist;
    private Node block;

    public FuncdefNode(TerminalNode _name, Node _paramlist, Node _block) {
        name = _name;
        paramlist = _paramlist;
        block = _block;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(paramlist.checkSemantics(ST, _nesting));
        errors.addAll(block.checkSemantics(ST, _nesting));

        return errors;
    }

    public Type typeCheck() {
        return new VoidType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        String result = s + "Funcdef(" + name + ")\n";

        if (paramlist != null) {
            result += paramlist.toPrint(s + "  ");
        }

        result += block.toPrint(s + "  ");

        return result;
    }

}
