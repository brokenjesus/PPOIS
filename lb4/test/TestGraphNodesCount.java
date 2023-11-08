import by.lupach.grapheditor.core.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestGraphNodesCount {

    @Test
    public void testGraphNodesCount(){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode(2);
        graph.addNode(3.6);
        graph.addNode(44444444);
        graph.addNode(123.123123);
        graph.addNode("F");

        Assertions.assertEquals(6, graph.getNodesCount());
    }
}
