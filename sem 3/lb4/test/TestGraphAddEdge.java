import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGraphAddEdge {
    @Test
    public void testGraphNodeDoesntExistDelete() {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode(3.14);

        graph.addEdge("A", "B");
        graph.addEdge("B", 3.14);
        graph.addEdge(3.14, "A");

        try {
            graph.deleteEdge("X", "A");
            assertEquals(3, graph.getNodesCount());
        } catch (IllegalArgumentException e) {
            assertEquals("Node doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testGraphEdgeAlreadyExist() {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode(3.14);

        graph.addEdge("A", "B");
        graph.addEdge("B", 3.14);
        graph.addEdge(3.14, "A");

        try {
            graph.addEdge("A", "B");
            assertEquals(3, graph.getNodesCount());
        } catch (IllegalArgumentException e) {
            assertEquals("Edge already exist", e.getMessage());
        }
    }
}