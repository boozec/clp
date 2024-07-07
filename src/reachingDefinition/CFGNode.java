package reachingDefinition;

/**
 * Class representing a node in the Control Flow Graph.
 */
public class CFGNode {
    private String id;
    private String code = "";

    public CFGNode(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addCode(String code) {
        this.code += code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return id;
    }

    // Method to see if the node's code is contained in a given code.
    public boolean codeContainedIn(String str) {
        str = str.replaceAll("\\s", "");
        if ((!code.isEmpty()) && str.contains(code.replaceAll("\\s", ""))) {
            return true;
        }

        return false;
    }
}