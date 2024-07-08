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
        this.funLabel = Label.newFun("fun");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        int paramNumber = ((ParamlistNode) paramlist).getParamNumber();
        Type returnType = this.block.typeCheck();
        FunctionType ft = new FunctionType(paramNumber, returnType, funLabel);

        ST.insert(this.name.toString(), ft, _nesting, "");

        HashMap<String, STentry> HM = new HashMap<>();

        ST.add(HM);

        ST.insert(this.name.toString(), ft, _nesting + 1, "");

        if (paramlist != null) {
            errors.addAll(paramlist.checkSemantics(ST, _nesting + 1));
        }

        // TODO: think to the fucking offset
        // Offset is increased for the possible return value
        ST.increaseoffset();

        errors.addAll(block.checkSemantics(ST, _nesting + 1));

        ST.remove();

        return errors;
    }

    // FIXME: this type must be the same of the return stmt variable
    @Override
    public Type typeCheck() {
        return new VoidType();
    }

    // taken from slide 56 of CodeGeneration.pdf 
    @Override
    public String codeGeneration() {
        int paramNumber = ((ParamlistNode) paramlist).getParamNumber();
        String paramNS = String.valueOf(paramNumber);
        String blockS = block.codeGeneration();
        String skipL = Label.newBasic("skip");
        // nel block c'è il return che mette a posto l'RA
        return  "b " + skipL + "\n" + funLabel + ":\npushr RA\n" + blockS + skipL + ":\n";
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
