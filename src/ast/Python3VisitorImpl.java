package com.clp.project.ast;

import java.util.ArrayList;
import java.util.List;

import com.clp.project.ast.*;
import com.clp.project.ast.nodes.*;
import com.clp.project.ast.types.*;
import com.clp.project.parser.Python3ParserBaseVisitor;
import com.clp.project.parser.Python3Parser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Implementation for the `Python3ParserBaseVisitor` class for the `Node` type.
 * Overrides each `visitNODE` method from the base class.
 */
public class Python3VisitorImpl extends Python3ParserBaseVisitor<Node> {
    /**
     * Since a root can be a simple_stmts or a compound_stmt, this method
     * returns a new `RootNode` with a list of them.
     *
     * ```
     * root : NEWLINE* (simple_stmts | compound_stmt)* EOF;
     * ```
     */
    public Node visitRoot(RootContext ctx) {
        ArrayList<Node> stmts = new ArrayList<Node>();
        ArrayList<Node> compStmts = new ArrayList<Node>();

        for (Simple_stmtsContext stm : ctx.simple_stmts()) {
            stmts.add(visit(stm));
        }
        for (Compound_stmtContext stm : ctx.compound_stmt()) {
            compStmts.add(visit(stm));
        }

        return new RootNode(stmts, compStmts);
    }

    /**
     * Returns a `SimpleStmtsNode`, made by an array of SimpleStmtNode
     *
     * ```
     * simple_stmts : simple_stmt (';' simple_stmt)* ';'? NEWLINE ;
     * ```
     */
    public Node visitSimple_stmts(Simple_stmtsContext ctx) {
        ArrayList<Node> stmts = new ArrayList<Node>();

        for (Simple_stmtContext stm : ctx.simple_stmt()) {
            stmts.add(visit(stm));
        }

        return new SimpleStmtsNode(stmts);
    }

    /**
     * Returns a `CompoundNode`. It can be built by a different kind of
     * statements, so only one of theme won't be null.
     *
     * ```
     * compound_stmt : if_stmt | while_stmt | for_stmt | funcdef ;
     * ```
     */
    public Node visitCompound_stmt(Compound_stmtContext ctx) {
        Node ifStmt = null;
        Node funcDef = null;
        Node forStmt = null;
        Node whileStmt = null;

        if (ctx.if_stmt() != null) {
            ifStmt = visit(ctx.if_stmt());
        }

        if (ctx.funcdef() != null) {
            funcDef = visit(ctx.funcdef());
        }

        if (ctx.for_stmt() != null) {
            forStmt = visit(ctx.for_stmt());
        }

        if (ctx.while_stmt() != null) {
            whileStmt = visit(ctx.while_stmt());
        }

        return new CompoundNode(ifStmt, funcDef, forStmt, whileStmt);
    }

    /**
     * Returns a `SimpleStmtNode`. It can be built by a different kind of
     * statements, so only one of theme won't be null.
     *
     * ```
     * simple_stmt : assignment | expr | return_stmt | import_stm ;
     * ```
     */
    public Node visitSimple_stmt(Simple_stmtContext ctx) {
        Node assignment = null;
        Node expr = null;
        Node returnStmt = null;
        Node importStmt = null;

        if (ctx.assignment() != null) {
            assignment = visit(ctx.assignment());
        }

        if (ctx.expr() != null) {
            expr = visit(ctx.expr());
        }

        if (ctx.return_stmt() != null) {
            returnStmt = visit(ctx.return_stmt());
        }

        if (ctx.import_stm() != null) {
            importStmt = visit(ctx.import_stm());
        }

        return new SimpleStmtNode(assignment, expr, returnStmt, importStmt);
    }

    /**
     * Returns an `AssignmentNode`. It's made by left side, an assignment and a
     * right side.
     * 
     * ```
     * assignment : exprlist augassign exprlist ;
     * ```
     */
    public Node visitAssignment(AssignmentContext ctx) {
        Node lhr = visit(ctx.exprlist(0));
        Node assign = visit(ctx.augassign());
        Node rhr = visit(ctx.exprlist(1));

        return new AssignmentNode(lhr, assign, rhr);
    }

    /**
     * Returns a `ReturnStmtNode`. The returned exprlist can be null.
     *
     * ```
     * return_stmt : 'return' exprlist? ;
     * ```
     */
    public Node visitReturn_stmt(Return_stmtContext ctx) {
        Node exprList = null;
        if (ctx.exprlist() != null) {
            exprList = visit(ctx.exprlist());
        }

        return new ReturnStmtNode(exprList);
    }

    /**
     * Returns a `ImportNode`. An import can be made in different ways so we
     * check the way in a module is imported (by from, by alias or by star).
     *
     * ```
     * import_stm : 'import' dotted_name ('as' NAME)?
     * | 'from' dotted_name 'import' (NAME (',' NAME)* | '*') ;
     * ```
     */
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

    /**
     * Returns a `DottedNameNode` used in `import_stm`.
     *
     * ```
     * dotted_name : NAME ('.' NAME)* ;
     * ```
     */
    public Node visitDotted_name(Dotted_nameContext ctx) {
        ArrayList<TerminalNode> names = new ArrayList<TerminalNode>();

        for (var name : ctx.NAME()) {
            names.add(name);
        }

        return new DottedNameNode(names);
    }

    /**
     * Returns a `FuncdefNode`. A paramlist can be null.
     *
     * ```
     * funcdef : 'def' NAME '(' paramlist? ')' ':' block ;
     * ```
     */
    public Node visitFuncdef(FuncdefContext ctx) {
        Node paramlist = null;

        if (ctx.paramlist() != null) {
            paramlist = visit(ctx.paramlist());
        }

        Node block = visit(ctx.block());

        return new FuncdefNode(ctx.NAME(), paramlist, block);
    }

    /**
     * Returns a `ParamlistNode`. We ignore the paramdef with default values
     * (eg: is_used=False) because there is no test which uses this feature.
     *
     * ```
     * paramlist : paramdef ('=' expr)? (',' paramdef ('=' expr)?)* ;
     * 
     * ```
     */
    public Node visitParamlist(ParamlistContext ctx) {
        ArrayList<Node> params = new ArrayList<Node>();

        for (ParamdefContext s : ctx.paramdef()) {
            params.add(visit(s));
        }

        return new ParamlistNode(params);
    }

    /**
     * Returns a `ParamdefNode`. We ignore the paramdef with type annotation
     * (eg: x : int) because there is no test which uses this feature.
     *
     * ```
     * paramdef : NAME (':' expr)? ;
     * ```
     */
    public Node visitParamdef(ParamdefContext ctx) {
        return new ParamdefNode(ctx.NAME().toString());
    }

    /**
     * Returns an `AugassignNode`. We don't provide all kinds of assignment
     * below.
     *
     * ```
     * augassign : '=' | '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' |
     * '^=' | '<<=' | '>>=' | '**=' | '//='
     * ;
     * ```
     */
    public Node visitAugassign(AugassignContext ctx) {
        Node x = null;

        if (ctx.ASSIGN() != null) {
            x = new AugassignNode(ctx.ASSIGN());
        } else if (ctx.ADD_ASSIGN() != null) {
            x = new AugassignNode(ctx.ADD_ASSIGN());
        } else if (ctx.SUB_ASSIGN() != null) {
            x = new AugassignNode(ctx.SUB_ASSIGN());
        } else if (ctx.MULT_ASSIGN() != null) {
            x = new AugassignNode(ctx.MULT_ASSIGN());
        } else if (ctx.DIV_ASSIGN() != null) {
            x = new AugassignNode(ctx.DIV_ASSIGN());
        }

        return x;
    }

    /**
     * Returns a `IfNode`.
     * FIXME: add support for elif statement.
     *
     * ```
     * if_stmt : 'if' expr ':' block ('elif' expr ':' block)* ('else' ':' block)? ;
     * ```
     */
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

    /**
     * Returns a `WhileStmtNode`. We do not provide 'else' branch.
     *
     * ```
     * while_stmt : 'while' expr ':' block ('else' ':' block)? ;
     * ```
     */
    public Node visitWhile_stmt(While_stmtContext ctx) {
        Node expr = visit(ctx.expr());

        // Block 1 is for the while-else statement
        Node block = visit(ctx.block(0));

        return new WhileStmtNode(expr, block);
    }

    /**
     * Returns a `ForSmtNode`. We do not provide 'else' branch.
     *
     * ```
     * for_stmt : 'for' exprlist ':' block ('else' ':' block)? ;
     * ```
     */
    public Node visitFor_stmt(For_stmtContext ctx) {
        Node exprList = visit(ctx.exprlist());

        // Block 1 is for the for-else statement
        Node block = visit(ctx.block(0));

        return new ForStmtNode(exprList, block);
    }

    /**
     * Returns a `BlockNode`. A block can be be a simple_stmts or a list of
     * simple_stms and/or compound_stmt, so we just use a list for each kind.
     *
     * ```
     * block : simple_stmts
     * | NEWLINE INDENT (simple_stmts | compound_stmt)+ DEDENT ;
     * ```
     */
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

    /**
     * Returns a `CompNode`. It should never be null.
     *
     * ```
     * comp_op : '<' | '>' | '==' | '>=' | '<=' | '<>' | '!=' | 'in' | 'not' 'in' |
     * 'is' | 'is' 'not' ;
     * ```
     */
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

    /**
     * Returns an `ExprNode`. An expession can be a different kind of
     * sub-expression. We do not provide all kinds of expr(s).
     *
     * ```
     * expr : atom trailer* | expr '**' expr | ('+' | '-' | '~')+ expr | expr ('*' |
     * '@' | '/' | '%' | '//') expr | expr ('+' | '-') expr | expr ('<<' | '>>')
     * expr | expr '&' expr | expr '^' expr | expr '|' expr | 'not' expr | expr
     * comp_op expr | expr 'and' expr | expr 'or' expr | expr 'if' expr 'else' expr
     * ;
     * ```
     */
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

    /**
     * Returns an `AtomNode`.
     * FIXME: add support for testlist_comp
     *
     * ```
     * atom : '(' testlist_comp? ')' | '[' testlist_comp? ']' | '{' testlist_comp?
     * '}' | NAME | NUMBER | STRING+ | '...' | 'None' | 'True' | 'False' ;
     * ```
     */
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

    public Node visitExprlist(ExprlistContext ctx) {
        // FIXME: you've used to be a list, c'mon
        Node exp = visit(ctx.expr(0));

        return exp;
    }

    public Node visitArglist(ArglistContext ctx) {
        ArrayList<Node> arguments = new ArrayList<Node>();

        for (ArgumentContext c : ctx.argument()) {
            arguments.add(visit(c));
        }

        return new ArglistNode(arguments);
    }
}
