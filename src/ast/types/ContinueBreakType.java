package ast.types;

/**
 * A type for the continue and break statements.
 */
public class ContinueBreakType extends Type {
    public String toPrint(String prefix) {
        return prefix + "ContinueBreak\n";
    }
}
