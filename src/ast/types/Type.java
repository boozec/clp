package ast.types;

import ast.nodes.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * A node which represents a type class.
 */
public class Type implements Node {

    public boolean isEqual(Type A, Type B) {
        return A.getClass().equals(B.getClass());
    }

    @Override
    public String toPrint(String s) {
        return s;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        // It is never invoked
        return null;
    }

    @Override
    public Type typeCheck() {
        // It is never invoked
        return null;
    }

    @Override
    public String codeGeneration() {
        // It is never invoked
        return "";
    }

}
