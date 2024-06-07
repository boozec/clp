package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;

import com.clp.project.ast.types.*;

/**
 * Node for the `paramdef` statement of the grammar. Extends the `AtomNode`
 * class.
 */
public class ParamdefNode extends AtomNode implements Node {
    public ParamdefNode(String val) {
        super(val);
    }

    // FIXME: it should returns the param' type
    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "Paramdef(" + val + ")\n";
    }
}
