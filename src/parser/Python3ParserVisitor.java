package parser;

// Generated from src/parser/Python3Parser.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Python3Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface Python3ParserVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link Python3Parser#root}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRoot(Python3Parser.RootContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#simple_stmts}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSimple_stmts(Python3Parser.Simple_stmtsContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#compound_stmt}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCompound_stmt(Python3Parser.Compound_stmtContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#simple_stmt}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSimple_stmt(Python3Parser.Simple_stmtContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#assignment}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignment(Python3Parser.AssignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#return_stmt}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReturn_stmt(Python3Parser.Return_stmtContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#import_stm}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitImport_stm(Python3Parser.Import_stmContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#dotted_name}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDotted_name(Python3Parser.Dotted_nameContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#funcdef}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFuncdef(Python3Parser.FuncdefContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#paramlist}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParamlist(Python3Parser.ParamlistContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#paramdef}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParamdef(Python3Parser.ParamdefContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#augassign}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAugassign(Python3Parser.AugassignContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#if_stmt}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIf_stmt(Python3Parser.If_stmtContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#while_stmt}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhile_stmt(Python3Parser.While_stmtContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#for_stmt}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFor_stmt(Python3Parser.For_stmtContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#block}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(Python3Parser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#comp_op}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComp_op(Python3Parser.Comp_opContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#expr}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpr(Python3Parser.ExprContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#atom}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAtom(Python3Parser.AtomContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#testlist_comp}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTestlist_comp(Python3Parser.Testlist_compContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#trailer}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTrailer(Python3Parser.TrailerContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#exprlist}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExprlist(Python3Parser.ExprlistContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#arglist}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArglist(Python3Parser.ArglistContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#argument}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArgument(Python3Parser.ArgumentContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#comp_iter}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComp_iter(Python3Parser.Comp_iterContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#comp_for}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComp_for(Python3Parser.Comp_forContext ctx);

    /**
     * Visit a parse tree produced by {@link Python3Parser#comp_if}.
     * 
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComp_if(Python3Parser.Comp_ifContext ctx);
}
