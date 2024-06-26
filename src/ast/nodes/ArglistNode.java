package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `arglist` statement of the grammar.
 */
public class ArglistNode implements Node {

    protected ArrayList<Node> arguments;

    public ArglistNode(ArrayList<Node> arguments) {
        this.arguments = arguments;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (var arg : arguments) {
            ExprNode argExpr = (ExprNode) arg;
            String argName = argExpr.getId();

            // TODO: check fucking IntType for params
            // TODO: remove fucking comments
            if (argName != null && !ST.top_lookup(argName) && argExpr.typeCheck() instanceof AtomType) {
                // System.out.println(!(this.typeCheck() instanceof IntType) + " " + !ST.top_lookup(this.getId()));
                errors.add(new SemanticError("'" + argName + "' is not defined."));
            } else {
                errors.addAll(arg.checkSemantics(ST, _nesting));
            }
        }

        return errors;
    }

    public int getArgumentNumber() {
        return arguments.size();
    }

    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // TODO: add code generation for arglist node
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "ArglistNode\n";

        prefix += "  ";
        for (Node arg : arguments) {
            str += arg.toPrint(prefix);
        }

        return str;
    }

}
