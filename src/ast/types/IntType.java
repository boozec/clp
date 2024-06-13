package com.clp.project.ast.types;

/**
 * An integer type.
 */
public class IntType extends Type {
    public String toPrint(String prefix) {
        return prefix + "Int\n";
    }
}
