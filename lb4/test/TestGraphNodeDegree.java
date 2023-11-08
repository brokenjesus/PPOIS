import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGraphNodeDegree {
    @Test
    public void testGraphNodeDegree(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        Assertions.assertEquals(2, graph.getNodeDegree("A"));
    }

    @Test
    public void testGraphNodeInDoesntExistDegree(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        try {
            graph.getNodeInDegree(2.17);
            Assertions.assertEquals(3, graph.getNodesCount());
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Node doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testGraphNodeInDegree(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("B", "A");

        Assertions.assertEquals(2, graph.getNodeInDegree("A"));
    }

    @Test
    public void testGraphNodeOutDegree(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("B", "A");

        Assertions.assertEquals(1, graph.getNodeOutDegree("A"));
    }

    @Test
    public void testGetNodeOutDegree(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        try {
            graph.getNodeOutDegree(2.17);
            Assertions.assertEquals(3, graph.getNodesCount());
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Node doesn't exist", e.getMessage());
        }
    }
}
