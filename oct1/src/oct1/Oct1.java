package oct1;

import java.util.Scanner;

/**
 *
 * Ryan Jensen, Zoe Hamel, Isabelle Noel
 */
public class Oct1 {
    public static String testInput(String input) {
        int n = input.indexOf("problem");
        if (n == -1) {
           return "no"; 
        }
        else {
        return "yes";    
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner (System.in);
        
       
        String input = keyboard.nextLine();
        String lowerInput;
        String output;
        
        while (!input.equals("QUIT")){          
           lowerInput = input.toLowerCase();
           output = testInput(lowerInput);
           System.out.println(output);
           input = keyboard.nextLine();
        }
       
    }
}
