package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `atom` statement of the grammar.
 */
public class AtomNode implements Node {

    protected String val;
    protected STentry entry;
    protected TestlistCompNode exprlist;

    public AtomNode(String val, Node exprlist) {
        this.val = val;
        this.exprlist = (TestlistCompNode) exprlist;
        this.entry = null;
    }

    /**
     * Returns the identifier of the `AtomNode` if it's not `null`, otherwise
     * returns `null`.
     */
    public String getId() {
        return val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (val != null) {
            if ((typeCheck() instanceof AtomType) && ST.nslookup(getId()) < 0) {
                errors.add(new SemanticError("name '" + getId() + "' is not defined."));
            } else {
                // System.out.println("exist " + typeCheck());
                entry = ST.lookup(getId());
            }
        }

        if (exprlist != null) {
            errors.addAll(exprlist.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    // ENHANCE: return more specific types
    @Override
    public Type typeCheck() {
        if (val == null) {
            return new VoidType();
        }

        Pattern noneVariable = Pattern.compile("^(None)$");
        Pattern booleanVariable = Pattern.compile("^(True|False)$");
        Pattern integerVariable = Pattern.compile("^[0-9]+$");
        Pattern reservedWords = Pattern.compile("^(continue|break|int|float)$");
        // this regex should match every possible atom name written in this format: CHAR
        // (CHAR | DIGIT)*
        Pattern simpleVariable = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$", Pattern.CASE_INSENSITIVE);

        Matcher noneVariableMatcher = noneVariable.matcher(val);
        Matcher booleanVariableMatcher = booleanVariable.matcher(val);
        Matcher integerVariableMatcher = integerVariable.matcher(val);
        Matcher reservedWordsMatcher = reservedWords.matcher(val);
        Matcher simpleVariableMatcher = simpleVariable.matcher(val);

        boolean matchFoundNone = noneVariableMatcher.find();
        boolean matchFoundBoolean = booleanVariableMatcher.find();
        boolean matchFoundInteger = integerVariableMatcher.find();
        boolean matchFoundContinueBreak = reservedWordsMatcher.find();
        boolean matchFoundSimpleVariable = simpleVariableMatcher.find();

        if (matchFoundBoolean) {
            return new BoolType();
        } else if (matchFoundInteger) {
            return new IntType();
        } else if (matchFoundContinueBreak) {
            return new ReservedWordsType();
        } else if (matchFoundNone) {
            return new NoneType();
        } else if (matchFoundSimpleVariable) {
            return new AtomType(); // could be a variable or a fuction
        } else {
            return new VoidType(); // could be any type of data
        }
    }

    @Override
    public String codeGeneration() {
        String base = "storei A0 ";

        if(exprlist != null) {
            return exprlist.codeGeneration();
        }
        if (typeCheck() instanceof IntType) {
            return base + getId() + "\n";
        }
        if (typeCheck() instanceof BoolType) {
            return base + boolValue(getId()) + "\n";
        }
        if (typeCheck() instanceof AtomType) {
            return base + "-" + String.valueOf(entry.getOffset()) + "(FP)\n" ;
        }
        return "Error: could not parse an atom\n";
    }

    public static String boolValue(String id) {
        return id == "True" ? "1" : "0";
    }

    @Override
    public String toPrint(String prefix) {
        // FIXME: can be a testlist_comp with two expr and two atoms
        if (val != null) {
            return prefix + "Atom(" + val + ")\n";
        }

        return prefix + "Atom(null)\n";

    }
}
