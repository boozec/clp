package com.clp.project.ast;

public class ErrorType extends Type {
    public String toPrint(String s) {
        return s + "Error\n";
    }
}
