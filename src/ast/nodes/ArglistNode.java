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
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (var arg : arguments) {
            if (arg instanceof ExprNode) {
                ExprNode argExpr = (ExprNode) arg;
                String argName = argExpr.getId();
                errors.addAll(arg.checkSemantics(ST, _nesting, ft));

                // TODO: check IntType for params
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
    public String printAST(String prefix) {
        String str = prefix + "ArglistNode\n";

        prefix += "  ";
        for (Node arg : arguments) {
            str += arg.printAST(prefix);
        }

        return str;
    }
    
    @Override
    public String toPrint(String prefix) {
        String str = prefix;
        str += arguments.get(0).toPrint("");
        
        if (arguments.size() > 1) {
            for (int i = 1; i < arguments.size(); i++) {
                str += ", " + arguments.get(i).toPrint("");
            }
        }

        return str;
    }

}