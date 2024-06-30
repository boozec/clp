package ast.types;

/**
 * An integer type.
 */
public class IntType extends Type {

    @Override
    public String toPrint(String prefix) {
        return prefix + "Int\n";
    }
}
