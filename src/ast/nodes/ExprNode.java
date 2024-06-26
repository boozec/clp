package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import java.util.Arrays;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Node for the `expr` statement of the grammar.
 */
public class ExprNode implements Node {

    private AtomNode atom;
    private Node compOp;
    private String op;
    private ArrayList<Node> exprs;
    private ArrayList<Node> trailers;

    // built-in functions
    private static final String[] bif = {
        "abs",
        "aiter",
        "all",
        "anext",
        "any",
        "ascii",
        "bin",
        "bool",
        "breakpoint",
        "bytearray",
        "bytes",
        "callable",
        "chr",
        "classmethod",
        "compile",
        "complex",
        "delattr",
        "dict",
        "dir",
        "divmod",
        "enumerate",
        "eval",
        "exec",
        "filter",
        "float",
        "format",
        "frozenset",
        "getattr",
        "globals",
        "hasattr",
        "hash",
        "help",
        "hex",
        "id",
        "input",
        "int",
        "isinstance",
        "issubclass",
        "iter",
        "len",
        "list",
        "locals",
        "map",
        "max",
        "memoryview",
        "min",
        "next",
        "object",
        "oct",
        "open",
        "ord",
        "pow",
        "print",
        "property",
        "range",
        "repr",
        "reversed",
        "round",
        "set",
        "setattr",
        "slice",
        "sorted",
        "staticmethod",
        "str",
        "sum",
        "super",
        "tuple",
        "type",
        "vars",
        "zip",
        "__import__"};

    public ExprNode(Node atom, Node compOp, ArrayList<Node> exprs, String op, ArrayList<Node> trailers) {
        this.atom = (AtomNode) atom;
        this.compOp = compOp;
        this.exprs = exprs;
        this.op = op;
        this.trailers = trailers;
    }

    public String getId() {
        if (atom != null) {
            return ((AtomNode) this.atom).getId();
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if (atom != null && !trailers.isEmpty()) {
            // function call
            if (!Arrays.asList(bif).contains(atom.getId())) {
                errors.addAll(atom.checkSemantics(ST, _nesting));
                Node trailer = trailers.get(0);
                String funName = atom.getId();
                STentry s = ST.lookup(funName);
                if (s != null) {
                    FunctionType ft = (FunctionType) s.getType();
                    int paramNumber = ft.getParamNumber();
                    int argNumber = ((TrailerNode) trailer).getArgumentNumber();
                    if (paramNumber != argNumber) {
                        errors.add(new SemanticError(funName + "() takes " + String.valueOf(paramNumber) + " positional arguments but " + String.valueOf(argNumber) + " were given"));
                    }
                }
            } else {
                for (var trailer : trailers) {
                    errors.addAll(trailer.checkSemantics(ST, _nesting));
                }
            }
        } else if (atom != null) {
            errors.addAll(atom.checkSemantics(ST, _nesting));
        }

        if (compOp != null) {
            errors.addAll(compOp.checkSemantics(ST, _nesting));
        }

        for (var expr : exprs) {
            errors.addAll(expr.checkSemantics(ST, _nesting));
        }

        return errors;
    }

    // FIXME: type for the expr
    @Override
    public Type typeCheck() {
        if (this.atom != null) {
            return this.atom.typeCheck();
        }

        return new VoidType();
    }

    // TODO: add code generation for expr
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String prefix) {
        String str = prefix + "Expr\n";

        prefix += "  ";
        if (atom != null) {
            str += atom.toPrint(prefix);
        }

        if (compOp != null) {
            str += compOp.toPrint(prefix);
        }

        for (var expr : exprs) {
            str += expr.toPrint(prefix);
        }

        for (var trailer : trailers) {
            str += trailer.toPrint(prefix);
        }

        if (op != null) {
            str += prefix + "Op(" + op + ")\n";
        }

        return str;
    }

}
