package semanticanalysis;

import java.util.ArrayList;

public class Share {

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();

        for (T element : list) {
            if (!customContains(newList, element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    public static <T> boolean customContains(ArrayList<T> list, T e) {
        String e1 = e.toString();
        for (T element : list) {
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
}
