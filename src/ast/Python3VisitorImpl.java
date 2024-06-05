package com.clp.project.ast;

import java.util.ArrayList;
import java.util.List;

import com.clp.project.ast.*;
import com.clp.project.ast.nodes.*;
import com.clp.project.ast.types.*;
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

        Node assignment = null;
        Node expr = null;

        if (ctx.assignment() != null) {
            assignment = visit(ctx.assignment());
        }

        if (ctx.expr() != null) {
            expr = visit(ctx.expr());
        }

        return new SimpleStmtNode(assignment, expr);
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
        Node lhr = visit(ctx.exprlist(0));
        Node assign = visit(ctx.augassign());
        Node rhr = visit(ctx.exprlist(1));

        return new AssignmentNode(lhr, assign, rhr);
    }

    public Node visitExprlist(ExprlistContext ctx) {
        Node exp = visit(ctx.expr(0));

        return exp;
    }

    public Node visitExpr(ExprContext ctx) {
        Node atom = null;
        Node compOp = null;
        ArrayList<Node> exprs = new ArrayList<Node>();
        ArrayList<Node> trailers = new ArrayList<Node>();

        if (ctx.atom() != null) {
            atom = visit(ctx.atom());
        }

        if (ctx.comp_op() != null) {
            compOp = visit(ctx.comp_op());
        }

        for (ExprContext s : ctx.expr()) {
            exprs.add(visit(s));
        }

        for (TrailerContext s : ctx.trailer()) {
            trailers.add(visit(s));
        }

        return new ExprNode(atom, compOp, exprs, trailers);
    }

    public Node visitAtom(AtomContext ctx) {
        if (ctx.NUMBER() != null) {
            return new AtomNode(ctx.NUMBER().toString());
        } else if (ctx.TRUE() != null) {
            return new AtomNode(ctx.TRUE().toString());
        } else if (ctx.FALSE() != null) {
            return new AtomNode(ctx.FALSE().toString());
        } else if (ctx.NAME() != null) {
            return new AtomNode(ctx.NAME().toString());
        } else if (ctx.STRING() != null) {

            var varName = "";
            for (var x : ctx.STRING()) {
                varName += x;
            }

            return new AtomNode(varName);
        }
        return new AtomNode(ctx.NONE().toString());
    }

    public Node visitAugassign(AugassignContext ctx) {
        return new AugassignNode(ctx.ASSIGN());
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

    public Node visitComp_op(Comp_opContext ctx) {
        return new CompNode(ctx.EQUALS());
    }

    public Node visitBlock(BlockContext ctx) {
        ArrayList<Node> stmts = new ArrayList<Node>();
        ArrayList<Node> compStmts = new ArrayList<Node>();

        for (Simple_stmtsContext s : ctx.simple_stmts()) {
            stmts.add(visit(s));
        }
        for (Compound_stmtContext s : ctx.compound_stmt()) {
            compStmts.add(visit(s));
        }

        return new BlockNode(stmts, compStmts);
    }

    public Node visitTrailer(TrailerContext ctx) {
        Node arglist = visit(ctx.arglist());
        return new TrailerNode(arglist);
    }

    public Node visitArglist(ArglistContext ctx) {
        ArrayList<Node> arguments = new ArrayList<Node>();

        for (ArgumentContext c : ctx.argument()) {
            arguments.add(visit(c));
        }

        return new ArglistNode(arguments);
    }
}
