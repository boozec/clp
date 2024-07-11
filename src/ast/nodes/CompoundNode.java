package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `compound_node` statement of the grammar.
 */
public class CompoundNode implements Node {

    private final Node ifNode;
    private final Node funcDef;
    private final Node forStmt;
    private final Node whileStmt;

    public CompoundNode(Node ifNode, Node funcDef, Node forStmt, Node whileStmt) {
        this.ifNode = ifNode;
        this.funcDef = funcDef;
        this.forStmt = forStmt;
        this.whileStmt = whileStmt;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (ifNode != null) {
            errors.addAll(ifNode.checkSemantics(ST, _nesting, ft));
        }

        if (funcDef != null) {
            errors.addAll(funcDef.checkSemantics(ST, _nesting, ft));
        }

        if (forStmt != null) {
            errors.addAll(forStmt.checkSemantics(ST, _nesting, ft));
        }

        if (whileStmt != null) {
            errors.addAll(whileStmt.checkSemantics(ST, _nesting, ft));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        if (ifNode != null) {
            return ifNode.codeGeneration();
        }

        if (funcDef != null) {
            return funcDef.codeGeneration();
        }

        if (forStmt != null) {
            return forStmt.codeGeneration();
        }

        if (whileStmt != null) {
            return whileStmt.codeGeneration();
        }

        return "Error: everything is null in Compound node\n";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "CompoundNode\n";

        prefix += "  ";

        if (ifNode != null) {
            str += ifNode.printAST(prefix);
        }

        if (funcDef != null) {
            str += funcDef.printAST(prefix);
        }

        if (forStmt != null) {
            str += forStmt.printAST(prefix);
        }

        if (whileStmt != null) {
            str += whileStmt.printAST(prefix);
        }

        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = "";

        if (ifNode != null) {
            str += ifNode.toPrint(prefix);
        }

        if (funcDef != null) {
            str += funcDef.toPrint(prefix);
        }

        if (forStmt != null) {
            str += forStmt.toPrint(prefix);
        }

        if (whileStmt != null) {
            str += whileStmt.toPrint(prefix);
        }

        return str;
        
    }

    public Node getForStmt() {
        return forStmt;
    }

    public Node getFuncDef() {
        return funcDef;
    }

    public Node getIfNode() {
        return ifNode;
    }

    public Node getWhileStmt() {
        return whileStmt;
    }
}
