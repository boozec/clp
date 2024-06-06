package com.clp.project.ast;

import java.util.ArrayList;
import java.util.List;

import com.clp.project.ast.*;
import com.clp.project.ast.nodes.*;
import com.clp.project.ast.types.*;
import com.clp.project.parser.Python3ParserBaseVisitor;
import com.clp.project.parser.Python3Parser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

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
        Node importStmt = null;
        Node returnStmt = null;

        if (ctx.assignment() != null) {
            assignment = visit(ctx.assignment());
        }

        if (ctx.expr() != null) {
            expr = visit(ctx.expr());
        }

        if (ctx.import_stm() != null) {
            importStmt = visit(ctx.import_stm());
        }

        if (ctx.return_stmt() != null) {
            returnStmt = visit(ctx.return_stmt());
        }

        return new SimpleStmtNode(assignment, expr, importStmt, returnStmt);
    }

    public Node visitCompound_stmt(Compound_stmtContext ctx) {
        Node ifStmt = null;
        if (ctx.if_stmt() != null) {
            ifStmt = visit(ctx.if_stmt());
        }

        Node funcDef = null;
        if (ctx.funcdef() != null) {
            funcDef = visit(ctx.funcdef());
        }

        Node forStmt = null;
        if (ctx.for_stmt() != null) {
            forStmt = visit(ctx.for_stmt());
        }

        Node whileStmt = null;
        if (ctx.while_stmt() != null) {
            whileStmt = visit(ctx.while_stmt());
        }
        return new CompoundNode(ifStmt, funcDef, forStmt, whileStmt);
    }

    public Node visitAssignment(AssignmentContext ctx) {
        Node lhr = visit(ctx.exprlist(0));
        Node assign = visit(ctx.augassign());
        Node rhr = visit(ctx.exprlist(1));

        return new AssignmentNode(lhr, assign, rhr);
    }

    public Node visitExprlist(ExprlistContext ctx) {
        // FIXME: you've used to be a list, c'mon
        Node exp = visit(ctx.expr(0));

        return exp;
    }

    public Node visitExpr(ExprContext ctx) {
        Node atom = null;
        Node compOp = null;
        ArrayList<Node> exprs = new ArrayList<Node>();
        ArrayList<Node> trailers = new ArrayList<Node>();
        String op = null;

        if (ctx.ADD(0) != null) {
            op = ctx.ADD(0).toString();
        }

        if (ctx.MINUS(0) != null) {
            op = ctx.MINUS(0).toString();
        }

        if (ctx.NOT() != null) {
            op = ctx.NOT().toString();
        }

        if (ctx.STAR() != null) {
            op = ctx.STAR().toString();
        }

        if (ctx.DIV() != null) {
            op = ctx.DIV().toString();
        }

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

        return new ExprNode(atom, compOp, exprs, op, trailers);
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
        Node comp = null;
        if (ctx.LESS_THAN() != null) {
            comp = new CompNode(ctx.LESS_THAN());
        } else if (ctx.GREATER_THAN() != null) {
            comp = new CompNode(ctx.GREATER_THAN());
        } else if (ctx.EQUALS() != null) {
            comp = new CompNode(ctx.EQUALS());
        } else if (ctx.GT_EQ() != null) {
            comp = new CompNode(ctx.GT_EQ());
        } else if (ctx.LT_EQ() != null) {
            comp = new CompNode(ctx.LT_EQ());
        } else if (ctx.NOT_EQ_2() != null) {
            // We're ignoring NOT_EQ_1() because no one uses `<>`
            comp = new CompNode(ctx.NOT_EQ_2());
        } else if (ctx.IN() != null) {
            comp = new CompNode(ctx.IN());
        } else if (ctx.NOT() != null) {
            comp = new CompNode(ctx.NOT());
        } else if (ctx.IS() != null) {
            comp = new CompNode(ctx.IS());
        }

        return comp;
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
        Node arglist = null;
        if (ctx.arglist() != null) {
            arglist = visit(ctx.arglist());
        }

        ArrayList<Node> exprs = new ArrayList<Node>();
        for (ExprContext expr : ctx.expr()) {
            exprs.add(visit(expr));
        }

        TerminalNode methodCall = null;
        if (ctx.DOT() != null) {
            methodCall = ctx.NAME();
        }

        return new TrailerNode(arglist, exprs, methodCall);
    }

    public Node visitArglist(ArglistContext ctx) {
        ArrayList<Node> arguments = new ArrayList<Node>();

        for (ArgumentContext c : ctx.argument()) {
            arguments.add(visit(c));
        }

        return new ArglistNode(arguments);
    }

    public Node visitFuncdef(FuncdefContext ctx) {
        Node paramlist = visit(ctx.paramlist());
        Node block = visit(ctx.block());

        return new FuncdefNode(ctx.NAME(), paramlist, block);
    }

    public Node visitParamlist(ParamlistContext ctx) {
        ArrayList<Node> params = new ArrayList<Node>();

        for (ParamdefContext s : ctx.paramdef()) {
            params.add(visit(s));
        }

        return new ParamlistNode(params);
    }

    public Node visitParamdef(ParamdefContext ctx) {
        return new ParamdefNode(ctx.NAME().toString());
    }

    public Node visitReturn_stmt(Return_stmtContext ctx) {
        Node exprList = null;
        if (ctx.exprlist() != null) {
            exprList = visit(ctx.exprlist());
        }

        return new ReturnStmtNode(exprList);
    }

    public Node visitFor_stmt(For_stmtContext ctx) {
        Node exprList = visit(ctx.exprlist());

        // Block 1 is for the for-else statement
        Node block = visit(ctx.block(0));

        return new ForStmtNode(exprList, block);
    }

    public Node visitWhile_stmt(While_stmtContext ctx) {
        Node expr = visit(ctx.expr());

        // Block 1 is for the while-else statement
        Node block = visit(ctx.block(0));

        return new WhileStmtNode(expr, block);
    }

    public Node visitImport_stm(Import_stmContext ctx) {
        boolean isFrom = ctx.FROM() != null;
        boolean importAs = ctx.AS() != null;
        boolean importAll = ctx.STAR() != null;

        Node dottedName = visit(ctx.dotted_name());

        ArrayList<String> names = new ArrayList<String>();

        for (var s : ctx.NAME()) {
            names.add(s.toString());
        }

        return new ImportNode(dottedName, isFrom, importAs, importAll, names);
    }

    public Node visitDotted_name(Dotted_nameContext ctx) {
        ArrayList<TerminalNode> names = new ArrayList<TerminalNode>();

        for (var name : ctx.NAME()) {
            names.add(name);
        }

        return new DottedNameNode(names);
    }
}
