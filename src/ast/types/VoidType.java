package ast.types;

/**
 * A void type. Voids return nothing.
 */
public class VoidType extends Type {
    public String toPrint(String prefix) {
        return prefix + "Void\n";
    }
}
