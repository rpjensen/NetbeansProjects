package euclidsproblem;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Ryan Jensen
 */
public class EuclidsProblem {

   public static int greatestDivisor(int A, int B){
      
       while (A != B){
           if (A > B){
               int hold = A;
               A = B;
               B = hold - B;
           }//if
           else {
               B = B - A;
           } //else
       }//while
       return A;
       
   }//greatest divisor
    public static void main(String[] args) throws Exception {
        Scanner keyboard = new Scanner (System.in);
        System.out.println("Please enter filename to run: ");
        int A = 0, B = 0;
        
        String fileName = keyboard.next();
        Scanner inFile = new Scanner(new File(fileName));
        
        while (inFile.hasNextInt()){
        A= inFile.nextInt();
        B= inFile.nextInt();
                
        
              
        if (!(A<1 || B < 1)){
        //only runs on pairs of numbers that are positive integers
        // lines contain A,B < 1 are simply skipped
        
        long d = greatestDivisor(A, B);
        long a = A/d;
        long b = B/d;
        long x = 0;
        long ax = 0;
        long y = 0;
        long by= 0;
        long dif = Math.abs(a-b);
        long groupDif = 0;
        
        int i = 1;
        long test = i * dif;//multiples of difference that are tested each iteration of i
        if (B == d){
            x = 0;
            y = 1;
        }// B = d special case or A = B = D special case
        
        else if (A == d){
            x = 1;
            y = 0;
        }// A = d special case
        //end of special case
        
        else {
        while (!((test%a == 1) || (test%b == 1))){
            i++;
            test = i * dif;
        }//runs through iterations of i until a multiple of difference mod a or b = 1
            
         
        
        if (test%a == 1){
            groupDif= test/a;
            x = i + groupDif;
            y = i; 
        }//tests whether multiple of 'a' ended while loop
        else {
            groupDif = test/b;
            x = i;
            y = i + groupDif;
        }//or it was a multiple of 'b' that ended while loop
    }//end of non-specialcase
       
        ax =  a * x;
        by = b * y;
         if (ax > by){
             y = -1 * y;
             by = b * y;
         }
         else {
             x = -1 * x;
             ax = a * x;
         }
        long Ax = ax * d;
        long By = by *d;
        System.out.printf("|A: %2d| |B: %2d| |X: %2d| |Y: %2d| |D: %2d| |Ax: %2d| |By: %2d|\n",
                A, B, x, y, d, Ax, By);
        //this isn't the format they wanted me to follow but I don't think their format was very easy to read so I changed it.
        //debugger outputSystem.out.printf("A: %d, B: %d, d: %d, a: %d, b: %d, dif: %d, i: %d, groupDif: %d, x: %d, ax: %d, y: %d, by: %d, Ax: %d, By: %d\n", A , B, d, a, b, dif, i, groupDif, x, ax, y, by, Ax, By);
        }//if numbers are positive integers
        
            }//while infile has next line
        }//main
}//class