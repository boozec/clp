package com.clp.project.semanticanalysis;

import com.clp.project.ast.types.Type;

/**
 * Entry class for the symbol table.
 */
public class STentry {
    private Type type;
    private int offset;
    private int nesting;
    private String label;

    public STentry(Type type, int offset, int nesting) {
        this.type = type;
        this.offset = offset;
        this.nesting = nesting;
    }

    public STentry(Type type, int offset, int nesting, String label) {
        this.type = type;
        this.offset = offset;
        this.nesting = nesting;
        this.label = label;
    }

    /**
     * Getter for `type`
     */
    public Type getType() {
        return type;
    }

    /**
     * Getter for `offset`
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Getter for `nesting`
     */
    public int getNesting() {
        return nesting;
    }

    /**
     * Getter for `label`
     */
    public String getLabel() {
        return label;
    }

}
