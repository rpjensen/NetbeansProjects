
package jensen_csci333_bfs;

/**
 * Testing for Breadth First Search HW
 * @author Ryan Jensen
 * @version 11/11/2014
 */
public class BreathSearchDriver {

    /**
     * Test main functionality of breadth first search using three adjacency lists
     * as our test cases
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        boolean[][] a = {{false, true, true, false, false, false}, {true, false, true, false, true, false}, {true, true, false, true, false, true}, {false, false, true, false, false, false}, {false, true, false, false, false, false}, {false, false, true, false, false, false}};
        boolean[][] b = {{false, false, true, false, true, true}, {false, false, false, false, true, false}, {true, false, false, true, false, false}, {false, false, true, false, true, false}, {true, true, false, true, false, false}, {true, false, false, false, false, false}};
        boolean[][] c = {{false, true, false, true, true, true}, {true, false, true, true, false, false}, {false, true, false, false, true, true}, {true, true, false, false, false, false}, {true, false, true, false, false, false}, {true, false, true, false, false, false}};
        
        Graph first = new Graph(a);
        Graph second = new Graph(b);
        Graph third = new Graph(c);
        
        first.breadthFirstSearch(0);
        second.breadthFirstSearch(0);
        third.breadthFirstSearch(0);
        
        System.out.println(first);
        System.out.println(first.getVertex(2));
        System.out.println();

        
        System.out.println(second);
        System.out.println(second.getVertex(2));
        System.out.println();
        
        System.out.println(third);
        System.out.println(third.getVertex(2));
        System.out.println();        
        
        first.printGraph();
        second.printGraph();
        third.printGraph();
    }
        
}
