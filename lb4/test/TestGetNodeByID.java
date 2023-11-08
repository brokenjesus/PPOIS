import by.lupach.grapheditor.core.Graph;
import by.lupach.grapheditor.core.GraphNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGetNodeByID{

    @Test
    public void testGraphGetNode(){
        Graph graph = new Graph();
        graph.addNode(2);

        GraphNode node = graph.getNodeById(2);

        Assertions.assertEquals(2, node.getId());
    }

    @Test
    public void testGraphGetDoesntExistNode(){
        Graph graph = new Graph();
        graph.addNode("A");

        GraphNode node = graph.getNodeById("X");

        Assertions.assertEquals(null, node);
    }
}

