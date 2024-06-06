package com.clp.project.ast.nodes;

import java.util.ArrayList;

import com.clp.project.semanticanalysis.SemanticError;
import com.clp.project.semanticanalysis.SymbolTable;
import com.clp.project.ast.types.*;

public class BlockNode extends RootNode implements Node {
    public BlockNode(ArrayList<Node> _stmts, ArrayList<Node> _compoundStmts) {
        super(_stmts, _compoundStmts);
    }

    @Override
    public String toPrint(String s) {
        String result = s + "Block\n";

        for (Node stmt : stmts) {
            result += stmt.toPrint(s + "  ");
        }
        for (Node stmt : compoundStmts) {
            result += stmt.toPrint(s + "  ");
        }

        return result;
    }

}
