package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `atom` statement of the grammar.
 */
public class AtomNode implements Node {

    private String prefix;
    private String suffix;
    protected String val;
    protected TestlistCompNode exprlist;

    public AtomNode(String val, Node exprlist) {
        this(val, exprlist, "", "");
    }

    public AtomNode(String val, Node exprlist, String prefix, String suffix) {
        this.val = val;
        this.exprlist = (TestlistCompNode) exprlist;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * Returns the identifier of the `AtomNode` if it's not `null`, otherwise
     * returns `null`.
     */
    public String getId() {
        return this.val;
    }

    public void setId(String id) {
        this.val = id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (val != null) {
            if ((this.typeCheck() instanceof AtomType) && ST.nslookup(this.getId()) < 0) {
                errors.add(new SemanticError("name '" + this.getId() + "' is not defined."));
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
        if (this.val == null) {
            return new VoidType();
        }

        Pattern noneVariable = Pattern.compile("^(None)$");
        Pattern booleanVariable = Pattern.compile("^(True|False)$");
        Pattern reservedWords = Pattern.compile("^(continue|break|int|float)$");
        // this regex should match every possible atom name written in this format: CHAR
        // (CHAR | DIGIT)*
        Pattern simpleVariable = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$", Pattern.CASE_INSENSITIVE);

        Matcher noneVariableMatcher = noneVariable.matcher(this.val);
        Matcher booleanVariableMatcher = booleanVariable.matcher(this.val);
        Matcher reservedWordsMatcher = reservedWords.matcher(this.val);
        Matcher simpleVariableMatcher = simpleVariable.matcher(this.val);

        boolean matchFoundNone = noneVariableMatcher.find();
        boolean matchFoundBoolean = booleanVariableMatcher.find();
        boolean matchFoundContinueBreak = reservedWordsMatcher.find();
        boolean matchFoundSimpleVariable = simpleVariableMatcher.find();

        if (matchFoundBoolean) {
            return new BoolType();
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

    // TODO: add code generation for atom node
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        if (val != null) {
            return prefix + "AtomNode: " + val + "\n";
        } else {
            return prefix + "AtomNode\n" + exprlist.printAST(prefix + "  ");
        }
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + this.prefix;

        if (val != null) {
            str += val;
        } else {
            str += this.exprlist.toPrint("");
        }

        str += this.suffix;
        return str;
    }
}
