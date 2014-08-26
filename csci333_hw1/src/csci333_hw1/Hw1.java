/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csci333_hw1;

/**
 * Programming portion of the first homework
 * @author Ryan Jensen
 * @version August 31, 2014
 */
public class Hw1 {
    
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
    public static int trinarySearch(int[] a, int searchTerm, int startIndex, int endIndex){
        //the length of the sub array is the inclusive distance between start and end
        int length = endIndex - startIndex + 1;
        //check that the sub array is non-empty
        if (length <= 0){return -1;}
        
        //if the length is >= 3 more recurssion is needed
        if (length >= 3){
            int third = length / 3 + startIndex;
            if (searchTerm <= a[third]){
                //we need to go down on equals case to make sure we get the first occurance
                return trinarySearch(a, searchTerm, startIndex, third);
            }
            
            int twoThird = 2 * length / 3 + startIndex;            
            if (searchTerm <= a[twoThird]){
                //once more we go down so that we get the first occurance
                return trinarySearch(a, searchTerm, third+1, twoThird);
            }
            //if it isn't in the lower two thirds then it is in the last third or not there at all
            return trinarySearch(a, searchTerm, twoThird+1, endIndex);
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
        }
        return -1;//To assure to netbeans that a value is returned
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
