
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;

import ast.*;
import ast.nodes.*;
import parser.*;
import semanticanalysis.*;
import svm.*;

public class Main {

    private static boolean execute = false;
    private static boolean treeFlag = false;
    private static boolean optimize = false;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err
                    .println(
                            "You must execute this program with a file parameter.\nUsage: java -cp lib/antlr-4.13.1-complete.jar:out Main <file.py>");
            return;
        }

        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--exec")) {
                    execute = true;
                }

                if (args[i].equals("--tree")) {
                    treeFlag = true;
                }

                if (args[i].equals("--optimize")) {
                    optimize = true;
                }
            }

            String fileStr = args[0];
            CharStream cs = CharStreams.fromFileName(fileStr);
            Python3Lexer lexer = new Python3Lexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Python3Parser parser = new Python3Parser(tokens);
            Python3Parser.RootContext tree = parser.root();

            if (tree == null) {
                System.err.println("The tree is null.");
                return;
            }

            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("Error on program parsing.");
                return;
            }

            Python3VisitorImpl visitor = new Python3VisitorImpl(tokens, optimize);
            Node ast = visitor.visit(tree);
            SymbolTable ST = new SymbolTable();

            if (optimize) {
                // first visit to optimize
                cs = CharStreams.fromString(visitor.getRewriter());
                System.out.println(cs);
                lexer = new Python3Lexer(cs);
                tokens = new CommonTokenStream(lexer);
                parser = new Python3Parser(tokens);
                tree = parser.root();
                ST = new SymbolTable();

                ast = visitor.visit(tree);

                System.out.println("Saving optimized file...");
                String astToPrint = ast.toPrint("");
                Share.saveFile("optimized.py", astToPrint);
            }

            ArrayList<SemanticError> errorsWithDup = ast.checkSemantics(ST, 0, null);
            ArrayList<SemanticError> errors = Share.removeDuplicates(errorsWithDup);
            if (!errors.isEmpty()) {
                System.out.println("You had " + errors.size() + " errors:");
                for (SemanticError e : errors) {
                    System.out.println("\t" + e);
                }
            } else {
                if (treeFlag) {
                    JFrame frame = new JFrame("Parse Tree");
                    JPanel panel = new JPanel();
                    TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()),
                            tree);
                    viewer.setScale(1); // Zoom factor
                    panel.add(viewer);
                    frame.add(panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 600);
                    frame.setVisible(true);

                    System.out.println(ast.printAST(""));
                }

                if (execute) {
                    System.out.println("Creating VM code...");
                    String asmFile = "code.asm";
                    String prog = ast.codeGeneration();
                    Share.saveFile(asmFile, prog);
                    executeVMFile(asmFile);
                }

                System.out.println("Everything is OK!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeVMFile(String fileName) {
        try {
            CharStream inputASM = CharStreams.fromFileName(fileName);
            SVMLexer lexerASM = new SVMLexer(inputASM);
            CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
            SVMParser parserASM = new SVMParser(tokensASM);

            SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
            visitorSVM.visit(parserASM.assembly());

            int nLexicalErrors = lexerASM.lexicalErrors;
            int nSyntaxErrors = parserASM.getNumberOfSyntaxErrors();
            if (nLexicalErrors > 0 || nSyntaxErrors > 0) {
                System.out.println("You had " + nLexicalErrors + " lexical errors and "
                        + nSyntaxErrors + " syntax errors in the asm file.");
                System.exit(1);
            }

            System.out.println("Executing assemply code...");
            ExecuteVM vm = new ExecuteVM(visitorSVM.code);
            vm.cpu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
