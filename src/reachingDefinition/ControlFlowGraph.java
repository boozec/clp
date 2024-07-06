package reachingDefinition;

import java.util.*;

public class ControlFlowGraph {
    private Set<CFGNode> entryNodes;
    private Map<CFGNode, List<CFGNode>> nodes;
    private CFGNode exitNode;

    public ControlFlowGraph() {
        this.nodes = new HashMap<>();
        this.entryNodes = new HashSet<>();
        this.exitNode = new CFGNode("exit");
        addNode(exitNode);
    }

    public void setEntryNodes(Set<CFGNode> entryNodes) {
        this.entryNodes = entryNodes;
    }

    public Set<CFGNode> getEntryNodes() {
        return entryNodes;
    }

    public CFGNode getExitNode() {
        return exitNode;
    }

    public void addNode(CFGNode node) {
        nodes.putIfAbsent(node, new ArrayList<>());
    }

    public List<CFGNode> getEdges(CFGNode node) {
        return nodes.get(node);
    }

    public void addEdge(CFGNode to) throws IllegalArgumentException {
        if (nodes.containsKey(to)) {
            for (CFGNode from : entryNodes) {
                nodes.get(from).add(to);
            }
        } else {
            throw new IllegalArgumentException("Node must be present in the graph");
        }
    }

    public void addEdge(CFGNode from, CFGNode to) throws IllegalArgumentException {
        if (nodes.containsKey(from) && nodes.containsKey(to)) {
            nodes.get(from).add(to);
        } else {
            throw new IllegalArgumentException("Nodes must be present in the graph");
        }
    }

    public List<CFGNode> getNodes() {
        return List.copyOf(nodes.keySet());
    }

    public CFGNode getNode(String id) {
        for (CFGNode node : nodes.keySet()) {
            if (node.equals(id)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CFGNode node : nodes.keySet()) {
            sb.append(node.getId()).append(" -> ");
            for (CFGNode edge : nodes.get(node)) {
                sb.append(edge.getId()).append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}