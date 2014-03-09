/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pythagoreantriple;

import java.util.Scanner;

/**
 *
 * 3-4-5 (plus any multiple of 3-4-5)
 * 5-12-13 (plus any multiple of 5-12-13)
 * 8-15-17 (plus any multiple of 8-15-17)
 * use loops to go through all cases of a,b,c between [1,1000]
 * and use if statement to only print pythagorean triples
 * change if statement so that it doesn't display doubles a <= b convention
 * 
 */
public class PythagoreanTriple {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     int a = 10000;
     int b = 10000;
     int c = 10000;
     for (int i = 1; i <= c; i++){
         for (int ii = 1; ii <= b; ii++ ){
             for (int iii = 1; iii <= a; iii++){
                 if ((iii * iii + ii * ii) == (i * i) && (iii <= ii)){
                     System.out.printf("a:%d, b:%d, c:%d is a pythagorean triple\n", iii, ii, i);
                 }//if statement
             }//loop 3
         }//loop 2 
     }//loop 1
    
     
    }//main
}//public class
