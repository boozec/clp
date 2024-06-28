package ast.types;

/**
 * A Function type.
 */
public class FunctionType extends Type {

    private int paramNumber;
    private Type returnType;

    public FunctionType(int paramNumber, Type returnType) {
        this.paramNumber = paramNumber;
        this.returnType = returnType;
    }

    // Return the length of the parameters
    public int getParamNumber() {
        return paramNumber;
    }

    public Type getReturnType() {
        return returnType;
    }

    public String toPrint(String prefix) {
        return prefix + "Function\n";
    }
}
