package com.clp.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.*;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.clp.project.ast.*;
import com.clp.project.ast.nodes.*;
import com.clp.project.parser.*;
import com.clp.project.semanticanalysis.*;

public class Main {
    public static void main(String[] args) {
        // for (File file : Objects.requireNonNull(new File("./progs/").listFiles())) {
        try {
            // String fileStr = file.getPath();
            // FIXME: use the fileStr above
            String fileStr = "./progs/test2.py";
            System.out.println(fileStr);

            System.out.println(readFile(fileStr));

            CharStream cs = CharStreams.fromFileName(fileStr);
            Python3Lexer lexer = new Python3Lexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Python3Parser parser = new Python3Parser(tokens);
            Python3Parser.RootContext tree = parser.root();

            // DEBUG
            // {
            // tokens.fill();
            // for (Token token : tokens.getTokens()) {
            // System.out.println(token.toString());
            // }
            //
            // System.out.println("Tree: " + tree);
            // }

            JFrame frame = new JFrame("Parse Tree");
            JPanel panel = new JPanel();
            TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()),
                    tree);
            viewer.setScale(1.5); // Zoom factor
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

            ArrayList<SemanticError> errors = ast.checkSemantics(ST, 0);
            if (errors.size() > 0) {
                System.out.println("You had: " + errors.size() + " errors:");
                for (SemanticError e : errors)
                    System.out.println("\t" + e);
            } else {
                System.out.println("Visualizing AST...");
                System.out.println(ast.toPrint(""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
