package ast.nodes;

import ast.types.*;

/**
 * Node for the `paramdef` statement of the grammar. Extends the `AtomNode`
 * class.
 */
public class ParamdefNode extends AtomNode {
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
