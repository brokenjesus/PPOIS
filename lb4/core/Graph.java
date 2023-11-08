package by.lupach.grapheditor.core;

import java.util.*;
import java.util.Map.Entry;


public class Graph{
    private Map<GraphNode, List<GraphNode>> adjacencyList;


    public Graph() {
        adjacencyList = new TreeMap<>();
    }

    public GraphNode getNodeByID(String ID) {
        for (GraphNode node : adjacencyList.keySet()) {
            if (node.getID().equals(ID)) {
                return node;
            }
        }
        return null;
    }

    public boolean isNodeExist(String ID){
        for (GraphNode node : adjacencyList.keySet()){
            if (node.getID().equals(ID)){
                return true;
            }
        }
        return false;
    }

    public void addNode(String ID) {
        adjacencyList.put(new GraphNode(ID), new ArrayList<>());
    }


    public int getNodesCount(){
        return adjacencyList.size();
    }

    public boolean isEdgeExist(String node1, String node2){
        for (GraphNode keyNode: adjacencyList.keySet()){
            if (keyNode.getID().equals(node1)){
                for (GraphNode valueNode : adjacencyList.get(keyNode)){
                    if (valueNode.getID().equals(node2)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void deleteNode(String ID) {
        if (!isNodeExist(ID)) {
            System.out.println("Node doesn't exist");
            return;
        }

        GraphNode nodeToDelete = getNodeByID(ID);
        adjacencyList.remove(nodeToDelete);

        for (GraphNode keyNode : adjacencyList.keySet()) {
            List<GraphNode> adjacentNodes = adjacencyList.get(keyNode);
            adjacentNodes.removeIf(adjacentNode -> adjacentNode.getID().equals(ID));
        }
    }

    public int getNodeInDegree(String ID){
        if (!isNodeExist(ID)){
            System.out.println("Node doesn't exist");
            return -1;
        }

        int inDegree = 0;
        for (GraphNode keyNode : adjacencyList.keySet()) {
            List<GraphNode> adjacentNodes = adjacencyList.get(keyNode);
            for (GraphNode valueNode : adjacentNodes){
                if (valueNode.getID().equals(ID)){
                    inDegree++;
                }
            }
        }


        return inDegree;
    }

    public int getNodeOutDegree(String ID){
        if (!isNodeExist(ID)){
            System.out.println("Node doesn't exist");
            return -1;
        }
        return adjacencyList.get(getNodeByID(ID)).size();
    }

    public int getNodeDegree(String ID){
        return getNodeInDegree(ID)+getNodeOutDegree(ID);
    }

    public void deleteNodeByIDViaIterator(String ID){
        Iterator<GraphNode> nodesIterator = adjacencyList.keySet().iterator();

        while (nodesIterator.hasNext()){
            GraphNode node = nodesIterator.next();

            List<GraphNode> adjacentNodes = adjacencyList.get(node);
            adjacentNodes.removeIf(adjacentNode -> adjacentNode.getID().equals(ID));

            if (node.getID().equals(ID)){
                adjacencyList.remove(node);
                break;
            }
        }
    }

    public void addEdge(String node1, String node2) {
        if (!isEdgeExist(node1, node2)){
            adjacencyList.computeIfAbsent(getNodeByID(node1), k -> new ArrayList<>()).add(new GraphNode(node2));
        }else {
            System.out.println("Edge already exist");
        }
    }

    public int getEdgesCount(){
        int edgesCount = 0;
        for (GraphNode node : adjacencyList.keySet()){
            edgesCount += adjacencyList.get(node).size();
        }
        return edgesCount;
    }

    public void deleteEdge(String node1, String node2) {
        if (!isEdgeExist(node1, node2)){
            System.out.println("Edge doesn't exist");
            return;
        }

        for (GraphNode keyNode : adjacencyList.keySet()){
            if (keyNode.getID().equals(node1)){
                adjacencyList.get(keyNode).removeIf(adjacentNode -> adjacentNode.getID().equals(node2));
            }
        }
    }

    public void deleteEdgeViaIterator(String node1, String node2){
        Iterator<GraphNode> nodesIterator = adjacencyList.keySet().iterator();

        while (nodesIterator.hasNext()){
            GraphNode keyNode = nodesIterator.next();

            if (keyNode.getID().equals(node1)){
                adjacencyList.get(keyNode).removeIf(adjacentNode -> adjacentNode.getID().equals(node2));
            }
        }
    }

    public void printAdjacencyList() {
        System.out.println("Adjacency List:");
        for (GraphNode node : adjacencyList.keySet()) {
            List<GraphNode> adjacentNodes = adjacencyList.get(node);

            System.out.print("Node " + node.getID() + " is adjacent to: ");
            for (GraphNode adjacentNode : adjacentNodes) {
                System.out.print(adjacentNode.getID() + " ");
            }
            System.out.println();
        }
    }

    public void printNodesViaIterator(){
        Iterator<GraphNode> iterator = adjacencyList.keySet().iterator();
        System.out.println("Nodes:");
        while (iterator.hasNext()){
            System.out.println(iterator.next().getID());
        }
    }

    public void printNodesBackwardViaIterator(){
        ListIterator<GraphNode> iterator = new ArrayList<>(adjacencyList.keySet())
                .listIterator(adjacencyList.size());
        System.out.println("Nodes (in reverse order):");

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous().getID());
        }
    }

    public List<Entry<GraphNode, GraphNode>> getEdgesList() {
        List<Entry<GraphNode, GraphNode>> edges = new ArrayList<>();
        for (Entry<GraphNode, List<GraphNode>> entry : adjacencyList.entrySet()) {
            GraphNode sourceNode = entry.getKey();
            for (GraphNode targetNode : entry.getValue()) {
                edges.add(new AbstractMap.SimpleEntry<>(sourceNode, targetNode));
            }
        }

        return edges;
    }

    public void printEdgesViaIterator() {
        List<Entry<GraphNode, GraphNode>> edges = getEdgesList();
        Iterator<Entry<GraphNode, GraphNode>> iterator = edges.iterator();

        System.out.println("Edges:");
        while (iterator.hasNext()){
            Entry<GraphNode, GraphNode> edge = iterator.next();
            System.out.println(edge.getKey().getID() +"->"+ edge.getValue().getID());
        }
    }

    public void printEdgesBackwardViaIterator() {
        List<Entry<GraphNode, GraphNode>> edges = getEdgesList();
        ListIterator<Entry<GraphNode, GraphNode>> iterator = edges.listIterator(edges.size());

        System.out.println("Edges (in reverse order):");
        while (iterator.hasPrevious()) {
            Entry<GraphNode, GraphNode> edge = iterator.previous();
            System.out.println(edge.getKey().getID() + "->" + edge.getValue().getID());
        }
    }

    public void printEdgesIncidentToNodeViaIterator(String ID){
        List<Entry<GraphNode, GraphNode>> edges = getEdgesList();
        Iterator<Entry<GraphNode, GraphNode>> iterator = edges.iterator();

        System.out.println("Edges incident to the " + ID+ " are:");
        while (iterator.hasNext()){
            Entry<GraphNode, GraphNode> edge = iterator.next();
            if (edge.getValue().getID().equals(ID) || edge.getKey().getID().equals(ID)){
                System.out.println("Node "+ID+" incident to edge: "+edge.getKey().getID() +"->"+ edge.getValue().getID());
            }
        }
    }

    public void printEdgesIncidentToNodeBackwardViaIterator(String ID){
        List<Entry<GraphNode, GraphNode>> edges = getEdgesList();
        ListIterator<Entry<GraphNode, GraphNode>> iterator = edges.listIterator(edges.size());

        System.out.println("Edges incident to the " + ID + " are (in reverse order):");
        while (iterator.hasPrevious()) {
            Entry<GraphNode, GraphNode> edge = iterator.previous();
            if (edge.getValue().getID().equals(ID) || edge.getKey().getID().equals(ID)) {
                System.out.println("Node " + ID + " incident to edge: " + edge.getKey().getID() + "->" + edge.getValue().getID());
            }
        }
    }

    public void printEdgesAdjacentToNodeViaIterator(String ID){
        List<Entry<GraphNode, GraphNode>> edges = getEdgesList();
        Iterator<Entry<GraphNode, GraphNode>> edgeIterator = edges.iterator();

        List<Entry<GraphNode, GraphNode>> adjacentEdges = new ArrayList<>();
        while (edgeIterator.hasNext()){
            Entry<GraphNode, GraphNode> edge = edgeIterator.next();
            if ((edge.getValue().getID().equals(ID) || edge.getKey().getID().equals(ID))){
                adjacentEdges.add(edge);
            }
        }

        Iterator<Entry<GraphNode, GraphNode>> adjacentEdgesIterator = adjacentEdges.iterator();
        System.out.println("Edges adjacent to the "+ID+" are:");
        while (adjacentEdgesIterator.hasNext()){
            Entry<GraphNode, GraphNode> edge = adjacentEdgesIterator.next();
            System.out.println(edge.getKey().getID() +"->"+ edge.getValue().getID());
        }
    }

    public void printEdgesAdjacentToNodeBackwardViaIterator(String ID){
        List<Entry<GraphNode, GraphNode>> edges = getEdgesList();
        Iterator<Entry<GraphNode, GraphNode>> edgeIterator = edges.iterator();

        List<Entry<GraphNode, GraphNode>> adjacentEdges = new ArrayList<>();
        while (edgeIterator.hasNext()){
            Entry<GraphNode, GraphNode> edge = edgeIterator.next();
            if ((edge.getValue().getID().equals(ID) || edge.getKey().getID().equals(ID))){
                adjacentEdges.add(edge);
            }
        }

        ListIterator<Entry<GraphNode, GraphNode>> adjacentEdgesIterator = adjacentEdges.listIterator(adjacentEdges.size());
        System.out.println("Edges adjacent to the " + ID + " are (in reverse order):");
        while (adjacentEdgesIterator.hasPrevious()) {
            Entry<GraphNode, GraphNode> edge = adjacentEdgesIterator.previous();
            System.out.println(edge.getKey().getID() + "->" + edge.getValue().getID());
        }
    }
}
