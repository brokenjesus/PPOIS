import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGraphDeleteEdge {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testGraphEdgeDoesntExistDelete() {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.deleteEdge("B", "A");

        Assertions.assertEquals("Edge doesn't exist", outputStream.toString().trim());
    }

    @Test
    public void testGraphEdgeDelete() {
        Graph graph = new Graph();
        graph.addNode(1);
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge(1, "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", 1);

        graph.deleteEdge(1, "B");

        Assertions.assertEquals(false, graph.isEdgeExist(1, "B"));
    }

    @Test
    public void testGraphEdgeDeleteViaIterator() {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.deleteEdgeViaIterator("A", "B");

        Assertions.assertEquals(false, graph.isEdgeExist("A", "B"));
    }
}