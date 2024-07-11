package ast.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;
import codegen.Label;

/**
 * Node for the `funcdef` statement of the grammar.
 */
public class FuncdefNode implements Node {

    private final TerminalNode name;
    private final Node paramlist;
    private final Node block;
    private final String funLabel;

    public FuncdefNode(TerminalNode name, Node paramlist, Node block) {
        this.name = name;
        this.paramlist = paramlist;
        this.block = block;
        this.funLabel = Label.newFun("FUN");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        int paramNumber = 0;
        if (paramlist != null) {
            paramNumber = ((ParamlistNode) paramlist).getParamNumber();
        }
        Type returnType = this.block.typeCheck();
        FunctionType newFt = new FunctionType(paramNumber, returnType, funLabel);

        String funName = this.name.toString();
        ST.insert(funName, newFt, _nesting, "");

        HashMap<String, STentry> HM = new HashMap<>();

        ST.add(HM);

        ST.insert(funName, newFt, _nesting + 1, "");
        ST.decreaseOffset();

        if (paramlist != null) {
            errors.addAll(paramlist.checkSemantics(ST, _nesting + 1, newFt));
        }

        // Offset is increased for the possible return value
        ST.increaseOffset();

        errors.addAll(block.checkSemantics(ST, _nesting + 1, newFt));

        // return to the outer block
        ST.remove();

        return errors;
    }

    // FIXME: this type must be the same of the return stmt variable
    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    /**
     * Taken from slide 56 of CodeGeneration.pdf
     */
    @Override
    public String codeGeneration() {
        String blockS = block.codeGeneration();
        // The "return" which fix the RA is inside the block
        String funS = funLabel + ":\n" +
                "pushr RA\n" +
                blockS;

        Label.addFunDef(funS);
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Funcdef(" + name + ")\n";

        prefix += "  ";

        if (paramlist != null) {
            str += paramlist.toPrint(prefix);
        }

        str += block.toPrint(prefix);

        return str;
    }

}
