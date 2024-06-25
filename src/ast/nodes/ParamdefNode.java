package ast.nodes;

import java.util.ArrayList;

import ast.types.*;
import semanticanalysis.*;

/**
 * Node for the `paramdef` statement of the grammar. Extends the `AtomNode`
 * class.
 */
public class ParamdefNode extends AtomNode {
    public ParamdefNode(String val) {
        super(val);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ST.insert(this.getId(), this.typeCheck(), _nesting, "");

        return new ArrayList<SemanticError>();
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
