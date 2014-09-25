/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jensen_csci333_hw4;

import java.util.Arrays;
import java.util.Random;

/**
 * Solution for hw4 - implementing quicksort
 * @author Ryan Jensen
 * @version September 23, 2014
 */
public class QuickSort {
    
    /**
     * Use the naive quicksort algorithm to sort a given array recursively from 
     * lower to upper index (inclusively)
     * @param array the array to sort (in place)
     * @param lower the lower index
     * @param upper the upper index (inclusive)
     */
    public static void quicksort(int[] array, int lower, int upper){
        if (lower < upper){
            int pivot = partition(array, lower, upper);
            quicksort(array, lower, pivot - 1);
            quicksort(array, pivot + 1, upper);
        }
        //implicit else case for trivial array (length 1)
    }
    
    /**
     * The helper method that moves all the integers lower than the pivot (last item by default)
     * to its left, and all the integers greater than the pivot to the right
     * @param array the array to partition (in place)
     * @param lower the lower index
     * @param upper the upper index (inclusive)
     * @return the index of the pivot value which is now in the correct sorted location
     */
    private static int partition(int[] array, int lower, int upper){
        int pivot = array[upper];
        int smallBucket = lower - 1;//highest index of small bucket
        for (int i = lower; i < upper; i++){
            if (array[i] <= pivot){
                smallBucket++;//now pointing just to the right of the small bucket (large side)
                int temp = array[smallBucket];//store the value from the large bucket
                array[smallBucket] = array[i];//put the small element at the added spot of the small bucket
                array[i] = temp;//put the larger value to the right between the small bucket and unvisited
            }
        }
        //small bucket is pointing to the largest index of the small bucket
        smallBucket++;//now pointing just to the right of the small bucket (large side)
        int temp = array[upper];//store pivot value
        array[upper] = array[smallBucket];//put a value from the large bucket into the last index
        array[smallBucket] = temp;//put the pivot in the spot just to the right of the small bucket (middle)
        
        return smallBucket;//now the middle where the pivot is in the right place
    }
    
    /**
     * Use the randomized quicksort algorithm to sort a given array recursively from 
     * lower to upper index (inclusively) using a randomly chosen pivot
     * Pre-Condition - This method relies on partition using element array[upper] as the pivot
     * @param array the array to sort (in place)
     * @param lower the lower index
     * @param upper the upper index (inclusive)
     */
    public static void randomizedQuicksort(int[] array, int lower, int upper){
        if (lower < upper){
            int length = upper - lower + 1;//inclusive
            Random gen = new Random();
            int pivot = gen.nextInt(length);//[0,length)
            pivot += lower;//[lower,length+lower) = [lower, upper + 1) = [lower, upper]
            int temp = array[pivot];//store the random pivot value
            array[pivot] = array[upper];//the partition method uses the last index as the pivot by default
            array[upper] = temp;//store the random pivot in the last index
            
            //partition and call recursive method
            pivot = partition(array, lower, upper);
            randomizedQuicksort(array, lower, pivot - 1);
            randomizedQuicksort(array, pivot + 1, upper);
        }
        //implicit else case for trivial array (length 1)
    }
    
    
    
    /**
     * Testing for quicksort and randomized quicksort
     * @param args not used
     */
    public static void main(String[] args) {
        Random gen = new Random();
        int qsFailed = 0;//# of quicksort fails
        int rqsFailed = 0;//# of randomized quicksort fails
        int testCases = 50;
        for (int i = 0; i < testCases; i++){
            int length = gen.nextInt(51);
            int[] testControl = new int[length];
            int[] testCase = new int[length];
            int[] testCopy = new int[length];
            for (int j = 0; j < testCase.length; j++){
                int val = gen.nextInt(41) - 20;//[0,40] - 20 = [-20,20]
                testCase[j] = val;//store the value into both copies and control
                testCopy[j] = val;
                testControl[j] = val;
            }
            
            //print the original and sorted version using the Array class as a control for correctness
            System.out.println("Original:         " + Arrays.toString(testControl));
            Arrays.sort(testControl);
            System.out.println("ArraySort:        " + Arrays.toString(testControl));
            //quicksort test
            QuickSort.quicksort(testCase, 0, testCase.length - 1);
            System.out.println("QuickSort:        " + Arrays.toString(testCase));
            if (!Arrays.equals(testControl, testCase)){
                System.out.println("******* NOT EQUAL *******");
                qsFailed++;
            }
            //randomized quicksort test
            QuickSort.randomizedQuicksort(testCopy, 0, testCopy.length - 1);
            System.out.println("Random Quicksort: " + Arrays.toString(testCopy));
            if (!Arrays.equals(testControl, testCopy)){
                System.out.println("******* NOT EQUAL *******");
                rqsFailed++;
            }            
            System.out.println("----------------------------");
            System.out.println();
        }
        
        System.out.println();
        System.out.printf("Test Summary with %d test cases:\nQuicksort Failed: %d \nRandomized Quicksort Failed: %d\n", testCases, qsFailed, rqsFailed);
 
    }
    
}
