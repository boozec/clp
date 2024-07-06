package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `assignment` statement of the grammar.
 */
public class AssignmentNode implements Node {

    private final ExprListNode lhr;
    private final Node assign;
    private final ExprListNode rhr;

    public AssignmentNode(Node lhr, Node assign, Node rhr) {
        this.lhr = (ExprListNode) lhr;
        this.assign = assign;
        this.rhr = (ExprListNode) rhr;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // errors.addAll(lhr.checkSemantics(ST, _nesting));
        errors.addAll(assign.checkSemantics(ST, _nesting));
        errors.addAll(rhr.checkSemantics(ST, _nesting));

        int lsize = lhr.getSize();

        // FIXME: unused variable
        // int rsize = rhr.getSize();
        // if (lsize == rsize) {
        for (int i = 0; i < lsize; i++) {
            ExprNode latom = (ExprNode) lhr.getElem(i);
            ST.insert(latom.getId(), new AtomType(), _nesting, "");
            // ExprNode ratom = (ExprNode) rhr.getElem(i);
        }
        // } else {
        // FIX: sgravata da più problemi che altro
        // errors.add(new SemanticError("ValueError: different size of left or right side assignment"));
        // }

        return errors;
    }

    // TODO: check it out for this type
    @Override
    public Type typeCheck() {
        return rhr.typeCheck();
    }

    // TODO: add code generation for assignment
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "Assignment\n" + lhr.toPrint(prefix + "  ") + assign.toPrint(prefix + "  ")
                + rhr.toPrint(prefix + "  ");
    }

    public ExprListNode getLhr() {
        return lhr;
    }

    public ExprListNode getRhr() {
        return rhr;
    }
}
