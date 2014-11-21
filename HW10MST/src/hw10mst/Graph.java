/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw10mst;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 * @author awhitley
 */
public class Graph {

    // ----- Data Fields -----
    private Vertex[] vertices; // DO NOT rename me without Refactor --> Rename, or you'll break decreaseKey.
    // You need to declare the other data fields, too!
    // You need to define all the constructor(s) and other class methods, too!
    private int order;
    private Edge[][] edges;
    
    /**
     * Creates a new graph based on the weighted adjacency edges contained in the
     * parameter.  Doesn't copy edges or check that it is a square array or properly
     * formed.
     * @param edges the weighted adjacency array (should be square)
     */
    public Graph(Edge[][] edges){
        this.edges = edges;
        vertices = new Vertex[this.edges.length];
        for (int i = 0; i < vertices.length; i++){
            vertices[i] = new Vertex(i, Edge.INFINITE_WEIGHT);
        }
        this.order = vertices.length;
    }
    
    /**
     * @return the number of vertices in the graph
     */
    public int getOrder(){
        return order;
    }
    
    /**
     * @param label the label of the vertex to retrieve from the graph
     * @return the vertex
     * @throws IllegalArgumentException if label is less than zero or greater 
     * than or equal to size
     */
    public Vertex getVertex(int label){
        if (label < 0 || label >= order){ throw new IllegalArgumentException("label should be between 0 and size-1");}
        return vertices[label];
    }
    
    /**
     * @param vert1 the label of the first vertex
     * @param vert2 the label of the second vertex
     * @return the edge from vert1 to vert2, null if vert1 = vert2
     * @throws IllegalArgumentException both vertices should be between zero and size-1
     */
    public Edge getEdge(int vert1, int vert2){
        if (vert1 < 0 || vert1 >= order){ throw new IllegalArgumentException("vert1 should be between 0 and size-1");}
        if (vert2 < 0 || vert2 >= order){ throw new IllegalArgumentException("vert2 should be between 0 and size-1");}
        if (vert1 == vert2){ return null;}
        
        return edges[vert1][vert2];
    }
    
    /**
     * Print a graph representation to the console
     */
    public void printGraph(){
        System.out.println("Order: " + order);
        System.out.println("Edges: " );
        for (Edge[] edgeRow : edges){
            System.out.println(Arrays.toString(edgeRow));
        }
    }
   
    /**
     * Create a string representation of the graph.
     * The general form is Order, Vertices, Edges
     * @return the string representation
     */
    @Override
    public String toString(){
        String string = "Order " + order + "\n";
        string += "Vertices: " + Arrays.toString(vertices) + "\n";
        string += "Edges:\n";
        for (Edge[] edgeRow : edges){
            string += Arrays.toString(edgeRow) + "\n";
        }
        return string;
    }
    
    /**
     * Apply Prims MST algorithm to the graph starting at the vertex with label zero.
     * Doesn't return anything, but does cause side effects in the vertex class contained
     * in the graph.
     */
    public void primMst(){
        PriorityQueue<Vertex> queue = new PriorityQueue<>(order);
        for (Vertex v : vertices){
            queue.add(v);
        }
        vertices[0].setKey(0);
        while (!queue.isEmpty()){
            Vertex u = queue.poll();
            for (int i = 0; i < edges[u.getLabel()].length; i++){
                Vertex v = vertices[i];
                Edge current = edges[u.getLabel()][i];
                if (isStillInQ(queue, i) && current.getWeight() < v.getKey()){
                    v.setParent(u);
                    decreaseKey(queue, i, current.getWeight());
                }
            }
        }
        
    }
    
    // ----- Private Helper Methods -----
    /**
     * Tells you whether a Vertex with the provided label is still in the queue
     * q. This is needed because, since the PriorityQueue is keyed on key not
     * label, it will tell you whether a given key is still in it, but not
     * whether a given label is still in it.
     *
     * @param q a PriorityQueue<Vertex>
     * @param label The Vertex label to check for.
     * @return Returns true if a Vertex with matching label is in q. Returns
     * false if no Vertex with matching label is in q.
     */
    private boolean isStillInQ(PriorityQueue<Vertex> q, int label) {

        Vertex[] array = q.toArray(new Vertex[0]); // dump out an array of the elements

        // traverse the array of elements, searching for a matching label
        for (int i = 0; i < array.length; i++) {
            if ((array[i]).getLabel() == label) {
                return true;
            }
        }

        return false; // no matching label found

    }

    /**
     * Takes the Vertex with matching label in queue q, and reduces its key to
     * newKey. Will return false if Vertex is not in the queue, or newKey is
     * larger than old key. Will return true if it successfully reduced the key.
     *
     * @param q The priority queue of Vertex
     * @param label The label of the Vertex whose key you want to decrease
     * @param newKey
     * @return Returns false if the Vertex with the given label is not in the
     * queue. Returns false if the newKey is larger than the old key of Vertex
     * with given label. Returns true otherwise; the vertex with the given label
     * had its key changed to newKey.
     */
    private boolean decreaseKey(PriorityQueue<Vertex> q, int label, int newKey) {
        // PAY NO ATTENTION TO THE CODE BEHIND THAT CURTAIN! ;)
        // Don't worry about the code in this method body. Read the Javadoc above.

        int indexOfVertex = -1;
        Vertex[] array = q.toArray(new Vertex[0]);

        // check to see if Vertex with the given label is in the Priority queue.
        for (int i = 0; i < array.length; i++) {
            if ((array[i]).getLabel() == label) {
                indexOfVertex = i;
            }
        }

        // if Vertex with the given label is not in the queue, do nothing and return false
        // also returns false if the new key is larger than the old key.
        if (indexOfVertex == -1 || newKey > array[indexOfVertex].getKey()) {
            return false;
        }

        // Without decreaseKey already in the PriorityQueue class,
        // I must remove the vertex and add it again with a different key. 
        // Actually, I'm emptying the queue, then I am inserting all the other
        // vertices back in, except the one being decreased. Then I am reinserting
        // the decreased vertex, with the newKey key value.
        // I had to resort to this because technically you can't remove 
        // an element from a PriorityQueue by its label, since it is keyed on something else.
        Vertex vertexToDecrease = array[indexOfVertex];
        vertexToDecrease.setKey(newKey);

        // clear and rebuild the priority queue
        q.clear();
        for (int i = 0; i < array.length; i++) { // add everything else
            if (i != indexOfVertex) { // not including the old vertex to be reduced
                q.add(array[i]);
            }
        }
        q.add(vertexToDecrease); // insert the decreased vertex back in

        return true; // queue is now effectively identical to before, but with one Vertex's key reduced to newKey
    }
}
