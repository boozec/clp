
package com.clp.project.semanticanalysis;

import com.clp.project.ast.Type;

public class STentry {
    private Type type;
    private int offset;
    private int nesting;
    private String label;

    public STentry(Type _type, int _offset, int _nesting) {
        type = _type;
        offset = _offset;
        nesting = _nesting;
    }

    public STentry(Type _type, int _offset, int _nesting, String _label) {
        type = _type;
        offset = _offset;
        nesting = _nesting;
        label = _label;
    }

    public Type gettype() {
        return type;
    }

    public int getoffset() {
        return offset;
    }

    public int getnesting() {
        return nesting;
    }

    public String getlabel() {
        return label;
    }

}
