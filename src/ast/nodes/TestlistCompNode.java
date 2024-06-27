package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `testlist_comp` statement of the grammar.
 */
public class TestlistCompNode implements Node {

    private final ArrayList<Node> exprs;
    private final CompForNode comp;

    public TestlistCompNode(ArrayList<Node> exprs, Node comp) {
        this.exprs = exprs;
        this.comp = (CompForNode) comp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList();

        if (comp != null) {
            // if comp is set, then we save the atom in the ST (we assume the first expr is an atom)
            String id = ((ExprNode) exprs.get(0)).getId();
            Type t = ((ExprNode) exprs.get(0)).typeCheck();
            ST.insert(id, t, _nesting, "");
            // errors.addAll(comp.checkSemantics(ST, _nesting));
        } else {
            // if comp is not set, then exprs is a list of 1 or more element
            for (var param : exprs) {
                errors.addAll(param.checkSemantics(ST, _nesting));
            }
        }

        return errors;
    }

    public int getSize() {
        return exprs.size();
    }

    public Node getElem(int i) {
        return exprs.get(i);
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: code generation for expr list
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Testlist_comp\n";

        prefix += "  ";
        for (var param : exprs) {
            str += param.toPrint(prefix);
        }

        return str;
    }

}
