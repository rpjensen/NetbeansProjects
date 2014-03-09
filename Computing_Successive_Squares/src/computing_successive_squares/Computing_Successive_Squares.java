/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computing_successive_squares;

import java.util.Scanner;

/**
 *
 * @author Ryan
 */
public class Computing_Successive_Squares {
    public static long highestPowerOfTwo(long n){
        long poTwo = -1;
        long d = 2;
        while(d>1){
            
            poTwo++;
            d = n / (long)(Math.pow(2, poTwo));
        }
        
        return poTwo;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       String z = "run";
        Scanner keyboard = new Scanner(System.in);
        while (z.equals("run")){
        System.out.println("Please enter a: n: m: in that order");
        long a = keyboard.nextLong();
        long n = keyboard.nextLong();
        long m = keyboard.nextLong();
       
        int successiveSteps = (int)highestPowerOfTwo(n);
        //long poTwo = highestPowerOfTwo(n);
        System.out.println();
        //binary array
        long[] binaryNumber = new long[successiveSteps+1];
        long remainder = n;
        for (int i = successiveSteps; i>=0; i--){
            if(Math.pow(2,i)<= remainder){
                remainder = remainder - (int)Math.pow(2, i);
                binaryNumber[i]=1;
            }//if
            else{
                binaryNumber[i]=0;
            }//else
          
        }//for
        //end binary array
        //computing the array of successive squares
        long[] successiveSquare = new long[successiveSteps+1];
        long[] unModedSS= new long[successiveSteps+1];
        successiveSquare[0] = a;
        unModedSS[0]=a;
        for(int i =1; i<=successiveSteps; i++){
            unModedSS[i] = ((long)Math.pow(successiveSquare[i-1],2));
            successiveSquare[i] = ((long)Math.pow(successiveSquare[i-1],2))% m;
        }//for
        //end computing the array of successive squares
        
        //start computation of final answer
        long result = 1;
        for (int ii = successiveSteps; ii>=0; ii--){
            if (binaryNumber[ii]==1){
                result = (result * successiveSquare[ii]) % m;  
            }//if
            
        }//for
        
        //start display
        System.out.print("Powers of two: "+ successiveSteps+ "  ");
        for(int i=binaryNumber.length-1; i>=0; i--){
            System.out.print(binaryNumber[i]);
        }
        System.out.println();
        System.out.print("Powers of two: ");
        for(int i=binaryNumber.length-1; i>=0; i--){
            if(binaryNumber[i]==1){
            System.out.print(i+"=>"+(long)Math.pow(2,i)+" ");
            }
        }
       
        System.out.println();
            System.out.print("UnModedSS ");
           for(int i=0; i < binaryNumber.length; i++){
            System.out.print(unModedSS[i]+" ");
        }
        System.out.println();
        System.out.print("Successive Squares ");
           for(int i=0; i < binaryNumber.length; i++){
            System.out.print(successiveSquare[i]+" ");
        }
         System.out.println();
         System.out.print("Used in Binary ");
           for (int ii = successiveSteps; ii>=0; ii--){
            if (binaryNumber[ii]==1){
                System.out.print(successiveSquare[ii]+" ");  
            }//if
           }
        System.out.println();
        System.out.println(result);
                }//while
        
    }//void
}//class
