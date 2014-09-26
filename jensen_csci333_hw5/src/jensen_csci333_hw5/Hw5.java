/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jensen_csci333_hw5;

import java.util.Arrays;
import java.util.Random;

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
     * A method to find the value associated with a given order statistic in the
     * array given. The first order statistic is the smallest value in the array.
     * Pre-Condition: orderStat is between one and the length of the array
     * Post-Condition: the returned value will be the nth smallest number in the array
     * @param array the array to look through
     * @param orderStat the order statistic to return
     * @return the value for the given orderStat
     */
    public static int randomizedQuickSelect(int[] array, int orderStat){
        if (orderStat < 1 || orderStat > array.length){throw new IllegalArgumentException("Order Stat should be between 1 and array length: " + orderStat);}
        int[] copy = new int[array.length];
        copy = Arrays.copyOf(array, copy.length);
        return randomizedQuickSelect(copy, 0, copy.length-1, orderStat-1);
    }
    
    /**
     * Opposed to the pseudocode in the book, this method avoids complicated array
     * addition for the next i value, but instead exploits the fact that the indices of lower and upper
     * are absolute relative to the full array.  If we know that a value is in sorted place regardless
     * of the other values and it is equal to i, we know we have found the i+1'th order
     * statistic (zero indexing).
     * @param array the array to search through
     * @param lower the lower index of the sub array to consider
     * @param upper the upper index of the sub array to consider
     * @param i the index the order statistic would be located at in a sorted array
     * @return the value of the i+1'th order statistic
     */
    private static int randomizedQuickSelect(int[] array, int lower, int upper, int i){
        if (i < lower || i > upper){throw new IllegalArgumentException(String.format("i: %d should be between lower: %d and upper: %d\n", i, lower, upper));}
        
        int length = upper - lower + 1;//get the length
        Random gen = new Random();
        
        if (length == 1){return array[lower];}//trivial case
        //gen pivot between lower and upper
        int pivot = gen.nextInt(length);//[0,length)
        pivot += lower;//[lower,length+lower) = [lower, upper + 1) = [lower, upper]
        //swap
        int temp = array[pivot];
        array[pivot] = array[upper];
        array[upper] = temp;
        //pivot has the index of the element in the correct place
        pivot = partition(array, lower, upper);
        //now test if pivot equals the order desired else recurse in one of two cases
        if (pivot == i){return array[pivot];}
        else if (i < pivot){
            return randomizedQuickSelect(array, lower, pivot-1, i);
        }
        else {
            return randomizedQuickSelect(array, pivot+1, upper, i);
        }        
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
    
    
    public static void radixSort(int[] array){
        if (array.length <= 1){return;}
        
        int highDecimal = 0;
        for (int i = 0; i < array.length; i++){
            String string = String.valueOf(array[i]);
            if (string.length() > highDecimal){
                highDecimal = string.length();
            }
        }
        highDecimal--;//decimal places are numbered from zero
        
        for (int i = 0; i <= highDecimal; i++){
            array = radixCountingSort(array, i);//switch the reference to the sorted array
        }
    }
    
    
    public static int[] radixCountingSort(int[] array, int decimalPlace){
        int largestValue = 9;//base 10
        int[] digits = new int[array.length];//holds the digits of array for the given decimalPlace
        int[] sorted = new int[array.length];//will hold the sorted version of array
        
        for (int i = 0; i < array.length; i++){
            sorted[i] = 0;//initialize sorted
            digits[i] = getDecimalValue(array[i], decimalPlace);//initialize digits
        }
        
        int[] counts = new int[largestValue+1];//will hold the count of each possible value in [0,largestValue]
        for (int i = 0; i < counts.length; i++){
            counts[i] = 0;//initialize counts to zero
        }
        for (int i: digits){
            counts[i]++;//increase the count for each number found in the original array
        }
        
        counts[0]--;//adjust the first count by 1 due to zero indexing (this adjustment will propegate in the following loop)
        for (int i = 1; i < counts.length; i++){
            counts[i] = counts[i] + counts[i-1];//adjust count to tell us where each value should be in the sorted array
        }
        int[] where = counts;//switch the varname for readibility (where[val] now gives the index of sorted to put val in)
        
        //now we go back to front of the digits array and once we find where it goes we place the original value 
        //digit cooresponded to in its correct place
        for (int i = digits.length - 1; i >= 0; i--){
            int value = digits[i];//the current value we are looking at
            int place = where[value];//the index in sorted to put the value
            sorted[place] = array[i];//put the value from the original array where the digit would have gone (the indexs of digits and array coorespond to each other)
            where[value]--;//the next time value comes up put it one to the left
        } 
        return sorted;
    }
    
    private static int getDecimalValue(int number, int decimal){
        return (number / (int)Math.pow(10, decimal)) % 10;
    }
    
   /**
     * A method to find the value associated with a given order statistic in the
     * array given. The first order statistic is the smallest value in the array.
     * Pre-Condition: orderStat is between one and the length of the array
     * Post-Condition: the returned value will be the nth smallest number in the array
     * @param array the array to look through
     * @param orderStat the order statistic to return
     * @return the value for the given orderStat
     */
    public static int deterministicQuickSelect(int[] array, int orderStat){
        if (orderStat < 1 || orderStat > array.length){throw new IllegalArgumentException("Order Stat should be between 1 and array length: " + orderStat);}
        int[] copy = new int[array.length];
        copy = Arrays.copyOf(array, copy.length);
        return randomizedQuickSelect(copy, 0, copy.length-1, orderStat-1);
    }
        
    /**
     * Opposed to the pseudocode in the book, this method avoids complicated array
     * addition for the next i value, but instead exploits the fact that the indices of lower and upper
     * are absolute relative to the full array.  If we know that a value is in sorted place regardless
     * of the other values and it is equal to i, we know we have found the i+1'th order
     * statistic (zero indexing).
     * @param array the array to search through
     * @param lower the lower index of the sub array to consider
     * @param upper the upper index of the sub array to consider
     * @param i the index the order statistic would be located at in a sorted array
     * @return the value of the i+1'th order statistic
     */
    private static int deterministicQuickSelect(int[] array, int lower, int upper, int i){
        if (i < lower || i > upper){throw new IllegalArgumentException(String.format("i: %d should be between lower: %d and upper: %d\n", i, lower, upper));}
        
        int length = upper - lower + 1;//get the length
        
        if (length == 1){return array[lower];}//trivial case
        
        int groups = length / 5;
        int[] medians = new int[groups + 1];
        for (int j = 0; i <= groups; i++){
            int low = lower + 5 * j;
            int high = low + 4;
            bubbleSort(array, low, high);
            medians[j] = array[low + (length + 1) / 2]; 
        }
        
         int pivotValue = deterministicQuickSelect(Arrays.copyOf(medians, medians.length), 0, medians.length-1, (medians.length + 1)/2);
         int pivot = -1;
         for (int j = 0; j < medians.length; j++){
             if (medians[j] == pivotValue){
                 pivot = j;
                 break;
             }
         }
        
        //swap
        int temp = array[pivot];
        array[pivot] = array[upper];
        array[upper] = temp;
        //pivot has the index of the element in the correct place
        pivot = partition(array, lower, upper);
        //now test if pivot equals the order desired else recurse in one of two cases
        if (pivot == i){return array[pivot];}
        else if (i < pivot){
            return deterministicQuickSelect(array, lower, pivot-1, i);
        }
        else {
            return deterministicQuickSelect(array, pivot+1, upper, i);
        }        
    }
    
    private static void bubbleSort(int[] array, int lower, int upper){
        for (int i = 0; i < array.length; i++){
            int max = i;//max for whats left in the array
            for (int j = i+1; j < array.length; j++){
                if (array[j] > array[max]){
                    max = j;//new max
                }
            }
            //swap max into the lowest index of the remaining values (i)
            int temp = array[max];
            array[max] = array[i];
            array[i] = array[max];
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
