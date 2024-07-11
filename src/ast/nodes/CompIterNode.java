package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `comp_iter` statement of the grammar.
 */
public class CompIterNode implements Node {

    protected CompForNode comp_for;

    public CompIterNode(Node comp_for) {
        this.comp_for = (CompForNode) comp_for;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (comp_for != null) {
            errors.addAll(comp_for.checkSemantics(ST, _nesting, ft));
        }
        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    /**
     * We do not want to provide the code generation for this stuff.
     */
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "CompIterNode\n";

        prefix += "  ";
        str += comp_for.printAST(prefix);
        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = "";
        str += comp_for.toPrint(prefix);
        return str;
    }

}
