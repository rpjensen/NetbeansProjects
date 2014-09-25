/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jensen_csci333_hw5;

/**
 * Implementation and testing for CSCI 333 -- HW 5
 * @author Ryan Jensen
 * @version October 1, 2014
 */
public class Hw5 {
    
    /**
     * A void method to sort array (out of place) into the allocated array called sorted
     * Pre-Condition: Array and sorted are the same length
     * Pre-Condition: Values in Array are between 0 and largestValue (inclusive)
     * Pre-Condition: LargestValue is greater than or equal to the largest value in array
     * Post-Condition: sorted is the sorted version of array and array is unchanged
     * @param array the array to sort
     * @param sorted the allocated array to store the sorted values
     * @param largestValue the largest value stored in array
     */
    public static void countingSort(int[] array, int[] sorted, int largestValue){
        if (array.length != sorted.length){throw new IllegalArgumentException("Array and Sorted should be the same length");}
        if (largestValue < 0){throw new IllegalArgumentException("LargestValue should be greater than or equal to zero");}
        //now that errors have been checked we can procede
        int[] counts = new int[largestValue+1];//will hold the count of each possible value in [0,largestValue]
        for (int i = 0; i < counts.length; i++){
            counts[i] = 0;//initialize counts to zero
        }
        for (int i: array){
            if (i > largestValue || i < 0){
                throw new IllegalArgumentException("array has a value larger than largestValue or less than 0: " + i);
            }
            counts[i]++;//increase the count for each number found in the original array
        }
        
        counts[0]--;//adjust the first count by 1 due to zero indexing (this adjustment will propegate in the following loop)
        for (int i = 1; i < counts.length; i++){
            counts[i] = counts[i] + counts[i-1];//adjust count to tell us where each value should be in the sorted array
        }
        int[] where = counts;//switch the varname for readibility (where[val] now gives the index of sorted to put val in)
        
        //now we go back to front of the original array putting the values into the correct place in sorted
        for (int i = array.length - 1; i >= 0; i--){
            int value = array[i];//the current value we are looking at
            int place = where[value];//the index in sorted to put the value
            sorted[place] = value;//put the value there
            where[value]--;//the next time value comes up put it one to the left
        } 
    }
    
    /**
     * Testing for HW 5
     * @param args not used
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
