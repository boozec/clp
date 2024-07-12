
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;

import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ast.*;
import ast.nodes.*;
import reachingDefinition.*;
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
            }
            if (tree == null) {
                System.err.println("The tree is null.");
                return;
            }
            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("Error on program parsing.");
                return;
            }
            Python3VisitorImpl visitor = new Python3VisitorImpl(tokens, optimize);
            SymbolTable ST = new SymbolTable();

            // first visit to optimize
            Node ast = visitor.visit(tree);
            cs = CharStreams.fromString(visitor.getRewriter());
            lexer = new Python3Lexer(cs);
            tokens = new CommonTokenStream(lexer);
            parser = new Python3Parser(tokens);
            tree = parser.root();
            visitor = new Python3VisitorImpl(tokens, false);
            ST = new SymbolTable();

            // second visit with optimized tree
            if (optimize) {
                ast = visitor.visit(tree);
            }
            ArrayList<SemanticError> errorsWithDup = ast.checkSemantics(ST, 0, null);
            ArrayList<SemanticError> errors = Share.removeDuplicates(errorsWithDup);
            if (!errors.isEmpty()) {
                System.out.println("You had " + errors.size() + " errors:");
                for (SemanticError e : errors) {
                    System.out.println("\t" + e);
                }
            } else {
                System.out.println("Saving optimized file...");
                String astToPrint = ast.toPrint("");
                Share.saveFile("optimized.py", astToPrint);

                if (execute) {
                    System.out.println("Creating VM code...");
                    String asmFile = "code.asm";
                    String prog = ast.codeGeneration();
                    Share.saveFile(asmFile, prog);
                    executeVmFile(asmFile);
                }

                System.out.println("Done!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeVmFile(String fileName) {
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
