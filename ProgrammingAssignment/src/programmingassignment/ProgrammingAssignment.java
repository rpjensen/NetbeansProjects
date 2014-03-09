/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingassignment;

import java.util.Scanner;

/**
 *
 * Here it is.
Write a Java program that will prompt and read a series of strings from the user. Processing will stop when the user enters the string quit. The program will print the number of strings entered (excluding the string quit), the average string length, and the number of possible words. A possible word is a string that contains only letters.
Sample run:

Please enter a string: cat
Please enter a string: Dog
Please enter a string: hamster
Please enter a string: 1234
Please enter a string: quit

Number of strings: 4
Average string length: 4.25
Number of possible words: 3
 */
public class ProgrammingAssignment {
    public static int convert(String input){
        if (!(input.indexOf('0')== -1)|| !(input.indexOf('1')== -1) || !(input.indexOf('2')== -1)|| !(input.indexOf('3')== -1)|| !(input.indexOf('4')== -1)|| !(input.indexOf('5')== -1)|| !(input.indexOf('6')== -1)|| !(input.indexOf('7')== -1)|| !(input.indexOf('8')== -1)|| !(input.indexOf('9')== -1)){
            return 0;
        } else {
            return 1;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner (System.in);
        System.out.println("Please enter a string: ");
        String input= keyboard.next();
        int count=0;
        int wordCount=convert(input);
        int runningAverage=0;
                while (!input.equals("QUIT")){
            runningAverage=runningAverage+input.length();
            System.out.println("Please enter a string: ");
            wordCount=wordCount+convert(input);
            input= keyboard.next();
            count++;
            
        }
        System.out.printf("Number of Strings: %d\n", count);
        double runningAveraged = runningAverage;
        double countd = count;
        double average= (runningAveraged / countd);
        System.out.printf("Average String Length: %6.2f\n", average);
        System.out.printf("Number of Possible Words: %d\n", wordCount);
    }
    
}
