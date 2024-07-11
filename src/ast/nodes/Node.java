package ast.nodes;

import ast.types.*;
import java.util.ArrayList;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

/**
 * Base interface for a Node.
 */
public interface Node {

    static final String[] bif = {
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
        "exit",
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

    /**
     * Checks semantics for a given node for a SymbolTable ST and a level of
     * nesting. Returns a list of `SemanticError`.
     */
    ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting, FunctionType ft);

    /**
     * Checks the type for a given node. If there's any error, returns an
     * `ErrorType`.
     */
    Type typeCheck();

    /**
     * Returns a string for the Python Virtual Machine.
     */
    String codeGeneration();

    /**
     * Returns a string for a given node with a prefix. It used when an AST
     * wants to be visualized on screen.
     */
    String toPrint(String prefix);
}
