package com.clp.project.ast;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;

public interface Node {

    ArrayList<SemanticError> checkSemantics(SymbolTable ST, int _nesting);

    Type typeCheck();

    String codeGeneration();

    String toPrint(String s);
}
