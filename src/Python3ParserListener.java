
// Generated from src/Python3Parser.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Python3Parser}.
 */
public interface Python3ParserListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link Python3Parser#root}.
     *
     * @param ctx the parse tree
     */
    void enterRoot(Python3Parser.RootContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#root}.
     *
     * @param ctx the parse tree
     */
    void exitRoot(Python3Parser.RootContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#simple_stmts}.
     *
     * @param ctx the parse tree
     */
    void enterSimple_stmts(Python3Parser.Simple_stmtsContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#simple_stmts}.
     *
     * @param ctx the parse tree
     */
    void exitSimple_stmts(Python3Parser.Simple_stmtsContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#compound_stmt}.
     *
     * @param ctx the parse tree
     */
    void enterCompound_stmt(Python3Parser.Compound_stmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#compound_stmt}.
     *
     * @param ctx the parse tree
     */
    void exitCompound_stmt(Python3Parser.Compound_stmtContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#simple_stmt}.
     *
     * @param ctx the parse tree
     */
    void enterSimple_stmt(Python3Parser.Simple_stmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#simple_stmt}.
     *
     * @param ctx the parse tree
     */
    void exitSimple_stmt(Python3Parser.Simple_stmtContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#assignment}.
     *
     * @param ctx the parse tree
     */
    void enterAssignment(Python3Parser.AssignmentContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#assignment}.
     *
     * @param ctx the parse tree
     */
    void exitAssignment(Python3Parser.AssignmentContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#return_stmt}.
     *
     * @param ctx the parse tree
     */
    void enterReturn_stmt(Python3Parser.Return_stmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#return_stmt}.
     *
     * @param ctx the parse tree
     */
    void exitReturn_stmt(Python3Parser.Return_stmtContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#import_stm}.
     *
     * @param ctx the parse tree
     */
    void enterImport_stm(Python3Parser.Import_stmContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#import_stm}.
     *
     * @param ctx the parse tree
     */
    void exitImport_stm(Python3Parser.Import_stmContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#dotted_name}.
     *
     * @param ctx the parse tree
     */
    void enterDotted_name(Python3Parser.Dotted_nameContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#dotted_name}.
     *
     * @param ctx the parse tree
     */
    void exitDotted_name(Python3Parser.Dotted_nameContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#funcdef}.
     *
     * @param ctx the parse tree
     */
    void enterFuncdef(Python3Parser.FuncdefContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#funcdef}.
     *
     * @param ctx the parse tree
     */
    void exitFuncdef(Python3Parser.FuncdefContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#paramlist}.
     *
     * @param ctx the parse tree
     */
    void enterParamlist(Python3Parser.ParamlistContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#paramlist}.
     *
     * @param ctx the parse tree
     */
    void exitParamlist(Python3Parser.ParamlistContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#paramdef}.
     *
     * @param ctx the parse tree
     */
    void enterParamdef(Python3Parser.ParamdefContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#paramdef}.
     *
     * @param ctx the parse tree
     */
    void exitParamdef(Python3Parser.ParamdefContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#augassign}.
     *
     * @param ctx the parse tree
     */
    void enterAugassign(Python3Parser.AugassignContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#augassign}.
     *
     * @param ctx the parse tree
     */
    void exitAugassign(Python3Parser.AugassignContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#if_stmt}.
     *
     * @param ctx the parse tree
     */
    void enterIf_stmt(Python3Parser.If_stmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#if_stmt}.
     *
     * @param ctx the parse tree
     */
    void exitIf_stmt(Python3Parser.If_stmtContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#while_stmt}.
     *
     * @param ctx the parse tree
     */
    void enterWhile_stmt(Python3Parser.While_stmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#while_stmt}.
     *
     * @param ctx the parse tree
     */
    void exitWhile_stmt(Python3Parser.While_stmtContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#for_stmt}.
     *
     * @param ctx the parse tree
     */
    void enterFor_stmt(Python3Parser.For_stmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#for_stmt}.
     *
     * @param ctx the parse tree
     */
    void exitFor_stmt(Python3Parser.For_stmtContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#block}.
     *
     * @param ctx the parse tree
     */
    void enterBlock(Python3Parser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#block}.
     *
     * @param ctx the parse tree
     */
    void exitBlock(Python3Parser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#comp_op}.
     *
     * @param ctx the parse tree
     */
    void enterComp_op(Python3Parser.Comp_opContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#comp_op}.
     *
     * @param ctx the parse tree
     */
    void exitComp_op(Python3Parser.Comp_opContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterExpr(Python3Parser.ExprContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitExpr(Python3Parser.ExprContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#atom}.
     *
     * @param ctx the parse tree
     */
    void enterAtom(Python3Parser.AtomContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#atom}.
     *
     * @param ctx the parse tree
     */
    void exitAtom(Python3Parser.AtomContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#testlist_comp}.
     *
     * @param ctx the parse tree
     */
    void enterTestlist_comp(Python3Parser.Testlist_compContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#testlist_comp}.
     *
     * @param ctx the parse tree
     */
    void exitTestlist_comp(Python3Parser.Testlist_compContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#trailer}.
     *
     * @param ctx the parse tree
     */
    void enterTrailer(Python3Parser.TrailerContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#trailer}.
     *
     * @param ctx the parse tree
     */
    void exitTrailer(Python3Parser.TrailerContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#exprlist}.
     *
     * @param ctx the parse tree
     */
    void enterExprlist(Python3Parser.ExprlistContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#exprlist}.
     *
     * @param ctx the parse tree
     */
    void exitExprlist(Python3Parser.ExprlistContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#arglist}.
     *
     * @param ctx the parse tree
     */
    void enterArglist(Python3Parser.ArglistContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#arglist}.
     *
     * @param ctx the parse tree
     */
    void exitArglist(Python3Parser.ArglistContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#argument}.
     *
     * @param ctx the parse tree
     */
    void enterArgument(Python3Parser.ArgumentContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#argument}.
     *
     * @param ctx the parse tree
     */
    void exitArgument(Python3Parser.ArgumentContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#comp_iter}.
     *
     * @param ctx the parse tree
     */
    void enterComp_iter(Python3Parser.Comp_iterContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#comp_iter}.
     *
     * @param ctx the parse tree
     */
    void exitComp_iter(Python3Parser.Comp_iterContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#comp_for}.
     *
     * @param ctx the parse tree
     */
    void enterComp_for(Python3Parser.Comp_forContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#comp_for}.
     *
     * @param ctx the parse tree
     */
    void exitComp_for(Python3Parser.Comp_forContext ctx);

    /**
     * Enter a parse tree produced by {@link Python3Parser#comp_if}.
     *
     * @param ctx the parse tree
     */
    void enterComp_if(Python3Parser.Comp_ifContext ctx);

    /**
     * Exit a parse tree produced by {@link Python3Parser#comp_if}.
     *
     * @param ctx the parse tree
     */
    void exitComp_if(Python3Parser.Comp_ifContext ctx);
}
