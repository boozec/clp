package ast.types;

/**
 * An tom type. TODO: do I need to use this one?
 */
public class FunctionType extends Type {

    private final int paramNumber;
    private final Type returnType;

    public FunctionType(int paramNumber, Type returnType) {
        this.paramNumber = paramNumber;
        this.returnType = returnType;
    }

    public int getParamNumber() {
        return paramNumber;
    }

    public Type getReturnType() {
        return returnType;
    }

    public String toPrint(String prefix) {
        return prefix + "Atom\n";
    }
}
