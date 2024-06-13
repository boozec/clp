package com.clp.project.ast.types;

/**
 * An tom type.
 * TODO: do I need to use this one?
 */
public class AtomType extends Type {
    public String toPrint(String prefix) {
        return prefix + "Atom\n";
    }
}
