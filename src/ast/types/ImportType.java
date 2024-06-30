package ast.types;

/**
 * A type for the imported names.
 */
public class ImportType extends Type {

    @Override
    public String toPrint(String prefix) {
        return prefix + "Import\n";
    }
}
