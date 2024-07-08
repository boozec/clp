package ast.types;

import codegen.Label;

/**
 * A Function type.
 */
public class FunctionType extends Type {

    private final int paramNumber;
    private final Type returnType;
    private final String label;

    public FunctionType(int paramNumber, Type returnType, String label) {
        this.paramNumber = paramNumber;
        this.returnType = returnType;
        this.label = label;
    }

    // Return the length of the parameters
    public int getParamNumber() {
        return paramNumber;
    }

    public Type getReturnType() {
        return returnType;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "Function\n";
    }
}
