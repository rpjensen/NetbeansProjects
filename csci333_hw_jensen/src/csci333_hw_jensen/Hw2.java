/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csci333_hw_jensen;

import java.util.Random;

/**
 * Programming portion of hw 2
 * @author Ryan Jensen
 * @version September 7, 2014
 */
public class Hw2 {

/**
     * Returns the index of the first occurrence of searchTerm in the array a
     * between the startIndex and endIndex (inclusively) or -1 if not found
     * Pre-Condition: a is a sorted array, startIndex and endIndex are valid 
     * indices into the array a.
     * @param a the array to be searched
     * @param searchTerm the integer to be searched for
     * @param startIndex the first index
     * @param endIndex the last index
     * @return the index of the first occurrence of searchTerm between start and end, else -1
     */
    public static int randomTrinarySearch(int[] a, int searchTerm, int startIndex, int endIndex){
        //the length of the sub array is the inclusive distance between start and end
        int length = endIndex - startIndex + 1;
        //check that the sub array is non-empty
        if (length <= 0){return -1;}
        
        //if the length is >= 3 more recurssion is needed
        if (length >= 3){
            Random rand = new Random();
            int first = rand.nextInt(length);
            int second = rand.nextInt(length - 1);
            int slice1;
            int slice2;
            if (second >= first){
                //second is in [first,n-1] so adding one give second in [first+1,n] as desired
                slice1 = first + startIndex;
                slice2 = second + 1 + startIndex;
            }
            else {
                //second < first so it is the smaller 'slice1'
                slice1 = second + startIndex;
                slice2 = first + startIndex;
            }
            
            if (searchTerm <= a[slice1]){
                //we need to go down on equals case to make sure we get the first occurance
                return randomTrinarySearch(a, searchTerm, startIndex, slice1);
            }
            if (searchTerm <= a[slice2]){
                //once more we go down so that we get the first occurance
                return randomTrinarySearch(a, searchTerm, slice1+1, slice2);
            }
            //if it isn't in the lower two thirds then it is in the last slice1 or not there at all
            return randomTrinarySearch(a, searchTerm, slice2+1, endIndex);
        }
        //else we are at the base case of length 1 or 2
        else {
            //if the length is 1 then it is either at start index or not there
            if (length == 1){ 
                return a[startIndex] == searchTerm ? startIndex : -1;
            }
            //if the length is two it is either at startIndex or the one after start or not there
            if (length == 2){
                if (a[startIndex] == searchTerm){
                    return startIndex;
                }
                else {
                    return a[startIndex+1] == searchTerm ? startIndex+1 : -1;
                } 
            }
            return -1;//assure netbeans we return a value
        }
    }
    
    /**
     * Convenience method to search the whole array recursively 
     * @param a the array to search
     * @param searchTerm the term to search for
     * @return the index of first occurrence, else -1
     */
    public static int randomTrinarySearch(int[] a, int searchTerm){
        return randomTrinarySearch(a, searchTerm, 0, a.length - 1);
    }
    
    /**
     * Test the method randomTrinarySearch using 11 test cases on 5 different array
     * which look at many edge cases such as finding the first occurrence of a
     * repeated value that is on 1/3, 2/3 divide points.
     * @param args not used 
     */
    public static void main(String[] args) {
        //testing for Trinary Search
        int[] test1 = {-45};
        int[] test2 = {-45, 0};
        int[] test3 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        int[] test4 = {0,1,1,1,4,4,7,7,12,14,65,333};
        int[] test5 = new int[0];
        
        System.out.println("Expected: 0");
        System.out.println("Actual: " + randomTrinarySearch(test1, -45));
        System.out.println("----------------------------");
        System.out.println("Expected: -1");
        System.out.println("Actual: " + randomTrinarySearch(test1, -47));
        System.out.println("----------------------------");
        System.out.println("Expected: -1");
        System.out.println("Actual: " + randomTrinarySearch(test2, -1));
        System.out.println("----------------------------");
        System.out.println("Expected: 1");
        System.out.println("Actual: " + randomTrinarySearch(test2, 0));
        System.out.println("----------------------------");
        System.out.println("Expected: 11");
        System.out.println("Actual: " + randomTrinarySearch(test3, 11));
        System.out.println("----------------------------");
        System.out.println("Expected: -1");
        System.out.println("Actual: " + randomTrinarySearch(test3, 19));
        System.out.println("----------------------------");
        System.out.println("Expected: 1");
        System.out.println("Actual: " + randomTrinarySearch(test4, 1));
        System.out.println("----------------------------");
        System.out.println("Expected: 6");
        System.out.println("Actual: " + randomTrinarySearch(test4, 7));
        System.out.println("----------------------------");
        System.out.println("Expected: 10");
        System.out.println("Actual: " + randomTrinarySearch(test4, 65));
        System.out.println("----------------------------");
        System.out.println("Expected: 11");
        System.out.println("Actual: " + randomTrinarySearch(test4, 333));
        System.out.println("----------------------------"); 
        System.out.println("Expected: -1");
        System.out.println("Actual: " + randomTrinarySearch(test5, 65));
        System.out.println("----------------------------");
    }
        
        
}
