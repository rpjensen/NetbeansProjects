
package studentgradegenerator;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 *Generate a file that will contain grades for n students. n is to be between 2000 and 4000. Each student will have between 0 and 40 grades. The file is to be formatted as follows:

The first line will contain and integer n, the number of students.

The next n lines will contain g, the number of grades for a student, followed by the student's letter grades delimited by spaces. Each letter grade is a random value from (A, A-,B+,B,B-,C+,C,C-,D+,D,F).

The program must ask the user for a file name and will store results in the file.

Sample file:

2
4 A B+ C B+
6 A B+ D+ A A B
* 
* Loop algorithm:
* Need loop to run 1 to n times
* inside need loop to run 0 to g times
* inside loop runs randomlettergrade method and prints 0 to g grades
* 
 * @author Ryan
 */
public class StudentGradeGenerator {
    public static String randomLetterGrade(int r){
        
        switch(r){
            case 1: return "A";
            case 2: return "A-";
            case 3: return "B+";
            case 4: return "B";
            case 5: return "B-";
            case 6: return "C+";
            case 7: return "C";
            case 8: return "C-";
            case 9: return "D+";
            case 10: return "D";
            case 11: return "F";
            default: return "Error";
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Scanner keyboard = new Scanner (System.in);
        System.out.print("Please enter the desired file name: ");
        String fileName = keyboard.next();
        PrintStream outFile = new PrintStream(fileName);
        
        Random gen = new Random();
        int n = gen.nextInt(2001) + 2000;
        outFile.println(n);
        
        
        for (int i = 1; i <= n; i++){
            int g = gen.nextInt(41);
            outFile.print(g + " ");
            for (int ii = 1; ii <= g; ii++){
                int exx = gen.nextInt(11) + 1;
                String letterGrade = randomLetterGrade(exx);
                outFile.print(letterGrade + " ");
            }//each student gets g grades
            outFile.printf("\n");
            
        }//for n students
        
        
        outFile.close();
        
    }//public
}//class
