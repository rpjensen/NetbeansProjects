

package jensen_hw3_heaps;

import java.util.Arrays;
import java.util.Random;

/**
 * Driver class for HW 3 on implementing a heap from scratch
 * @author Ryan Jensen
 * @version September, 14 2014
 */
public class HeapDriver {

    /**
     * Test cases for MaxHeap. Generates 25 test arrays of length [0,50]
     * with values between [-20,20] generated randomly.  They are then compared
     * against a sorted array using the arrays utility and compared for equality.
     * The method prints the original array, heap, the sorted array, and the result
     * of heapsort.  It also prints if the heapsort result isn't equal to array sort
     * utility results.
     * @param args not used
     */
    public static void main(String[] args) {
         //testing for MaxHeap
        
        int[] currentTestCase = {};
        Random gen = new Random();
        
        //for 25 test cases
        for (int i = 0; i < 25; i++){
            //of size [0,50]
            int size = gen.nextInt(51);
            currentTestCase = new int[size];
            for (int j = 0; j < currentTestCase.length; j++){
                //populate with number between [-20,20]
                currentTestCase[j] = gen.nextInt(41) - 20;
            }
            //print the original array
            System.out.println("PreSort: " + Arrays.toString(currentTestCase));
            //create the heap
            MaxHeap heap = new MaxHeap(currentTestCase);
            //print the heap
            heap.printMaxHeap();
            //sort the array and print the array
            Arrays.sort(currentTestCase);
            System.out.println("PostSort: " + Arrays.toString(currentTestCase));
            //heapsort and print result
            heap.heapSort();
            System.out.println("HeapSort: " + heap.toString());
            //if they aren't the same array string
            if (! heap.toString().equals(Arrays.toString(currentTestCase))){
                //print not equal
                System.out.println("******* Not Equal *******");
            }
            System.out.println("----------------------\n");   
        }
        
        System.out.println("\nIf a array sort and heap sort don't have equal values the following string will print");
        System.out.println("******* Not Equal *******");



        
        
    }
    
}
