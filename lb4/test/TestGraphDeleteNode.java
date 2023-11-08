import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGraphDeleteNode {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testGraphNodeDoesntExistDelete(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode(999999999);

        graph.addEdge("A", "B");
        graph.addEdge("B", 999999999);
        graph.addEdge(999999999, "A");

        try {
            graph.deleteNode("X");
            assertEquals(3, graph.getNodesCount());
        } catch (IllegalArgumentException e) {
            assertEquals("Node doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testGraphNodesCountAfterDeletingNode(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.deleteNode("B");

        Assertions.assertEquals(2, graph.getNodesCount());
    }

    @Test
    public void testGraphDeleteNodeViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.deleteNodeByIdViaIterator("B");

        Assertions.assertEquals(false, graph.isNodeExist("B"));
    }

    @Test
    public void testGraphEdgesCountAfterDeletingNode(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.deleteNode("B");

        Assertions.assertEquals(1, graph.getEdgesCount());
    }
}
