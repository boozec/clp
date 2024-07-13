package ast.nodes;

import ast.types.*;
import codegen.Label;
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

    private final int startIndex;
    private final int endIndex;

    // Useful for code gen
    private int offset;
    private boolean alreadyDef;

    public AssignmentNode(Node lhr, Node assign, Node rhr, int startIndex, int endIndex) {
        this.lhr = (ExprListNode) lhr;
        this.assign = assign;
        this.rhr = (ExprListNode) rhr;
        this.alreadyDef = false;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // DO NOT CHECK lhr
        errors.addAll(assign.checkSemantics(ST, _nesting, ft));
        errors.addAll(rhr.checkSemantics(ST, _nesting, ft));

        int lsize = lhr.getSize();

        for (int i = 0; i < lsize; i++) {
            ExprNode leftAtom = (ExprNode) lhr.getElem(i);
            STentry e = ST.lookup(leftAtom.getId());
            if (ft != null) {
                ft.addLocalVar();
            }

            if (e == null) {
                Label.addGlobalVar();
                ST.insert(leftAtom.getId(), new AtomType(), _nesting, "");
                e = ST.lookup(leftAtom.getId());
            } else {
                int ns = e.getNesting();
                if (_nesting == ns) {
                    this.alreadyDef = true;
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

        String lhrString = "";
        ExprNode leftAtom = (ExprNode) lhr.getElem(0);

        // The code generation for the left atom returns a `store A0 0(T1)` at
        // the end but we do not want that command.
        // So, we'll have a string with the substring + the offset.
        String leftAtomCode = leftAtom.codeGeneration();
        lhrString += leftAtomCode.substring(0, leftAtomCode.length() - 17) + offset;

        // If the variable name is previously defined it'll load the variable
        // from the `0(T1)`, otherwise it'll push the `A0` register at the top
        // of the stack.
        if (!this.alreadyDef) {
            lhrString += "\npushr A0\n";
        } else {
            lhrString += "\nload A0 0(T1)\n";
        }

        return rhrString + lhrString;
    }

    @Override
    public String printAST(String prefix) {
        return prefix + "Assignment\n" + lhr.printAST(prefix + "  ") + assign.printAST(prefix + "  ")
                + rhr.printAST(prefix + "  ");
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix;
        str += lhr.toPrint("") + " " + assign.toPrint("") + " " + rhr.toPrint("");
        return str;
    }

    public ExprListNode getLhr() {
        return lhr;
    }

    public ExprListNode getRhr() {
        return rhr;
    }

    public int getLhrIndex() {
        return this.startIndex;
    }

    public int getRhrIndex() {
        return this.endIndex;
    }
}
