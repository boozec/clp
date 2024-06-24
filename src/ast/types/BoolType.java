package ast.types;

/**
 * A boolean type. A bool is True or False.
 */
public class BoolType extends Type {
    public String toPrint(String prefix) {
        return prefix + "Bool\n";
    }
}
