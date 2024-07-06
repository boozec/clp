package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `for_stmt` statement of the grammar.
 */
public class ForStmtNode implements Node {

    private final Node exprList;
    private final Node block;

    public ForStmtNode(Node exprList, Node block) {
        this.exprList = exprList;
        this.block = block;
    }

    /**
     * This methods check the semantics of the operation `for i in list: block`.
     *
     * After the `for` keyword, it's parsed as a list of expression. We verified
     * that the last expression is always gonna be `expr comp_op expr`. We
     * comp_op must be the `in` keyword. `i` could be of any lenght (e.g. `a, b,
     * c`).
     *
     * In this function we define the following approch: we save in the
     * SymbolTable every atom until the last expression in the expression's
     * list. For the last element, we know that is in the `expr comp_op expr`
     * format, so we take the left element and save it in the SymbolicTable as
     * an atom.
     */
    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // Save every atom in the expression's list, except the last one
        var l = (ExprListNode) exprList;
        for (int i = 0; i < l.getSize() - 1; ++i) {
            var e = (ExprNode) l.getElem(i);
            ST.insert(e.getId(), e.typeCheck(), _nesting, "");
        }

        // Manage the last expression of expression's list
        // Get the ExprNode
        var left = (ExprNode) l.getElem(l.getSize() - 1);
        // Get the left side expression of the operation, that
        // corresponds to the first element of the expression list.
        var atomLeft = (ExprNode) left.getExpr(0);
        // ENHANCE: check that the comp_op is the `in` keyword
        ST.insert(atomLeft.getId(), atomLeft.typeCheck(), _nesting, "");

        errors.addAll(exprList.checkSemantics(ST, _nesting));
        errors.addAll(block.checkSemantics(ST, _nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for while
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        return prefix + "For\n" + exprList.printAST(prefix + "  ") + block.printAST(prefix + "  ");
    }

    @Override
    public String toPrint(String prefix) {
        
        // TODO: indicare che diamo per assunto il for su più righe
        String str = prefix + "for ";
        str += exprList.toPrint("") + ":\n";
        str += block.toPrint(prefix + "\t");
        return str;
    } 

    public Node getBlock() {
        return block;
    }

    public Node getExprList() {
        return exprList;
    }
}
