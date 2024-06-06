package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class ParamdefNode extends AtomNode implements Node {
    public ParamdefNode(String val) {
        super(val);
    }

    @Override
    public String toPrint(String s) {
        return s + "Paramdef(" + val + ")\n";
    }
}
