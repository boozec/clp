
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import org.antlr.v4.runtime.*;

import ast.Python3VisitorImpl;
import ast.nodes.*;
import parser.Python3Lexer;
import parser.Python3Parser;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import semanticanalysis.Share;

public class ParseAll {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        new File("./trees/").mkdirs();
        for (File file : Objects.requireNonNull(new File("./progs").listFiles())) {
            String fileStr = file.getPath();
            // fileStr = "./progs/wrong.py";
            try {

                if (!file.isFile() || !Share.getExtension(file.getName()).equals("py")) {
                    System.err.println("Wont parse: " + fileStr);
                    continue;
                }

                CharStream cs = CharStreams.fromFileName(fileStr);
                Python3Lexer lexer = new Python3Lexer(cs);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                Python3Parser parser = new Python3Parser(tokenStream);

                Python3Parser.RootContext tree = parser.root();
                String treeStr = tree.toStringTree();
                // System.out.println(treeStr);

                Python3VisitorImpl visitor = new Python3VisitorImpl();
                SymbolTable ST = new SymbolTable();
                Node ast = visitor.visit(tree);
                ArrayList<SemanticError> errorsWithDup = ast.checkSemantics(ST, 0);
                ArrayList<SemanticError> errors = Share.removeDuplicates(errorsWithDup);
                if (errors.size() > 0) {
                    System.out.println();
                    System.out.println(fileStr);
                    System.out.println("You had " + errors.size() + " errors:");
                    for (SemanticError e : errors) {
                        System.out.println("\t" + e);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(fileStr);
            }
        }
    }
}
