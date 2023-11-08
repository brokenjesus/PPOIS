import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestGraphIsEdgeExist {

    @Test
    public void testGraphIsEdgeExistTrue(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("A", "B");


        Assertions.assertEquals(true, graph.isEdgeExist("A", "B"));
    }

    @Test
    public void testGraphIsEdgeExistFalse(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("A", "B");


        Assertions.assertEquals(false, graph.isEdgeExist("B", "A"));
    }
}
