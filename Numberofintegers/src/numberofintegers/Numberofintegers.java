/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numberofintegers;
import java.util.Scanner;
/**
 *
 * @author Ryan
 */
public class Numberofintegers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the larger number");
        int n1 = keyboard.nextInt();
        System.out.println("Please enter the smaller number");
        int n2 = keyboard.nextInt();
        System.out.println("The number of integers in this closed interval is:");
        System.out.println(n1-n2+1);
        
        
    }
}
