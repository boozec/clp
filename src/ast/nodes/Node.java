package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

/**
 * Base interface for a Node.
 */
public interface Node {
    /**
     * Checks semantics for a given node for a SymbolTable ST and a level of
     * nesting.
     * Returns a list of `SemanticError`.
     */
    ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting);

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
     * Returns a string for a given node with a prefix.
     * It used when an AST wants to be visualized on screen.
     */
    String toPrint(String prefix);
}
