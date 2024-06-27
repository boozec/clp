package ast.types;

/**
 * A type for the continue and break statements.
 */
public class ReservedWordsType extends Type {
    public String toPrint(String prefix) {
        return prefix + "ReservedWords\n";
    }
}
