package ast.types;

/**
 * A type for the imported names.
 */
public class ImportType extends Type {
    public String toPrint(String prefix) {
        return prefix + "Import\n";
    }
}

