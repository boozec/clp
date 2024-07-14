package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `paramdef` statement of the grammar. Extends the `AtomNode`
 * class.
 */
public class ParamdefNode extends AtomNode {

    public ParamdefNode(String val) {
        super(val, null);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        String paramName = this.getId();

        if (!ST.top_lookup(paramName)) {
            ST.insert(paramName, this.typeCheck(), _nesting, "");
        } else {
            errors.add(new SemanticError("Duplicate argument '" + paramName + "' in function definition."));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String printAST(String prefix) {
        return prefix + "Paramdef(" + val + ")\n";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + val;
    }
}
