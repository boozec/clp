package reachingDefinition;

import ast.nodes.*;
import java.util.*;

public class ReachingDefinitionAnalysis {

    static class ReachingDefinitions {
        Map<String, Integer> definitions = new HashMap<>();

        void add(String variable, int lineNumber) {
            definitions.put(variable, lineNumber);
            // FIXME: Print definitions for debug
            /*System.out.println();
            // FIXME: Print all the map
            for (Map.Entry<String, Integer> entry : definitions.entrySet()) {
                System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
            }*/
        }

        Integer get(String variable) {
            return definitions.get(variable);
        }

        ReachingDefinitions copy() {
            ReachingDefinitions copy = new ReachingDefinitions();
            copy.definitions.putAll(this.definitions);
            // FIXME: Print definitions for debug
            /*System.out.println();
            for (Map.Entry<String, Integer> entry : definitions.entrySet()) {
                System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
            }*/
            return copy;
        }

        @Override
        public String toString() {
            // Print all the map
            String str = "";
            for (Map.Entry<String, Integer> entry : definitions.entrySet()) {
                str += "(" + entry.getKey() + ", " + entry.getValue() + ")\n";
            }
            return str;
        }
    }

    ReachingDefinitions reachingDefinitions = new ReachingDefinitions();
    RootNode root;
    int lineNumber = 0;
    int lastStatement;

    public void optimize(Node node) {
        /*try {
            System.out.println("Node: " + node.getClass().getName());
        } catch (Exception e) {
            System.out.println("Node: null");
        }*/

        if (node instanceof RootNode) {
            root = (RootNode) node;
            for (Node child : root.getChilds()) {
                optimize(child);
            }
            
        } else if (node instanceof SimpleStmtsNode) {
            this.lineNumber++;
            SimpleStmtsNode simpleStmts = (SimpleStmtsNode) node;
            for (Node simpleStmt : simpleStmts.getStmts()) {
                optimize(simpleStmt);
            }

        } else if (node instanceof SimpleStmtNode) {
            SimpleStmtNode simpleStmt = (SimpleStmtNode) node;
            if (simpleStmt.getAssignment() != null)
                optimize(simpleStmt.getAssignment());
            else if (simpleStmt.getExpr() != null)
                optimize(simpleStmt.getExpr());
            else if (simpleStmt.getReturnStmt() != null)
                optimize(simpleStmt.getReturnStmt());
            else if (simpleStmt.getImportStmt() != null)
                optimize(simpleStmt.getImportStmt());

        } else if (node instanceof CompoundNode) {
            this.lineNumber++;
            CompoundNode compound = (CompoundNode) node;
            if (compound.getIfNode() != null)
                optimize(compound.getIfNode());
            else if (compound.getFuncDef() != null)
                optimize(compound.getFuncDef());
            else if (compound.getForStmt() != null)
                optimize(compound.getForStmt());
            else if (compound.getWhileStmt() != null)
                optimize(compound.getWhileStmt());
        
        } else if (node instanceof BlockNode) {
            RootNode blockNode = (BlockNode) node;
            for (Node block : blockNode.getChilds()) {
                optimize(block);
            }
        
        } else if (node instanceof ExprNode) {
            ExprNode expression = (ExprNode) node;
            if (expression.getAtom() != null) {
                optimize(expression.getAtom());
            
            } else if (expression.getExprs() != null) {
                for (Node expr : expression.getExprs()) {
                    optimize(expr);
                }
            
            } else if (expression.getCompOp() != null) {
                optimize(expression.getCompOp());
            
            } else if (expression.getTrailers() != null) {
                for (Node trailer : expression.getTrailers()) {
                    optimize(trailer);
                }
            }
            //replaceSubExpressions(expression);
        
        } else if (node instanceof WhileStmtNode) {
            WhileStmtNode whileNode = (WhileStmtNode) node;
            optimize(whileNode.getExpr());
            this.lastStatement = this.lineNumber;
            optimizeBody(whileNode.getBlock());

        } else if (node instanceof ForStmtNode) {
            ForStmtNode forNode = (ForStmtNode) node;
            for (Node expr : ((ExprListNode) forNode.getExprList()).getExprs()) {    
                optimize(expr);
            }
            this.lastStatement = this.lineNumber;

            optimizeBody(forNode.getBlock());
        
        } else if (node instanceof AssignmentNode) {
            AssignmentNode assignment = (AssignmentNode) node;
            for (Node elem : assignment.getLhr().getExprs()) {
                this.reachingDefinitions.add(((ExprNode) elem).getId(), this.lineNumber);
                if (isRedundant(assignment)) { return; }
            }
        
        }
    }

    void optimizeBody(Node body) {
        if (body instanceof BlockNode) {
            BlockNode block = (BlockNode) body;
            for (Node child : block.getChilds()) {
                optimizeBody(child);
            }

        } else if (body instanceof SimpleStmtsNode) {
            this.lineNumber++;
            SimpleStmtsNode simpleStmts = (SimpleStmtsNode) body;
            for (Node simpleStmt : simpleStmts.getStmts()) {
                optimizeBody(simpleStmt);
            }

        } else if (body instanceof SimpleStmtNode) {
            SimpleStmtNode simpleStmt = (SimpleStmtNode) body;
            if (simpleStmt.getAssignment() != null)
                optimizeBody(simpleStmt.getAssignment());
        
        } else if (body instanceof AssignmentNode) {
            AssignmentNode assignment = (AssignmentNode) body;

            for (Node elem : assignment.getLhr().getExprs()) {
                if (((ExprNode) elem).getAtom().getId() != null) {
                    try {
                        if (this.reachingDefinitions.get(((ExprNode) elem).getAtom().getId()) < this.lastStatement) {
                            // TODO: Qui ci sarebbe da spostare l'assegnamento a prima di lastStatement e cancellarla da qui
                        }
                    } catch (NullPointerException e) {
                        continue;
                    }
                }
            }

            for (Node elem : assignment.getLhr().getExprs()) {
                this.reachingDefinitions.add(((ExprNode) elem).getId(), this.lineNumber);
                if (isRedundant(assignment)) { return; }
            }
        } else 
            optimize(body);
    }

    boolean isRedundant(AssignmentNode assignment) {
        for (Node elem : assignment.getRhr().getExprs()) {
            if (elem instanceof AtomNode) {
                AtomNode variable = (AtomNode) elem;
                Integer definitionLine = this.reachingDefinitions.get(variable.getId());
                if (definitionLine != null && definitionLine < lineNumber) {
                    return true;
                }
            }
        }
        return false;
    }

    void replaceSubExpressions(ExprNode expression) {
        for (int i = 0; i < expression.getExprs().size(); i++) {
            Node term = expression.getExpr(i);
            if (!(term instanceof AtomNode)) {
                String tempVar = "_tmp" + lineNumber;
                AssignmentNode tempAssignment = new AssignmentNode(
                        new ExprListNode(Arrays.asList(new AtomNode(tempVar, null))),
                        new AtomNode("=", null),
                        new ExprListNode(Arrays.asList(term)));
                this.reachingDefinitions.add(tempVar, this.lineNumber);
                expression.getExprs().set(i, tempAssignment);
                lineNumber++;
            }
        }
    }
}
