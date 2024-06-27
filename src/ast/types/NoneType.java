package ast.types;

/**
 * A none type. None return unit.
 */
public class NoneType extends Type {

    public String toPrint(String prefix) {
        return prefix + "None\n";
    }

    @Override
    public String toString() {
        return "None";
    }
}