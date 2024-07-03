package reachingDefinition;

import java.util.*;

import org.antlr.v4.runtime.misc.Pair;

public class ControlFlowGraph {
    private final List<CFGNode> nodes;
    private final CFGNode entryBlock;
    private CFGNode exitBlock;

    public ControlFlowGraph(CFGNode entryBlock) {
        this.nodes = new ArrayList<>();
        this.entryBlock = entryBlock;
        this.exitBlock = null;
    }

    // TODO: da testare
    public void generateInAndOutSets() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (CFGNode node : this.nodes) {
                Set<String> in = new HashSet<>();
                Set<String> out = new HashSet<>();
                for (CFGNode pred : node.getPredecessors()) {
                    in.addAll(pred.getOut());
                }
                Set<String> oldIn = new HashSet<>(node.getIn());
                Set<String> oldOut = new HashSet<>(node.getOut());
                node.setIn(in);
                out.addAll(node.getGen());
                for (String var : node.getOut()) {
                    if (!node.getKill().contains(var)) {
                        out.add(var);
                    }
                }
                node.setOut(out);
                if (!oldIn.equals(node.getIn()) || !oldOut.equals(node.getOut())) {
                    changed = true;
                }
            }
        }
    }

    // TODO: da testare
    public Pair<Map<CFGNode, Set<String>>, Map<CFGNode, Set<String>>> generateGenKillSets() {
        Map<CFGNode, Set<String>> gen = new HashMap<>();
        Map<CFGNode, Set<String>> kill = new HashMap<>();

        for (CFGNode node : this.nodes) {
            Set<String> genSet = new HashSet<>();
            Set<String> killSet = new HashSet<>();
            for (String var : node.getGen()) {
                if (!genSet.contains(var)) {
                    genSet.add(var);
                }
            }
            for (String var : node.getKill()) {
                if (!killSet.contains(var)) {
                    killSet.add(var);
                }
            }
            gen.put(node, genSet);
            kill.put(node, killSet);
        }

        return new Pair<>(gen, kill);
    }

    public List<CFGNode> getNodes() {
        return this.nodes;
    }

    public CFGNode getEntryBlock() {
        return this.entryBlock;
    }

    public CFGNode getExitBlock() {
        return this.exitBlock;
    }

    public void setExitBlock(CFGNode exitBlock) {
        this.exitBlock = exitBlock;
    }

    public void addNode(CFGNode node) {
        if (!this.nodes.contains(node)) {
            this.nodes.add(node);
        }
    }

    public void removeNode(CFGNode node) {
        if (this.nodes.contains(node)) {
            this.nodes.remove(node);
        }

        // TODO: remove from successors and predecessors
        // TODO: remove da kill e gen di tutti i nodi
        // TODO: remove da in e out di tutti i nodi
    }

    public void addEdge(CFGNode from, CFGNode to) {
        if (!from.getSuccessors().contains(to)) {
            from.addSuccessor(to);
        }
    }

    public void removeEdge(CFGNode from, CFGNode to) {
        if (from.getSuccessors().contains(to)) {
            from.removeSuccessor(to);
        }

        // TODO: capire cosa fare con i gen e kill
        // TODO: capire cosa fare con in e out
    }
}
