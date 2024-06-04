package com.clp.project.semanticanalysis;

public class SemanticError {
    private String msg;

    public SemanticError(String _msg) {
        msg = _msg;
    }

    public String toString() {
        return msg;
    }
}
