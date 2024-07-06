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

    public ParamlistNode(ArrayList<Node> params) {
        this.params = params;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (Node param : params) {
            errors.addAll(param.checkSemantics(ST, _nesting));
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
    public String printAST(String prefix) {
        String str = prefix + "Paramlist\n";

        prefix += "  ";
        for (Node param : params) {
            str += param.printAST(prefix);
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = "";

        for (int i = 0; i < params.size(); i++) {
            str += params.get(i).toPrint(prefix);
            if (i != params.size() - 1) {
                str += ", ";
            }
        }
        
        return str;
    }

}
