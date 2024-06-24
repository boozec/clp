package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;

/**
 * Node for the `assignment` statement of the grammar.
 */
public class AssignmentNode implements Node {
    private Node lhr;
    private Node assign;
    private Node rhr;

    public AssignmentNode(Node lhr, Node assign, Node rhr) {
        this.lhr = lhr;
        this.assign = assign;
        this.rhr = rhr;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(lhr.checkSemantics(ST, _nesting));
        errors.addAll(assign.checkSemantics(ST, _nesting));
        errors.addAll(rhr.checkSemantics(ST, _nesting));

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

}
