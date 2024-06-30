package ast.types;

/**
 * A void type. Voids return nothing.
 */
public class VoidType extends Type {

    @Override
    public String toPrint(String prefix) {
        return prefix + "Void\n";
    }

    @Override
    public String toString() {
        return "Void";
    }
}
