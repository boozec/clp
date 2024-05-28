import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.*;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;

public class ParseAll {
    public static void main(String[] args) {
        for (File file : Objects.requireNonNull(new File("./progs").listFiles())) {
            String fileStr = file.getPath();
            // fileStr = "./progs/wrong.py";
            try {

                if (!file.isFile() || !getExtension(file.getName()).equals("py")) {
                    System.err.println("Wont parse: " + fileStr);
                    continue;
                } else {
                    System.out.println(fileStr);
                }

                // System.out.println(readFile(fileStr));

                CharStream cs = CharStreams.fromFileName(fileStr);
                Python3Lexer lexer = new Python3Lexer(cs);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                Python3Parser parser = new Python3Parser(tokenStream);

                Python3Parser.RootContext tree = parser.root();
                String treeStr = tree.toStringTree();
                // System.out.println(treeStr);

                TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
                viewer.setScale(1.5);
                saveTree(viewer, "./trees/" + removeExtension(file.getName()) + ".png");
                if (parser.getNumberOfSyntaxErrors() != 0) {
                    System.err.println("Parse errors: " + fileStr);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(fileStr);
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

    private static void showTree(TreeViewer viewer) {
        JFrame frame = new JFrame("Parse Tree");
        JPanel panel = new JPanel();
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private static void saveTree(TreeViewer viewer, String name) {
        try {
            viewer.save(name);
        } catch (Exception e) {
            System.err.println(name);
            e.printStackTrace();
        }
    }

    public static String removeExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, extensionIndex);
        }
    }

    public static String getExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(extensionIndex + 1);
        }
    }
}
