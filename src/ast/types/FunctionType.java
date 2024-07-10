package ast.types;

/**
 * A Function type.
 */
public class FunctionType extends Type {

    private final int paramNumber;
    private final Type returnType;
    private final String label;
    private int localvarNum;

    public FunctionType(int paramNumber, Type returnType, String label) {
        this.paramNumber = paramNumber;
        this.returnType = returnType;
        this.label = label;
        this.localvarNum = 0;
    }

    public void addLocalVar() {
        localvarNum++;
    }

    public int getLocalvarNum() {
        return localvarNum;
    }

    // Return the number of the parameters
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

    @Override
    public String toString() {
        return "NP: " + paramNumber + " RT: " + returnType + " L: " + label;
    }
}
