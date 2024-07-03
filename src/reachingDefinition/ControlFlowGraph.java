package reachingDefinition;

import java.util.*;

public class ControlFlowGraph {
    private final List<CFGNode> nodes;
    private final CFGNode entryBlock;

    public ControlFlowGraph(CFGNode entryBlock) {
        this.entryBlock = entryBlock;
        this.nodes = new ArrayList<>();

        this.nodes.add(entryBlock);
    }

    // TODO: da testare
    public void generateInOutSets() {
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
    public void generateGenKillSets() {
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
    }

    public CFGNode getNodeById(int id) {
        for (CFGNode node : this.nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    public List<CFGNode> getNodes() {
        return this.nodes;
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

        node.getPredecessors().forEach(pred -> pred.removeSuccessor(node));
        node.getSuccessors().forEach(succ -> succ.removePredecessor(node));
        // remove from gen, kill, in and out of all nodes
        for (CFGNode n : this.nodes) {
            n.getGen().removeAll(node.getGen());
            n.getKill().removeAll(node.getKill());
            n.getIn().removeAll(node.getIn());
            n.getOut().removeAll(node.getOut());
        }
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

        if (to.getPredecessors().contains(from)) {
            to.removePredecessor(from);
        }
    }

    public String toPrint(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix + "ControlFlowGraph\n");
        sb.append(prefix + "Entry block: " + this.entryBlock.getId() + "\n");
        sb.append(prefix + "Nodes:\n");
        for (CFGNode node : this.nodes) {
            sb.append(node.toPrint(prefix + "\t"));
        }
        return sb.toString();
    }
}
