import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGraphAddEdge {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testGraphNodeDoesntExistDelete() {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode(3.14);

        graph.addEdge("A", "B");
        graph.addEdge("B", 3.14);
        graph.addEdge(3.14, "A");
        graph.addEdge(3.14, "A");

        Assertions.assertEquals("Edge already exist", outputStream.toString().trim());
    }
}