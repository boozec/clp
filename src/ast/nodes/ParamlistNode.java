package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `param_list` statement of the grammar.
 */
public class ParamlistNode implements Node {

    private final ArrayList<Node> params;

    public ParamlistNode(ArrayList<Node> _params) {
        params = _params;
    }

        @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (var param : params) {
            errors.addAll(param.checkSemantics(ST, _nesting, ft));
        }

        return errors;
    }

    public int getParamNumber() {
        return params.size();
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: code generation for param list
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Paramlist\n";

        prefix += "  ";
        for (var param : params) {
            str += param.toPrint(prefix);
        }

        return str;
    }

}
