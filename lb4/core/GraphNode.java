package by.lupach.grapheditor.core;

public class GraphNode<T extends Comparable<T>> {
    private T id;

    public GraphNode(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
