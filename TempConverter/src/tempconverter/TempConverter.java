/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tempconverter;

import java.util.Scanner;

/**
 *
 * @author Ryan Jensen
 */
public class TempConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please input a temperature in Fahrenheit: ");
        float tempF = keyboard.nextFloat();
        float tempCelcius; = (5/9)(tempF-32); 
        System.out.printf("The temperature in Celcius is %6.2f", tempCelcius);
        
    }//main
}//public
