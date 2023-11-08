package by.lupach.grapheditor.core;

import java.util.*;
import java.util.Map.Entry;


public class Graph <T extends Comparable<T>>{
    private Map<GraphNode<T>, List<GraphNode<T>>> adjacencyList;


    public Graph() {
        adjacencyList = new LinkedHashMap<>();
    }

    public GraphNode getNodeById(T id) {
        for (GraphNode node : adjacencyList.keySet()) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    public boolean isNodeExist(T id){
        for (GraphNode node : adjacencyList.keySet()){
            if (node.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void addNode(T id) {
        adjacencyList.put(new GraphNode(id), new ArrayList<>());
    }


    public int getNodesCount(){
        return adjacencyList.size();
    }

    public boolean isEdgeExist(T node1, T node2){
        for (GraphNode keyNode: adjacencyList.keySet()){
            if (keyNode.getId().equals(node1)){
                for (GraphNode valueNode : adjacencyList.get(keyNode)){
                    if (valueNode.getId().equals(node2)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void deleteNode(T id) {
        if (!isNodeExist(id)) {
            throw new IllegalArgumentException("Node doesn't exist");
        }

        GraphNode nodeToDelete = getNodeById(id);
        adjacencyList.remove(nodeToDelete);

        for (GraphNode keyNode : adjacencyList.keySet()) {
            List<GraphNode<T>> adjacentNodes = adjacencyList.get(keyNode);
            adjacentNodes.removeIf(adjacentNode -> adjacentNode.getId().equals(id));
        }
    }

    public int getNodeInDegree(T id){
        if (!isNodeExist(id)) {
            throw new IllegalArgumentException("Node doesn't exist");
        }

        int inDegree = 0;
        for (GraphNode keyNode : adjacencyList.keySet()) {
            List<GraphNode<T>> adjacentNodes = adjacencyList.get(keyNode);
            for (GraphNode valueNode : adjacentNodes){
                if (valueNode.getId().equals(id)){
                    inDegree++;
                }
            }
        }


        return inDegree;
    }

    public int getNodeOutDegree(T id){
        if (!isNodeExist(id)) {
            throw new IllegalArgumentException("Node doesn't exist");
        }
        return adjacencyList.get(getNodeById(id)).size();
    }

    public int getNodeDegree(T id){
        return getNodeInDegree(id)+getNodeOutDegree(id);
    }

    public void deleteNodeByIdViaIterator(T id){
        Iterator<GraphNode<T>> nodesIterator = adjacencyList.keySet().iterator();

        while (nodesIterator.hasNext()){
            GraphNode node = nodesIterator.next();

            List<GraphNode<T>> adjacentNodes = adjacencyList.get(node);
            adjacentNodes.removeIf(adjacentNode -> adjacentNode.getId().equals(id));

            if (node.getId().equals(id)){
                adjacencyList.remove(node);
                break;
            }
        }
    }

    public void addEdge(T node1, T node2) {
        if (!isEdgeExist(node1, node2)){
            adjacencyList.computeIfAbsent(getNodeById(node1), k -> new ArrayList<>()).add(new GraphNode(node2));
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

    public void deleteEdge(T node1, T node2) {
        if (!isEdgeExist(node1, node2)){
            System.out.println("Edge doesn't exist");
            return;
        }

        for (GraphNode keyNode : adjacencyList.keySet()){
            if (keyNode.getId().equals(node1)){
                adjacencyList.get(keyNode).removeIf(adjacentNode -> adjacentNode.getId().equals(node2));
            }
        }
    }

    public void deleteEdgeViaIterator(T node1, T node2){
        Iterator<GraphNode<T>> nodesIterator = adjacencyList.keySet().iterator();

        while (nodesIterator.hasNext()){
            GraphNode keyNode = nodesIterator.next();

            if (keyNode.getId().equals(node1)){
                adjacencyList.get(keyNode).removeIf(adjacentNode -> adjacentNode.getId().equals(node2));
            }
        }
    }

    public void printAdjacencyList() {
        System.out.println("Adjacency List:");
        for (GraphNode node : adjacencyList.keySet()) {
            List<GraphNode<T>> adjacentNodes = adjacencyList.get(node);

            System.out.print("Node " + node.getId() + " is adjacent to: ");
            for (GraphNode adjacentNode : adjacentNodes) {
                System.out.print(adjacentNode.getId() + " ");
            }
            System.out.println();
        }
    }

    public void printNodesViaIterator(){
        Iterator<GraphNode<T>> iterator = adjacencyList.keySet().iterator();
        System.out.println("Nodes:");
        while (iterator.hasNext()){
            System.out.println(iterator.next().getId());
        }
    }

    public void printNodesBackwardViaIterator(){
        ListIterator<GraphNode<T>> iterator = new ArrayList<>(adjacencyList.keySet())
                .listIterator(adjacencyList.size());
        System.out.println("Nodes (in reverse order):");

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous().getId());
        }
    }

    public List<Entry<GraphNode<T>, GraphNode<T>>> getEdgesList() {
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = new ArrayList<>();
        for (Entry<GraphNode<T>, List<GraphNode<T>>> entry : adjacencyList.entrySet()) {
            GraphNode<T> sourceNode = entry.getKey();
            for (GraphNode<T> targetNode : entry.getValue()) {
                edges.add(new AbstractMap.SimpleEntry<>(sourceNode, targetNode));
            }
        }
        return edges;
    }


    public void printEdgesViaIterator() {
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = getEdgesList();
        Iterator<Entry<GraphNode<T>, GraphNode<T>>> iterator = edges.iterator();

        System.out.println("Edges:");
        while (iterator.hasNext()){
            Entry<GraphNode<T>, GraphNode<T>> edge = iterator.next();
            System.out.println(edge.getKey().getId() +"->"+ edge.getValue().getId());
        }
    }

    public void printEdgesBackwardViaIterator() {
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = getEdgesList();
        ListIterator<Entry<GraphNode<T>, GraphNode<T>>> iterator = edges.listIterator(edges.size());

        System.out.println("Edges (in reverse order):");
        while (iterator.hasPrevious()) {
            Entry<GraphNode<T>, GraphNode<T>> edge = iterator.previous();
            System.out.println(edge.getKey().getId() + "->" + edge.getValue().getId());
        }
    }

    public void printEdgesIncidentToNodeViaIterator(T id){
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = getEdgesList();
        Iterator<Entry<GraphNode<T>, GraphNode<T>>> iterator = edges.iterator();

        System.out.println("Edges incident to the " + id+ " are:");
        while (iterator.hasNext()){
            Entry<GraphNode<T>, GraphNode<T>> edge = iterator.next();
            if (edge.getValue().getId().equals(id) || edge.getKey().getId().equals(id)){
                System.out.println("Node "+id+" incident to edge: "+edge.getKey().getId() +"->"+ edge.getValue().getId());
            }
        }
    }

    public void printEdgesIncidentToNodeBackwardViaIterator(T id){
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = getEdgesList();
        ListIterator<Entry<GraphNode<T>, GraphNode<T>>> iterator = edges.listIterator(edges.size());

        System.out.println("Edges incident to the " + id + " are (in reverse order):");
        while (iterator.hasPrevious()) {
            Entry<GraphNode<T>, GraphNode<T>> edge = iterator.previous();
            if (edge.getValue().getId().equals(id) || edge.getKey().getId().equals(id)) {
                System.out.println("Node " + id + " incident to edge: " + edge.getKey().getId() + "->" + edge.getValue().getId());
            }
        }
    }

    public void printEdgesAdjacentToNodeViaIterator(T id){
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = getEdgesList();
        Iterator<Entry<GraphNode<T>, GraphNode<T>>> edgeIterator = edges.iterator();

        List<Entry<GraphNode<T>, GraphNode<T>>> adjacentEdges = new ArrayList<>();
        while (edgeIterator.hasNext()){
            Entry<GraphNode<T>, GraphNode<T>> edge = edgeIterator.next();
            if ((edge.getValue().getId().equals(id) || edge.getKey().getId().equals(id))){
                adjacentEdges.add(edge);
            }
        }

        Iterator<Entry<GraphNode<T>, GraphNode<T>>> adjacentEdgesIterator = adjacentEdges.iterator();
        System.out.println("Edges adjacent to the "+id+" are:");
        while (adjacentEdgesIterator.hasNext()){
            Entry<GraphNode<T>, GraphNode<T>> edge = adjacentEdgesIterator.next();
            System.out.println(edge.getKey().getId() +"->"+ edge.getValue().getId());
        }
    }

    public void printEdgesAdjacentToNodeBackwardViaIterator(T id){
        List<Entry<GraphNode<T>, GraphNode<T>>> edges = getEdgesList();
        Iterator<Entry<GraphNode<T>, GraphNode<T>>> edgeIterator = edges.iterator();

        List<Entry<GraphNode<T>, GraphNode<T>>> adjacentEdges = new ArrayList<>();
        while (edgeIterator.hasNext()){
            Entry<GraphNode<T>, GraphNode<T>> edge = edgeIterator.next();
            if ((edge.getValue().getId().equals(id) || edge.getKey().getId().equals(id))){
                adjacentEdges.add(edge);
            }
        }

        ListIterator<Entry<GraphNode<T>, GraphNode<T>>> adjacentEdgesIterator = adjacentEdges.listIterator(adjacentEdges.size());
        System.out.println("Edges adjacent to the " + id + " are (in reverse order):");
        while (adjacentEdgesIterator.hasPrevious()) {
            Entry<GraphNode<T>, GraphNode<T>> edge = adjacentEdgesIterator.previous();
            System.out.println(edge.getKey().getId() + "->" + edge.getValue().getId());
        }
    }
}
