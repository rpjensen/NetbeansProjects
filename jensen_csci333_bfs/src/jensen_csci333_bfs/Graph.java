

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
     * 
     * @param edges 
     */
    public Graph(boolean[][] edges){
        if (edges == null){ throw new IllegalArgumentException("Edges should be non-null");}
        
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
    
    public void printGraph(){
        System.out.println("Order: " + order);
        System.out.println("Edges: ");
        for (boolean[] arr : edges){
            System.out.println(Arrays.toString(arr));
        }
        System.out.println();
    }
    
    public void breadthFirstSearch(int sourceId){
        queue.offer(vertices[sourceId]);
    }
    
}
