
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
import parser.Python3Lexer;
import parser.Python3Parser;
import semanticanalysis.*;
import svm.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err
                    .println(
                            "You must execute this program with a file parameter.\nUsage: java -cp lib/antlr-4.13.1-complete.jar:out Main <file.py>");
            return;
        }

        try {
            String fileStr = args[0];
            System.out.println(fileStr);
            System.out.println(Share.readFile(fileStr));
            CharStream cs = CharStreams.fromFileName(fileStr);
            Python3Lexer lexer = new Python3Lexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Python3Parser parser = new Python3Parser(tokens);
            Python3Parser.RootContext tree = parser.root();

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
            if (tree == null) {
                System.err.println("The tree is null.");
                return;
            }
            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("Error on program parsing.");
                return;
            }
            Python3VisitorImpl visitor = new Python3VisitorImpl();
            SymbolTable ST = new SymbolTable();
            Node ast = visitor.visit(tree);
            ArrayList<SemanticError> errorsWithDup = ast.checkSemantics(ST, 0, null);
            ArrayList<SemanticError> errors = Share.removeDuplicates(errorsWithDup);
            if (!errors.isEmpty()) {
                System.out.println("You had " + errors.size() + " errors:");
                for (SemanticError e : errors) {
                    System.out.println("\t" + e);
                }
            } else {
                System.out.println("Visualizing AST...");
                System.out.println(ast.toPrint(""));
                System.out.println("Creating VM code...");
                String prog = ast.codeGeneration();
                String asmFile = "code.asm";
                Path file = Paths.get(asmFile);
                if (!Files.exists(file)) {
                    Files.createFile(file);
                }
                Files.write(file, prog.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Done!");

                CharStream inputASM = CharStreams.fromFileName(asmFile);
                SVMLexer lexerASM = new SVMLexer(inputASM);
                CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
                SVMParser parserASM = new SVMParser(tokensASM);

                SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
                visitorSVM.visit(parserASM.assembly());

                System.out.println("You had: " + lexerASM.lexicalErrors + " lexical errors and "
                        + parserASM.getNumberOfSyntaxErrors() + " syntax errors.");
                if (lexerASM.lexicalErrors > 0 || parserASM.getNumberOfSyntaxErrors() > 0)
                    System.exit(1);

                System.out.println("Starting Virtual Machine...");
                ExecuteVM vm = new ExecuteVM(visitorSVM.code);
                vm.cpu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
