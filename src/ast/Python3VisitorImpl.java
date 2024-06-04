package com.clp.project.ast;

import java.util.ArrayList;
import java.util.List;

import com.clp.project.parser.Python3ParserBaseVisitor;
import com.clp.project.parser.Python3Parser.*;

public class Python3VisitorImpl extends Python3ParserBaseVisitor<Node> {
    public Node visitRoot(RootContext ctx) {
        ArrayList<Node> stmts = new ArrayList<Node>();
        ArrayList<Node> compStmts = new ArrayList<Node>();

        for (Simple_stmtsContext s : ctx.simple_stmts()) {
            stmts.add(visit(s));
        }
        for (Compound_stmtContext s : ctx.compound_stmt()) {
            compStmts.add(visit(s));
        }

        return new RootNode(stmts, compStmts);
    }

    public Node visitSimple_stmts(Simple_stmtsContext ctx) {
        ArrayList<Node> stmts = new ArrayList<Node>();

        for (Simple_stmtContext stm : ctx.simple_stmt()) {
            stmts.add(visit(stm));
        }

        return new SimpleStmtsNode(stmts);
    }

    public Node visitSimple_stmt(Simple_stmtContext ctx) {
        Node exp = visit(ctx.expr());

        return exp;
    }

    public Node visitCompound_stmt(Compound_stmtContext ctx) {
        Node ifStmt = visit(ctx.if_stmt());
        // FIXME: adds below
        // Node whileStmt = visit(ctx.while_stmt());
        // Node forStmt = visit(ctx.for_stmt());
        // Node funcDef = visit(ctx.funcdef());
        //
        return new CompoundNode(ifStmt);
    }

    public Node visitAssignment(AssignmentContext ctx) {
        Node exp = visit(ctx.exprlist(1));
        return exp;
    }

    // FIXME: add support for `elif`
    public Node visitIf_stmt(If_stmtContext ctx) {
        var blocks = ctx.block();
        Node condExp = visit(ctx.expr(0));
        Node thenExp = visit(blocks.get(0));
        Node elseExp = null;
        if (blocks.size() > 1) {
            elseExp = visit(blocks.get(1));
        }

        return new IfNode(condExp, thenExp, elseExp);
    }
}
