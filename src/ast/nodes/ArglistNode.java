package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (var arg : arguments) {
            if (arg instanceof ExprNode) {
                ExprNode argExpr = (ExprNode) arg;
                String argName = argExpr.getId();

                // TODO: check fucking IntType for params
                // TODO: remove fucking comments
                if (argName != null) {
                    if (Arrays.asList(bif).contains(argName)) {
                        continue;
                    }

                    if (ST.lookup(argName) != null && ST.lookup(argName).getType() instanceof ImportType) {
                        continue;
                    }

                    if (ST.nslookup(argName) < 0 && argExpr.typeCheck() instanceof AtomType) {
                        errors.add(new SemanticError("name '" + argName + "' is not defined."));
                    }
                }
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

    @Override
    public String codeGeneration() {
        String str = "";
        for (Node arg : arguments) {
            str += arg.codeGeneration() + "pushr A0\n";
        }
        return str;
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
