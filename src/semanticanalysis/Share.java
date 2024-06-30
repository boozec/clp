package semanticanalysis;

import java.io.*;
import java.util.*;

public class Share {

    /**
     * Removes the duplicate elements in a list of Semantic Errors. It's not
     * generic because it's used a custom contains function.
     */
    public static ArrayList<SemanticError> removeDuplicates(ArrayList<SemanticError> list) {
        ArrayList<SemanticError> newList = new ArrayList();

        for (SemanticError element : list) {
            if (!customContains(newList, element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * Normal contains did not work, so we made a custom contains function.
     * Returns `true` if the String rappresentation of an object in the list is
     * equal to the element given in input.
     */
    private static boolean customContains(ArrayList<SemanticError> list, SemanticError e) {
        String e1 = e.toString();
        for (SemanticError element : list) {
            String e2 = element.toString();
            if (e2.equals(e1)) {
                return true;
            }
        }
        return false;
    }

    public static String getExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(extensionIndex + 1);
        }
    }

    public static String readFile(String filePath) throws IOException {
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
