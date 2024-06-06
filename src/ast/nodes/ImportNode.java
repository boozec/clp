package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class ImportNode implements Node {
    private Node dottedName;
    private boolean isFrom;
    private boolean importAs;
    private boolean importAll;
    private ArrayList<String> names;

    public ImportNode(Node dottedName, boolean isFrom, boolean importAs, boolean importAll,
            ArrayList<String> names) {
        this.dottedName = dottedName;
        this.isFrom = isFrom;
        this.importAs = importAs;
        this.importAll = importAll;
        this.names = names;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        return errors;
    }

    public Type typeCheck() {
        return new VoidType();
    }

    public String codeGeneration() {
        return "";
    }

    public String toPrint(String s) {
        String result = s + "Import\n";

        if (isFrom) {
            result += s + "  From\n" + dottedName.toPrint(s + "    ");
        } else {
            result += dottedName.toPrint(s + "  ");
        }

        if (importAs) {
            result += s + "  As " + names.get(0) + "\n";
        }

        if (importAll) {
            result += s + "  All\n";
        }

        for (int i = 0; i < names.size(); ++i) {
            if (i == 0 && importAs)
                continue;

            result += s + "  " + names.get(i) + "\n";
        }

        result += "\n";
        return result;
    }

}
