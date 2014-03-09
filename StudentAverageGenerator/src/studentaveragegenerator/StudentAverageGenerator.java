
package studentaveragegenerator;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Ryan Jensen
 */
public class StudentAverageGenerator {
    public static double convert(String letterGrade) {
            switch (letterGrade){
                case "A": return 4.0;
                case "A-": return 3.67;
                case "B+": return 3.33;
                case "B": return 3.0;
                case "B-": return 2.67;
                case "C+": return 2.33;
                case "C": return 2.0;
                case "C-": return 1.67;
                case "D+": return 1.33;
                case "D": return 1.0;
                case "F": return 0.00;
                default : return 0;
            }//letterGrade values
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the filename: ");
        String fileName = keyboard.next();
        
        
        Scanner inFile = new Scanner(new File(fileName));
        
        int n = inFile.nextInt();
        int id = 0;
        for (int i = 1; i <= n; i++){
            id++; 
            int g = inFile.nextInt();
            double individualGradeTotal = 0;
            for (int ii = 1; ii <= g; ii++){
                String letterGrade = inFile.next();
                double numberGrade = convert(letterGrade);
                individualGradeTotal += numberGrade;
                
            }//for g grades
            
            double individualAverage;
            if (g == 0){
               individualAverage= 0;
            
            }
            else {   
            individualAverage = individualGradeTotal / (double)g;
            
            //System.out.printf("Student Average: %f\n", individualAverage);
            }
            if (individualAverage >= 3.00){
                System.out.printf("Id: %d Student Average: %2.2f\n", id, individualAverage);
            }
           
        }//for n students
        
        
        
        
        
        
    }//main
        }//class

