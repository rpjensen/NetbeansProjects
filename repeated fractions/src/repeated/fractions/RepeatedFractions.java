/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repeated.fractions;

import java.util.Scanner;

/**
 *
 * @author Ryan
 */
public class RepeatedFractions {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        double x;
        int p;
        int q;
        int n;
        double y;
        long w;
        long n1;
        long n2;
        int type;
        while (1 == 1){
            type = 2;
        while ((type != 0) && (type != 1)){
        System.out.println("Enter 1 for rational, 0 for real:");
        type = keyboard.nextInt();
        }
        if (type == 0){
        System.out.println("Enter x p q n such that x^(p/q) is being approximated to n decimal places:");
        x = keyboard.nextDouble();
        p = keyboard.nextInt();
        q = keyboard.nextInt();
        n = keyboard.nextInt();
        y = (p/(double)q);
        x = Math.pow(x,y);
        //x = Math.PI;
        w = (long)x;
        x = (x - w);
        n1 = (long)Math.pow(10, n);
        n2 = (long)(x * n1);
        } else {
            System.out.println("Enter p q such that p/q is rational:");
            n1 = keyboard.nextInt();
            n2 = keyboard.nextInt();
            w = n1/n2;
            long r = n1 % n2;
            n1 = n2;
            n2 = r;
        }       
        System.out.printf("[ %d;", w);    
            while(n2 > 1){
                long output = n1/n2;
                System.out.printf(" %d,",output);
                long r = n1 % n2;
                n1 = n2;
                n2 = r;
            }
        if(n2 == 1){
            System.out.printf(" %d]", n1);
        } else {
            System.out.println("Error with program");
            
        }
        System.out.println();
        
        }
    }
}
