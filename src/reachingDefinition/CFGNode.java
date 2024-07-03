package reachingDefinition;
import java.util.*;

public class CFGNode {
    private static int line = 1;

    private int id;

    // Set of definitions generated from this node
    private final Set<String> gen;
    // Set of definitions eliminated from this node
    private final Set<String> kill;
    
    private final List<CFGNode> successors;
    private final List<CFGNode> predecessors;

    private final Set<String> in;
    private final Set<String> out;


    public CFGNode() {
        this.id = CFGNode.line++;
        this.gen = new HashSet<>();
        this.kill = new HashSet<>();
        this.successors = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.in = new HashSet<>();
        this.out = new HashSet<>();
    }

    public int getId() {
        return this.id;
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

    @Override
    public String toString() {
        return "CFGNode [id=" + id + ", gen=" + gen + ", kill=" + kill + ", in=" + in + ", out=" + out + "]";
    }
}