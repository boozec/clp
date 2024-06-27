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

    protected String val;

    public AtomNode(String val) {
        this.val = val;
    }

    public String getId() {
        return this.val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        var errors = new ArrayList<SemanticError>();

        if ((this.typeCheck() instanceof AtomType) && ST.nslookup(this.getId()) < 0) {
            errors.add(new SemanticError("'" + this.getId() + "' is not defined."));
        }

        return errors;
    }

    // ENHANCE: return more specific types
    @Override
    public Type typeCheck() {
        Pattern booleanVariable = Pattern.compile("^(True|False)$");
        Pattern continueBreakVariable = Pattern.compile("^(continue|break)$");
        // this regex should match every possible atom name written in this format: CHAR (CHAR | DIGIT)*
        Pattern simpleVariable = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$", Pattern.CASE_INSENSITIVE);
        
        Matcher booleanVariableMatcher = booleanVariable.matcher(this.val);
        Matcher continueBreakVariableMatcher = continueBreakVariable.matcher(this.val);
        Matcher simpleVariableMatcher = simpleVariable.matcher(this.val);
        
        boolean matchFoundBoolean = booleanVariableMatcher.find();
        boolean matchFoundContinueBreak = continueBreakVariableMatcher.find();
        boolean matchFoundSimpleVariable = simpleVariableMatcher.find();

        if (matchFoundBoolean) {
            return new BoolType();
        } else if (matchFoundContinueBreak) {
            return new ContinueBreakType();
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
    public String toPrint(String prefix) {
        if (val != null) {
            return prefix + "Atom(" + val + ")\n";
        }

        return prefix + "Atom(null)\n";

    }
}
