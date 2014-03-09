/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testtwo;

import java.util.Arrays;

/**
 *
 * @author Ryan Jensen
 */
public class TestTwo {
    public static int arrayValueCount(int[] numberList, int value){
        Arrays.sort(numberList);
        int count=0;
        for (int i=0; i<numberList.length; i++){
            if(numberList[i]==value){
                count++;
            }//if
        }//for
        return count;
    }//arrayvaluecount
   
    public static void main(String[] args) {
       int[] a = new int[100];
       for (int i=0; i< a.length; i++){
           a[i]= i+1;
       }//for
       a[7]=5;
       for (int i=0; i< a.length; i++){
           System.out.print(a[i]+" ");
       }//for
       a[7]=5;
       //this is my way to show that the arrayValueCount method works 
       System.out.println();
       System.out.println(arrayValueCount(a, 5));
       System.out.println(arrayValueCount(a, 8));
       System.out.println(arrayValueCount(a, 9));
    }//main
}//class
 