package inclassproject;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author KH037s
 */
public class InClassProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please Enter the File Name: ");
        String filename = keyboard.next();
        Scanner inFile = new Scanner(new File(filename));
        
        double highAverage = Double.MIN_VALUE;
        String highString = " ";
        while (inFile.hasNextLine()){
            Student student1 = new Student(inFile.nextInt(), inFile.next(), inFile.next(), inFile.nextInt(), inFile.nextInt(), inFile.nextInt());
        double average = student1.examAverage();
        String studentString = student1.toString();
        System.out.printf("%s\n", studentString);
        if (average > highAverage){
            highAverage = average;
            highString = student1.toString();
        }//if
        
        }//for each student
        System.out.printf("The highest average was Student ID#%s with an average exam score of %2.2f\n",
                highString, highAverage);
    }
}//main class
