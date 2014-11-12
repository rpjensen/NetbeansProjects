

package jensen_csci333_bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents an undirected graph of vertices connected by edges
 * @author Ryan Jensen
 * @version Nov 6, 2014
 */
public class Graph {
    private int order;
    private boolean[][] edges;
    private Vertex[] vertices;
    private Queue<Vertex> queue;
    
    /**
     * Construct a new graph based off the given adjacency matrix.  The graph is
     * undirected and the vertices are labeled based on their order in the adjacency
     * from 0, n-1
     * @param edges the square adjacency matrix of boolean values that correspond to
     * edges either existing or not existing between the vertices
     * @throws NullPointerException if edges is null 
     * @throws IllegalArgumentException if edges is not a square matrix
     */
    public Graph(boolean[][] edges){
        if (edges == null){ throw new NullPointerException("Edges should be non-null");}
                
        this.edges = new boolean[edges.length][edges.length];
        order = this.edges.length;
        vertices = new Vertex[this.edges.length];

        for (int i = 0; i < edges.length; i++){
            //argument check while we are doing the copy
            if (edges[i].length != edges.length){
                throw new IllegalArgumentException("Edges should be a square adjacency matrix");
            }
            for (int j = 0; j < edges[i].length; j++){
                this.edges[i][j] = edges[i][j];
            }
            vertices[i] = new Vertex(i);
        }        
        
        queue = new LinkedList<>();
    }
    
    /**
     * Getter for a particular vertex in the graph
     * @param index the index of this vertex between [0,order-1]
     * @return the vertex handle if the index was valid
     * @throws IllegalArgumentException if the index was out of bounds
     */
    public Vertex getVertex(int index){
        if (index < 0 || index >= order){ throw new IllegalArgumentException("Index should be between 0 and order-1");}
        return vertices[index];
    }
    
    /**
     * Creates a string representation of the graph.  The general form follows:
     * "Vertex Order: order
     *  Vertex.toString (each on its own row)
     *  Edges:
     *  Arrays.toString(single row of adjacency matrix)
     * @return the string representation
     */
    @Override
    public String toString(){
        String returnString = "Vertex Order: " + order + "\n";
        for (Vertex vert : vertices){
            returnString += vert.toString() + "\n";
        }
        returnString += "Edges: \n";
        for (boolean[] arr : edges){
            returnString += Arrays.toString(arr) + "\n";
        }
        return returnString;
    }
    
    /**
     * Prints the order of the graph and the adjacency matrix to the console
     */
    public void printGraph(){
        System.out.println("Order: " + order);
        System.out.println("Edges: ");
        for (boolean[] arr : edges){
            System.out.println(Arrays.toString(arr));
        }
        System.out.println();
    }
    
    /**
     * Perform a breadth first search starting at the vertex at index sourceId.
     * This method affects the parent, color, and distance fields of the vertices
     * stored in this graph.
     * @param sourceId the source(starting point) of the breadth first search
     */
    public void breadthFirstSearch(int sourceId){
        vertices[sourceId].setColor(Vertex.GRAY);
        vertices[sourceId].setDistance(0);
        
        queue.offer(vertices[sourceId]);
        while (!queue.isEmpty()){
            Vertex v = queue.remove();//Going for nosuchelm exception over nullpointer since we just did the not empty check
            v.setColor(Vertex.BLACK);
            boolean[] neighbors = edges[v.getLabel()];
            for (int i = 0; i < neighbors.length; i++){
                if (neighbors[i] && vertices[i].getColor() == Vertex.WHITE){
                    Vertex u = vertices[i];
                    u.setColor(Vertex.GRAY);
                    u.setDistance(v.getDistance()+1);
                    u.setParent(v);
                    queue.offer(u);
                }
            }
        }
    }
    
}
