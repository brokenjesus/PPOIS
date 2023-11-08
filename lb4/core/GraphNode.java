package by.lupach.grapheditor.core;

public class GraphNode implements Comparable<GraphNode>{
    private String ID;

    public GraphNode(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    @Override
    public int compareTo(GraphNode otherNode) {
        return this.ID.compareTo(otherNode.getID());
    }

}
