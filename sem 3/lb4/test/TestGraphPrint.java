import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGraphPrint {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testGraphPrintAdjacencyList() {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");
        graph.addEdge("B", "A");

        graph.printAdjacencyList();

        String expectedOutput = "Adjacency List:" + System.lineSeparator() +
                "Node A is adjacent to: B " + System.lineSeparator() +
                "Node B is adjacent to: C A " + System.lineSeparator() +
                "Node C is adjacent to: D " + System.lineSeparator() +
                "Node D is adjacent to: A";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintNodesViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.printNodesViaIterator();

        String expectedOutput = "Nodes:" + System.lineSeparator() +
                "A" + System.lineSeparator() +
                "B" + System.lineSeparator() +
                "C" + System.lineSeparator() +
                "D";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintNodesBackwardViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.printNodesBackwardViaIterator();

        String expectedOutput = "Nodes (in reverse order):" + System.lineSeparator() +
                "D" + System.lineSeparator() +
                "C" + System.lineSeparator() +
                "B" + System.lineSeparator() +
                "A";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintEdgesViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.printEdgesViaIterator();

        String expectedOutput = "Edges:" + System.lineSeparator() +
                "A->B" + System.lineSeparator() +
                "B->C" + System.lineSeparator() +
                "C->A";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintEdgesBackwardViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.printEdgesBackwardViaIterator();

        String expectedOutput = "Edges (in reverse order):" + System.lineSeparator() +
                "C->A" + System.lineSeparator() +
                "B->C" + System.lineSeparator() +
                "A->B";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintEdgesIncidentToNodeViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");
        graph.addEdge("B", "A");

        graph.printEdgesIncidentToNodeViaIterator("C");

        String expectedOutput = "Edges incident to the C are:"+  System.lineSeparator() +
                "Node C incident to edge: B->C" +  System.lineSeparator() +
                "Node C incident to edge: C->D";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintEdgesIncidentToNodeBackwardViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");
        graph.addEdge("B", "A");

        graph.printEdgesIncidentToNodeBackwardViaIterator("C");

        String expectedOutput = "Edges incident to the C are (in reverse order):"+  System.lineSeparator() +
                "Node C incident to edge: C->D" +  System.lineSeparator() +
                "Node C incident to edge: B->C";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintEdgesAdjacentToNodeViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");
        graph.addEdge("B", "A");

        graph.printEdgesAdjacentToNodeViaIterator("C");

        String expectedOutput = "Edges adjacent to the C are:" +  System.lineSeparator() +
                "B->C" +  System.lineSeparator()+
                "C->D";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGraphPrintEdgesAdjacentToNodeBackwardViaIterator(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");


        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");
        graph.addEdge("B", "A");

        graph.printEdgesAdjacentToNodeBackwardViaIterator("C");

        String expectedOutput = "Edges adjacent to the C are (in reverse order):" +  System.lineSeparator() +
                "C->D" +  System.lineSeparator()+
                "B->C";

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }
}