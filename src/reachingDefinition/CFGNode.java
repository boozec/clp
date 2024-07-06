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
}