package ast.nodes;

import ast.types.*;
import java.util.ArrayList;

import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `assignment` statement of the grammar.
 */
public class AssignmentNode implements Node {

    private final ExprListNode lhr;
    private final Node assign;
    private final ExprListNode rhr;

    // useful for code gen
    private int offset;
    private boolean alreadyDef;

    public AssignmentNode(Node lhr, Node assign, Node rhr) {
        this.lhr = (ExprListNode) lhr;
        this.assign = assign;
        this.rhr = (ExprListNode) rhr;
        this.alreadyDef = false;
    }

        @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // DO NOT CHECK lhr
        errors.addAll(assign.checkSemantics(ST, _nesting, ft));
        errors.addAll(rhr.checkSemantics(ST, _nesting, ft));

        int lsize = lhr.getSize();

        for (int i = 0; i < lsize; i++) {
            ExprNode latom = (ExprNode) lhr.getElem(i);
            STentry e = ST.lookup(latom.getId());
            if (ft != null) {
                ft.addLocalVar();
            }
            if (e == null) {
                ST.insert(latom.getId(), new AtomType(), _nesting, "");
                e = ST.lookup(latom.getId());
            } else {
                int ns = e.getNesting();
                if (_nesting == ns) {
                    alreadyDef = true;
                }
            }
            offset = e.getOffset();
        }
        return errors;
    }

    // TODO: check it out for this type
    @Override
    public Type typeCheck() {
        return rhr.typeCheck();
    }

    @Override
    public String codeGeneration() {
        String rhrString = rhr.codeGeneration();

        String str = "";
        ExprNode latom = (ExprNode) lhr.getElem(0);
        // se si sta definendo una nuova variabile ci sarà sempre alla fine store A0
        // 0(T1)\n quindi sostituiamo store con load
        String whole = latom.codeGeneration();
        // TODO: DOCUMENTARE STA ROBA
        String withoutStore = whole.substring(0, whole.length() - 17);
        str += withoutStore + offset;
        if (!alreadyDef) {
            str += "\npushr A0\n";
        } else {
            str += "\nload A0 0(T1)\n";
        }
        return rhrString + str;
    }

    @Override
    public String toPrint(String prefix) {
        return prefix + "Assignment\n" + lhr.toPrint(prefix + "  ") + assign.toPrint(prefix + "  ")
                + rhr.toPrint(prefix + "  ");
    }

}
