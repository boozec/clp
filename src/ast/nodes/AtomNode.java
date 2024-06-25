package ast.nodes;

import java.util.ArrayList;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import ast.types.*;

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
        System.out.println(getId() + " " + _nesting + " " + ST.nslookup(getId()));
        if (!(this.typeCheck() instanceof IntType) && !ST.top_lookup(this.getId())) {
            System.out.println(!(this.typeCheck() instanceof IntType) + " " + !ST.top_lookup(this.getId()));
            errors.add(new SemanticError("Undefined name `" + this.getId() + "`"));
        }

        return errors;
    }

    // FIXME: this type for atom
    @Override
    public Type typeCheck() {
        try {
            Integer.parseInt(this.val);
            System.out.println(this.val + " is int");
            return new IntType();
        } catch (NumberFormatException e) {
            System.out.println(this.val + " is atom");
            return new AtomType();
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
