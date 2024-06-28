package ast;

import java.util.ArrayList;

import ast.nodes.*;
import parser.Python3ParserBaseVisitor;
import parser.Python3Parser.*;
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
     * ``` root : NEWLINE* (simple_stmts | compound_stmt)* EOF; ```
     */
    public Node visitRoot(RootContext ctx) {
        ArrayList<Node> childs = new ArrayList<Node>();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            var child = ctx.getChild(i);

            if (child instanceof Simple_stmtsContext) {
                childs.add(visit((Simple_stmtsContext) child));
            } else if (child instanceof Compound_stmtContext) {
                childs.add(visit((Compound_stmtContext) child));
            }
        }

        return new RootNode(childs);
    }

    /**
     * Returns a `SimpleStmtsNode`, made by an array of SimpleStmtNode
     *
     * ``` simple_stmts : simple_stmt (';' simple_stmt)* ';'? NEWLINE ; ```
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
     * ``` compound_stmt : if_stmt | while_stmt | for_stmt | funcdef ; ```
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
     * ``` simple_stmt : assignment | expr | return_stmt | import_stm ; ```
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
     * ``` assignment : exprlist augassign exprlist ; ```
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
     * ``` return_stmt : 'return' exprlist? ; ```
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
     * ``` import_stm : 'import' dotted_name ('as' NAME)? | 'from' dotted_name
     * 'import' (NAME (',' NAME)* | '*') ; ```
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
     * ``` dotted_name : NAME ('.' NAME)* ; ```
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
     * ``` funcdef : 'def' NAME '(' paramlist? ')' ':' block ; ```
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
     * ``` paramlist : paramdef ('=' expr)? (',' paramdef ('=' expr)?)* ;
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
     * ``` paramdef : NAME (':' expr)? ; ```
     */
    public Node visitParamdef(ParamdefContext ctx) {
        return new ParamdefNode(ctx.NAME().toString());
    }

    /**
     * Returns an `AugassignNode`. We don't provide all kinds of assignment
     * below.
     *
     * ``` augassign : '=' | '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' |
     * '|=' | '^=' | '<<=' | '>>=' | '**=' | '//=' ; ```
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
        } else if (ctx.AT_ASSIGN() != null) {
            x = new AugassignNode(ctx.AT_ASSIGN());
        } else if (ctx.DIV_ASSIGN() != null) {
            x = new AugassignNode(ctx.DIV_ASSIGN());
        } else if (ctx.MOD_ASSIGN() != null) {
            x = new AugassignNode(ctx.MOD_ASSIGN());
        } else if (ctx.AND_ASSIGN() != null) {
            x = new AugassignNode(ctx.AND_ASSIGN());
        } else if (ctx.OR_ASSIGN() != null) {
            x = new AugassignNode(ctx.OR_ASSIGN());
        } else if (ctx.XOR_ASSIGN() != null) {
            x = new AugassignNode(ctx.XOR_ASSIGN());
        } else if (ctx.LEFT_SHIFT_ASSIGN() != null) {
            x = new AugassignNode(ctx.LEFT_SHIFT_ASSIGN());
        } else if (ctx.RIGHT_SHIFT_ASSIGN() != null) {
            x = new AugassignNode(ctx.RIGHT_SHIFT_ASSIGN());
        } else if (ctx.POWER_ASSIGN() != null) {
            x = new AugassignNode(ctx.POWER_ASSIGN());
        } else if (ctx.IDIV_ASSIGN() != null) {
            x = new AugassignNode(ctx.IDIV_ASSIGN());
        }

        return x;
    }

    /**
     * Returns a `IfNode`. FIXME: add support for elif statement.
     *
     * ``` if_stmt : 'if' expr ':' block ('elif' expr ':' block)* ('else' ':'
     * block)? ; ```
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
     * ``` while_stmt : 'while' expr ':' block ('else' ':' block)? ; ```
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
     * ``` for_stmt : 'for' exprlist ':' block ('else' ':' block)? ; ```
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
     * ``` block : simple_stmts | NEWLINE INDENT (simple_stmts | compound_stmt)+
     * DEDENT ; ```
     */
    public Node visitBlock(BlockContext ctx) {
        ArrayList<Node> childs = new ArrayList<Node>();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            var child = ctx.getChild(i);

            if (child instanceof Simple_stmtsContext) {
                childs.add(visit((Simple_stmtsContext) child));
            } else if (child instanceof Compound_stmtContext) {
                childs.add(visit((Compound_stmtContext) child));
            }
        }

        return new BlockNode(childs);
    }

    /**
     * Returns a `CompNode`. It should never be null.
     *
     * ``` comp_op : '<' | '>' | '==' | '>=' | '<=' | '<>' | '!=' | 'in' | 'not'
     * 'in' | 'is' | 'is' 'not' ; ```
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
     * ``` expr : atom trailer* | expr '**' expr | ('+' | '-' | '~')+ expr |
     * expr ('*' | '@' | '/' | '%' | '//') expr | expr ('+' | '-') expr | expr
     * ('<<' | '>>') expr | expr '&' expr | expr '^' expr | expr '|' expr |
     * 'not' expr | expr comp_op expr | expr 'and' expr | expr 'or' expr | expr
     * 'if' expr 'else' expr ; ```
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
     * Returns an `AtomNode`. FIXME: add support for testlist_comp
     *
     * ``` atom : '(' testlist_comp? ')' | '[' testlist_comp? ']' | '{'
     * testlist_comp? '}' | NAME | NUMBER | STRING+ | '...' | 'None' | 'True' |
     * 'False' ; ```
     */
    public Node visitAtom(AtomContext ctx) {
        Testlist_compContext tlc = ctx.testlist_comp();
        if (ctx.NUMBER() != null) {
            return new AtomNode(ctx.NUMBER().toString(), null);
        } else if (ctx.NONE() != null) {
            return new AtomNode(ctx.NONE().toString(), null);
        } else if (ctx.TRUE() != null) {
            return new AtomNode(ctx.TRUE().toString(), null);
        } else if (ctx.FALSE() != null) {
            return new AtomNode(ctx.FALSE().toString(), null);
        } else if (ctx.NAME() != null) {
            return new AtomNode(ctx.NAME().toString(), null);
        } else if (!ctx.STRING().isEmpty()) {
            var varName = "";
            for (var x : ctx.STRING()) {
                varName += x;
            }
            return new AtomNode(varName, null);
        } else if (ctx.OPEN_BRACE() != null && ctx.CLOSE_BRACE() != null) {
            return manageTlc(tlc);
        } else if (ctx.OPEN_BRACK() != null && ctx.CLOSE_BRACK() != null) {
            return manageTlc(tlc);
        } else if (ctx.OPEN_PAREN() != null && ctx.CLOSE_PAREN() != null) {
            return manageTlc(tlc);
        }
        return new AtomNode(null, null);
    }

    public AtomNode manageTlc(Testlist_compContext tlc) {
        if (tlc != null) {
            Node testlist_comp = visit(tlc);
            return new AtomNode(null, testlist_comp);
        } else {
            return new AtomNode(null, null);
        }
    }

    /**
     * Returns an `TrailerNode`.
     *
     * ``` trailer : '(' arglist? ')' | '[' expr (',' expr)* ','? ']' | '.' NAME
     * | '[' expr? ':' expr? (':' expr? )? ']' ; ```
     */
    public Node visitTrailer(TrailerContext ctx) {
        Node arglist = null;
        if (ctx.arglist() != null) {
            arglist = visit(ctx.arglist());
        }

        ArrayList<Node> exprs = new ArrayList<Node>();
        for (ExprContext expr : ctx.expr()) {
            exprs.add(visit(expr));
        }

        // A trailer could be `.methodName()`.
        TerminalNode methodCall = null;
        if (ctx.DOT() != null) {
            methodCall = ctx.NAME();
        }

        return new TrailerNode(arglist, exprs, methodCall, ctx.OPEN_PAREN() != null);
    }

    /**
     * Returns a `Node`. FIXME: what to do in case of list??
     *
     * ``` exprlist : expr (',' expr )* ','? ; ```
     */
    public Node visitExprlist(ExprlistContext ctx) {
        ArrayList<Node> exprlist = new ArrayList<Node>();

        for (ExprContext c : ctx.expr()) {
            exprlist.add(visit(c));
        }

        return new ExprListNode(exprlist);
    }

    /**
     * Returns a `ArglistNode`.
     *
     * ``` arglist : argument (',' argument)* ','? ; ```
     */
    public Node visitArglist(ArglistContext ctx) {
        ArrayList<Node> arguments = new ArrayList<Node>();

        for (ArgumentContext c : ctx.argument()) {
            arguments.add(visit(c));
        }

        return new ArglistNode(arguments);
    }

    /**
     * Returns a `TestlistCompNode`.
     *
     * ``` testlist_comp : expr (comp_for | (',' expr)* ','?) ; ```
     */
    public Node visitTestlist_comp(Testlist_compContext ctx) {
        // TODO: implement comp_for
        ArrayList<Node> exprlist = new ArrayList<Node>();

        for (ExprContext c : ctx.expr()) {
            exprlist.add(visit(c));
        }
        Comp_forContext cfc = ctx.comp_for();
        Node comp = null;
        if (cfc != null) {
            comp = visit(ctx.comp_for());
        }
        return new TestlistCompNode(exprlist, comp);
    }

    /**
     * Returns a `CompForNode`.
     *
     * ``` comp_for : 'for' exprlist 'in' expr comp_iter? ;```
     */
    public Node visitComp_for(Comp_forContext ctx) {
        Node exprlist = visit(ctx.exprlist());
        Node expr = visit(ctx.expr());
        Comp_iterContext cic = ctx.comp_iter();

        if (cic != null) {
            Node comp = visit(ctx.comp_iter());
            return new CompForNode(exprlist, expr, comp);
        }
        return new CompForNode(exprlist, expr, null);
    }

    /**
     * Returns a `CompIterNode`.
     *
     * ``` comp_iter : comp_for | comp_if ; ;```
     */
    public Node visitComp_iter(Comp_iterContext ctx) {
        // TODO: Implement comp_if
        // Node iter = visit(ctx.comp_if());
        Comp_forContext cfc = ctx.comp_for();
        if (cfc != null) {
            Node forNode = visit(ctx.comp_for());
            return new CompIterNode(forNode);

        }
        return new CompIterNode(null);
    }
}
