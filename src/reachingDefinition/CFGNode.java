package reachingDefinition;
import java.util.*;

/**
 * Class representing a node in the Control Flow Graph.
 */

public class CFGNode {
    private int line;

    // Set of definitions generated from this node
    private final Set<String> gen;
    // Set of definitions eliminated from this node
    private final Set<String> kill;
    
    private final List<CFGNode> successors;
    private final List<CFGNode> predecessors;

    private final Set<String> in;
    private final Set<String> out;

    private final boolean isStmt;


    public CFGNode(int line, boolean isStmt) {
        this.line = line; 
        this.gen = new HashSet<>();
        this.kill = new HashSet<>();
        this.successors = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.in = new HashSet<>();
        this.out = new HashSet<>();
        this.isStmt = isStmt;
    }

    public boolean isStmt() {
        return this.isStmt;
    }

    public int getLine() {
        return this.line;
    }

    public int setLine(int line) {
        return this.line = line;
    }

    public Set<String> getGen() {
        return this.gen;
    }

    public Set<String> getKill() {
        return this.kill;
    }

    public List<CFGNode> getSuccessors() {
        return this.successors;
    }

    public List<CFGNode> getPredecessors() {
        return this.predecessors;
    }

    public Set<String> getIn() {
        return this.in;
    }

    public Set<String> getOut() {
        return this.out;
    }

    public void setOut(Set<String> out) {
        this.out.clear();
        this.out.addAll(out);
    }

    public void setIn(Set<String> in) {
        this.in.clear();
        this.in.addAll(in);
    }

    public void addGen(String var) {
        if (!this.gen.contains(var)) {
            this.gen.add(var);
        }
    }

    public void removeGen(String var) {
        if (this.gen.contains(var)) {
            this.gen.remove(var);
        }
    }

    public void addKill(String var) {
        if (this.kill.contains(var)) {
            this.kill.add(var);
        }
    }

    public void removeKill(String var) {
        if (this.kill.contains(var)) {
            this.kill.remove(var);
        }
    }

    public void addSuccessor(CFGNode successor) {
        if (!this.successors.contains(successor)) {
            this.successors.add(successor);
        }
    }

    public void removeSuccessor(CFGNode successor) {
        if (this.successors.contains(successor)) {
            this.successors.remove(successor);
        }
    }

    public void addPredecessor(CFGNode predecessor) {
        if (!this.predecessors.contains(predecessor)) {
            this.predecessors.add(predecessor);
        }
    }

    public void removePredecessor(CFGNode predecessor) {
        if (this.predecessors.contains(predecessor)) {
            this.predecessors.remove(predecessor);
        }
    }

    public void addIn(String var) {
        if (!this.in.contains(var)) {
            this.in.add(var);
        }
    }

    public void removeIn(String var) {
        if (this.in.contains(var)) {
            this.in.remove(var);
        }
    }

    public void addOut(String var) {
        if (!this.out.contains(var)) {
            this.out.add(var);
        }
    }

    public void removeOut(String var) {
        if (this.out.contains(var)) {
            this.out.remove(var);
        }
    }

    public String toPrint(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix + "Node " + this.line + "\n");
        sb.append(prefix + "\tGen: [ ");
        for (String var : this.gen) {
            sb.append(var + " ");
        }
        sb.append("]\n" + prefix + "\tKill: [ ");
        for (String var : this.kill) {
            sb.append(var + " ");
        }
        sb.append("]\n" + prefix + "\tSuccessors: [ ");
        for (CFGNode node : this.successors) {
            sb.append(node.getLine() + " ");
        }
        sb.append("]\n" + prefix + "\tPredecessors: [ ");
        for (CFGNode node : this.predecessors) {
            sb.append(node.getLine() + " ");
        }
        sb.append("]\n" + prefix + "\tIn: [ ");
        for (String var : this.in) {
            sb.append(var + " ");
        }
        sb.append("]\n" + prefix + "\tOut: [ ");
        for (String var : this.out) {
            sb.append(var + " ");
        }
        sb.append("]\n");

        return sb.toString();
    }
}