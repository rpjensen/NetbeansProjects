/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Scanner;

/**
 *
 * @author Ryan
 */
public class Test {
    public static String convert(int season){
        switch(season){
            case 1: return "Spring";
            case 2: return "Summer";
            case 3: return "Fall";
            case 4: return "Winter";
            default: return "Invalid value given";
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner keyboard = new Scanner (System.in);
       //Month Day, Year 
       String dateStr = keyboard.nextLine();
       int limit = dateStr.indexOf(' ');
       int limit2 = dateStr.indexOf(',');
       String month = dateStr.substring(0,limit);
       String day = dateStr.substring(limit+1,limit2);
       String year = dateStr.substring(limit2+2);
       System.out.printf("%s,,%s,,%s", month, day, year);
        }
        
        
        
    }

