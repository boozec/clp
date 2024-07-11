package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `import_stmt` statement of the grammar.
 */
public class ImportNode implements Node {

    private final Node dottedName;
    private final boolean isFrom;
    private final boolean importAs;
    private final boolean importAll;
    private final ArrayList<String> names;

    public ImportNode(Node dottedName, boolean isFrom, boolean importAs, boolean importAll,
            ArrayList<String> names) {
        this.dottedName = dottedName;
        this.isFrom = isFrom;
        this.importAs = importAs;
        this.importAll = importAll;
        this.names = names;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (isFrom) {
            for (int i = 0; i < names.size(); ++i) {
                ST.insert(names.get(i), this.typeCheck(), _nesting, null);
            }
        } else {
            errors.addAll(dottedName.checkSemantics(ST, _nesting, ft));
        }

        if (importAs) {
            ST.insert(names.get(names.size() - 1), this.typeCheck(), _nesting, null);
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return new ImportType();
    }

    /**
     * NOTE: we do not want to provide a code generation for this statement
     */
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String printAST(String prefix) {
        String str = prefix + "Import\n";

        prefix += "  ";
        if (isFrom) {
            str += prefix + "  From\n" + dottedName.printAST(prefix + "  ");
        } else {
            str += dottedName.printAST(prefix);
        }

        if (importAs) {
            str += prefix + "  As " + names.get(0) + "\n";
        }

        if (importAll) {
            str += prefix + "  All\n";
        }

        for (int i = 0; i < names.size(); ++i) {
            if (i == 0 && importAs) {
                continue;
            }

            str += prefix + names.get(i) + "\n";
        }

        str += "\n";
        return str;
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix;

        if (isFrom) {
            str += "from " + dottedName.toPrint("") + " import ";
            if (importAll) {
                str += "*";
            } else {
                str += names.get(0);
                for (int i = 1; i < names.size(); ++i) {
                    str += ", " + names.get(i);
                }
            }
        } else {
            str += "import " + dottedName.toPrint("");
            if (importAs) {
                str += " as " + names.get(0);
            }
        }
        return str;
    }

}
