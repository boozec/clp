package ast.types;

/**
 * An tom type.
 */
public class AtomType extends Type {

    @Override
    public String toPrint(String prefix) {
        return prefix + "Atom\n";
    }
}
