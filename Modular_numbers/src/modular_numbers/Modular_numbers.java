/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modular_numbers;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author Ryan
 */
public class Modular_numbers {
    public static long successiveSquares(long x, int n){
        
        
            
            double exx = x;
            double eye = n;
            double power = Math.pow(exx, eye);
            long returnValue = (long) power;
        return returnValue;   
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Scanner keyboard = new Scanner (System.in);
        System.out.println("Please enter filename to run: ");
        
        String fileName = keyboard.next();
        Scanner inFile = new Scanner(new File(fileName));
        System.out.println("Would you like to use successive squaring to add to the file?");
        PrintStream outFile = new PrintStream(fileName+"SS");
        if (keyboard.next().equals("yes")){
        System.out.println("Choose base x to power n modulo m");
        int x = keyboard.nextInt();
        int enn = keyboard.nextInt();
        int m = keyboard.nextInt();
        long zee = x;
        outFile.print("1) "+zee);
        int n =  (int)(Math.log10(enn)/ Math.log10(2));
        
            for (int i = 1; i <= n; i++){
            zee = successiveSquares(zee, 2)% m;
            outFile.printf("  %2.0f) %d",(Math.pow(2, i)),zee);
           
            }
             outFile.println();
       System.exit(0);
        }
        else {
        while (inFile.hasNextLine()){
       int m = inFile.nextInt();
            int n = inFile.nextInt();
      long b = 1;
            for (int i=1; i <=n; i++){
           
        
        b = b * inFile.nextLong()% m;
       }
        
        System.out.printf("x is congruent to %d mod %d\n", b, m);
               
        }//while
        
    }//else
    }
}
