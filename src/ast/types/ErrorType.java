package ast.types;

/**
 * Error type.
 */
public class ErrorType extends Type {

    @Override
    public String toPrint(String prefix) {
        return prefix + "Error\n";
    }
}
