package com.clp.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.*;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.clp.project.parser.*;

public class Main {
    public static void main(String[] args) {
        for (File file : Objects.requireNonNull(new File("./progs/").listFiles())) {
            try {
                String fileStr = file.getPath();
                // FIXME: use the fileStr above
                fileStr = "./progs/test.py";
                System.out.println(fileStr);

                System.out.println(readFile(fileStr));

                CharStream cs = CharStreams.fromFileName(fileStr);
                Python3Lexer lexer = new Python3Lexer(cs);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                Python3Parser parser = new Python3Parser(tokenStream);
                Python3Parser.RootContext tree = parser.root();
                // String treeStr = tree.toString();
                // System.out.println(treeStr);
                // Visualize the parse tree
                JFrame frame = new JFrame("Parse Tree");
                JPanel panel = new JPanel();
                TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
                viewer.setScale(1.5); // Zoom factor
                panel.add(viewer);
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
