package semanticanalysis;

/**
 * Class respresents a semantic error.
 */
public class SemanticError {

    private final String msg;

    public SemanticError(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
