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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (comp != null) {
            // if comp is set, then we save the atom in the ST (we assume the first expr is
            // an atom)
            String id = ((ExprNode) exprs.get(0)).getId();
            Type t = ((ExprNode) exprs.get(0)).typeCheck();
            ST.insert(id, t, _nesting, "");
            // errors.addAll(comp.checkSemantics(ST, _nesting, ft));
        } else {
            // TODO: controllare per bene questo ramo e aggiungere commenti
            // if comp is not set, then exprs is a list of 1 or more element)
            for (var param : exprs) {
                var exp = (ExprNode) param; 
                if (exp.getId() != null && !exp.isFunctionCall()) {
                    ST.insert(exp.getId(), exp.typeCheck(), _nesting, "");
                }
                errors.addAll(param.checkSemantics(ST, _nesting, ft));
            }
        }

        return errors;
    }

    public int getSize() {
        return exprs.size();
    }

    /**
     * Returns the i-th expressions of `exprs` field. If the index is greater or
     * equals than the size return `null`.
     */
    public Node getElem(int i) {
        if (i >= this.exprs.size()) {
            return null;
        }
        return exprs.get(i);
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        String str = "";
        for (var param : exprs) {
            str += param.codeGeneration();
        }
        return str;
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
