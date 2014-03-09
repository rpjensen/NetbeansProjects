
package passwordstrength;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Ryan Jensen
 * Algorithm
 * need file read in
 * need while loop (infile.hasnextline())
 * need variable totalcount =  number of passwords
 * need test for uppercase lowercase number and length
 * need variables count for Strong, medium, weak passwords
 * need average length of each string
 * ('a'=97 'z'=122 'A'=65 'Z'=90 0 = 48, 9 = 57
 * 
 */
public class PasswordStrength {
        public static int passwordType(String password) {
            boolean capLetter = false;
            boolean lowerLetter = false;
            boolean number = false;
            boolean length = false;
            
            for (int i=0; i< password.length(); i++){
                char currentChar = password.charAt(i);    
                if ((currentChar <= 90) && (currentChar >= 65))
                    capLetter = true;
                if ((currentChar <= 122) && (currentChar >= 97))
                    lowerLetter = true;
                if ((currentChar <= 57) && (currentChar >= 48))
                    number = true;
                
                }//for each letter of password
                if (password.length() >= 6)
                    length = true;
                //======================return type=======================================
                if ((capLetter)&&(lowerLetter)&&(number)&&(length))
                    return 1;
                if ((capLetter)&&(lowerLetter)&&(length))
                    return 2;
                else
                    return 3;
            }//passwordtype
        
    
    public static void main(String[] args) throws Exception{
        Scanner keyboard = new Scanner (System.in);
        System.out.println("Please enter the file name");
        String fileName = keyboard.next();
        Scanner inFile = new Scanner (new File(fileName));
        //variables
        int[][] passwordStats = new int[3][2];
        for (int x=0; x < 3; x++){
            for (int y = 0; y<2; y++){
            passwordStats[x][y] = 0;
            }
        }//initilizing array
        //|weakpasswords| |weakpasswordcharacters|
        //|medpasswords | |medpasswordcharacter|
        //|strongpasswords| |strongpasswordchars|
        int totalPasswords = 0;
        double averageStrongLength = 0;
        double averageMediumLength = 0;
        double averageWeakLength = 0;
        //variables
        while (inFile.hasNext()){
            String password = inFile.next();
            totalPasswords++;
            int type = passwordType(password);
            if (type == 1){
                passwordStats[2][0]++;
                passwordStats[2][1] += password.length();
            }//type 1
            if (type == 2){
                passwordStats[1][0]++;
                passwordStats[1][1] += password.length();
            }//type 2
            if (type == 3){
                passwordStats[0][0]++;
                passwordStats[0][1] += password.length();
            }//type 3
        }//while infile has next line
        averageStrongLength = passwordStats[2][1] / (double)passwordStats[2][0];
        averageMediumLength = passwordStats[1][1] / (double)passwordStats[1][0];
        averageWeakLength = passwordStats[0][1] / (double)passwordStats[0][0];
        
        //printout
        System.out.printf("Total Number of Passwords read: %d\n", totalPasswords);
        System.out.printf("Number of Strong Passwords: %d\n", passwordStats[2][0]);
        System.out.printf("Number of Medium Passwords: %d\n", passwordStats[1][0]);
        System.out.printf("Number of Weak Passwords: %d\n", passwordStats[0][0]);
        System.out.println();
        System.out.printf("Average Size of Strong Passwords: %1.2f\n", averageStrongLength);
        System.out.printf("Average Size of Medium Passwords: %1.2f\n", averageMediumLength);
        System.out.printf("Average Size of Weak Passwords: %1.2f\n", averageWeakLength);
        
       
    }//main
}//class
