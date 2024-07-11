package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    // very scatchy
    protected int ns;
    protected int offset;

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
        return val;
    }

    public void setId(String id) {
        this.val = id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (val != null) {
            if (!Arrays.asList(bif).contains(getId())) {
                if ((typeCheck() instanceof AtomType) && ST.nslookup(getId()) < 0) {
                    errors.add(new SemanticError("name '" + getId() + "' is not defined."));
                } else {
                    if ((typeCheck() instanceof AtomType)) {
                        int varNs = ST.lookup(getId()).getNesting();
                        this.ns = _nesting - varNs;
                        offset = ST.lookup(getId()).getOffset();
                    }
                }
            }
        }

        if (exprlist != null) {
            errors.addAll(exprlist.checkSemantics(ST, _nesting, ft));
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
        if (exprlist != null) {
            return exprlist.codeGeneration();
        }

        if (typeCheck() instanceof IntType) {
            return "storei A0 " + getId() + "\n";
        }

        if (typeCheck() instanceof BoolType) {
            return "storei A0 " + boolValue(getId()) + "\n";
        }

        if (typeCheck() instanceof AtomType) {
            // We need to ascend the access-link chain to the top (or bottom,
            // who knows).
            String str = "move AL T1\n";
            for (int i = 0; i < this.ns; i++) {
                str += "store T1 0(T1)\n";
            }

            str += "subi T1 " + offset + "\nstore A0 0(T1)\n";
            return str;
        }

        return "Error: could not parse an atom\n";
    }

    public static String boolValue(String id) {
        return id.equals("True") ? "1" : "0";
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
