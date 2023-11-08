import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestGraphEdgesCount {

    @Test
    public void testGraphEdgesCount(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode(1.567890);
        graph.addNode("C");

        graph.addEdge("A", 1.567890);
        graph.addEdge(1.567890, "C");
        graph.addEdge("C", "A");

        Assertions.assertEquals(3, graph.getEdgesCount());
    }
}
