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
        // System.out.println("[ATOM] id: " + getId() + " ns: " + _nesting + " top_lookup" + ST.top_lookup(this.getId()));
        if ((this.typeCheck() instanceof AtomType) && ST.nslookup(this.getId()) < 0) {
            // System.out.println(!(this.typeCheck() instanceof IntType) + " " + !ST.top_lookup(this.getId()));
            errors.add(new SemanticError("Undefined name `" + this.getId() + "`"));
        }

        return errors;
    }

    // ENHANCE: return more specific types
    @Override
    public Type typeCheck() {
        // this regex should match every possible atom name written in this format: CHAR (CHAR | DIGIT)*
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.val);
        boolean matchFound = matcher.find();
        if (matchFound) {
            // System.out.println("Match found for " + this.val);
            return new AtomType(); // could be a variable or a fuction
        } else {
            // System.out.println("Match not found for " + this.val);
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
