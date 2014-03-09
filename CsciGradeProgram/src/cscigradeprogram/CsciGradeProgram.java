/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cscigradeprogram;

import java.util.Scanner;


public class CsciGradeProgram {
   static int exerciseWeight = 5;
   static int feedbackWeight = 5;
   static int labsWeight = 10;
   static int programsWeight = 20;
   static int examsWeight = 30;
   static int finalWeight = 30;
   //Created so programer can easily change the weights of different components separately 
   
   public static boolean validGrade(double actual, double possible){
       if ((0 <= actual) && (0 < possible) && (actual <= possible)){
           return true;
      }//if
       else {
           return false;
       }//else
   }//validGrade determines if the input are logically valid for computation
   
   public static double programPenalty(double score){
       if (14.5 < score) {
           return 0;
       }
       else {
          return 24*(14.5 - score)/14.5 ;
       }
   }//programpenalty component
    public static double finalPenalty(double score){
       if (21.75 < score) {
           return 0;
           
       }
       else {
          return 24*(21.75 - score)/21.75 ;
       }
   }//finalpenalty component
   
    public static String convertToLettergrade(double scoreTotal){
       if (92.50 <= scoreTotal){
           return "A";
       } else if (89.50 <= scoreTotal){
           return "A-";
       } else if (86.50 <= scoreTotal){
           return "B+";
       } else if (82.50 <= scoreTotal){
           return "B";
       } else if (79.50 <= scoreTotal){
           return "B-";
       } else if (76.50 <= scoreTotal){
           return "C+";
       } else if (72.50 <= scoreTotal){
           return "C";
       } else if (69.50 <= scoreTotal){
           return "C-";
       } else if (66.50 <= scoreTotal){
           return "D+";
       } else if (66.50 <= scoreTotal){
           return "D";
       }else {
           return "F";
       }
   }//creates lettergrade string to display after total score
    
    public static void main(String[] args) {
       Scanner keyboard = new Scanner (System.in); 
       System.out.println("CS181 Grade Calculator\n");
       //Asking for all the user inputs
       System.out.print("Please enter possible and actual points for exercises: ");
       double possibleExercises = keyboard.nextDouble();
       double actualExercises = keyboard.nextDouble();
       while (!validGrade(actualExercises, possibleExercises)){
           System.out.print("An error has occured please re-enter possible and actual points for exercises: ");
           possibleExercises = keyboard.nextDouble();
           actualExercises = keyboard.nextDouble();
       }//while loop keeps asking for the variables until two valid inputs are entered 
       double scoreExercises = (actualExercises / possibleExercises) * exerciseWeight;
       
       System.out.print("Please enter possible and actual points for feedback: ");
       double possibleFeedback = keyboard.nextDouble();
       double actualFeedback = keyboard.nextDouble();
       while (!validGrade(actualFeedback, possibleFeedback)){
           System.out.print("An error has occured please re-enter possible and actual points for feedback: ");
           possibleFeedback = keyboard.nextDouble();
           actualFeedback = keyboard.nextDouble();
       } //while loop keeps asking for the variables until two valid inputs are entered  
       double scoreFeedback = (actualFeedback / possibleFeedback)* feedbackWeight;
       
       System.out.print("Please enter possible and actual points for labs: ");
       double possibleLabs = keyboard.nextDouble();
       double actualLabs = keyboard.nextDouble();
       while (!validGrade(actualLabs, possibleLabs)){
           System.out.print("An error has occured please re-enter possible and actual points for labs: ");
           possibleLabs = keyboard.nextDouble();
           actualLabs = keyboard.nextDouble();
       }//while loop keeps asking for the variables until two valid inputs are entered   
       double scoreLabs = (actualLabs / possibleLabs)* labsWeight;
       
       System.out.print("Please enter possible and actual points for programs: ");
       double possiblePrograms = keyboard.nextDouble();
       double actualPrograms = keyboard.nextDouble();
       while (!validGrade(actualPrograms, possiblePrograms)){
           System.out.print("An error has occured please re-enter possible and actual points for programs: ");
           possiblePrograms = keyboard.nextDouble();
           actualPrograms = keyboard.nextDouble();
       }//while loop keeps asking for the variables until two valid inputs are entered   
       double scorePrograms = (actualPrograms / possiblePrograms)* programsWeight;
       
       System.out.print("Please enter possible and actual points for exams: ");
       double possibleExams = keyboard.nextDouble();
       double actualExams = keyboard.nextDouble();
       while (!validGrade(actualExams, possibleExams)){
           System.out.print("An error has occured please re-enter possible and actual points for exams: ");
           possibleExams = keyboard.nextDouble();
           actualExams = keyboard.nextDouble();
       }//while loop keeps asking for the variables until two valid inputs are entered   
       double scoreExams = (actualExams / possibleExams)* examsWeight;
       
       System.out.print("Please enter possible and actual points for final: ");
       double possibleFinal = keyboard.nextDouble();
       double actualFinal = keyboard.nextDouble();
       while (!validGrade(actualFinal, possibleFinal)){
           System.out.print("An error has occured please re-enter possible and actual points for final: ");
           possibleFinal = keyboard.nextDouble();
           actualFinal = keyboard.nextDouble();
       }//while loop keeps asking for the variables until two valid inputs are entered   
       double scoreFinal = (actualFinal / possibleFinal)* finalWeight;
       //end of user input
       
       double penaltyProgram = programPenalty(scorePrograms);
       double penaltyFinal = finalPenalty(scoreFinal);
       //runs Penalty methods above and returns penalty components of grade
       
       double scoreTotal = (scoreExercises + scoreFeedback + scoreLabs + scorePrograms + scoreExams + scoreFinal - penaltyProgram - penaltyFinal);
       //creates final score
      
       String letterGrade = convertToLettergrade(scoreTotal);
       //creates letter grade string
       
       
       //table 
       System.out.print("\nComponent       | Weight | Possible | Actual | Score\n");
       System.out.print("===========================================================\n");
       System.out.printf("Exercises       |    %2d%%  |     %4.0f |   %4.0f |%6.2f\n", exerciseWeight, possibleExercises, actualExercises, scoreExercises );
       System.out.printf("Feedback        |    %2d%%  |     %4.0f |   %4.0f |%6.2f\n", feedbackWeight, possibleFeedback, actualFeedback, scoreFeedback );
       System.out.printf("Labs            |    %2d%%  |     %4.0f |   %4.0f |%6.2f\n", labsWeight, possibleLabs, actualLabs, scoreLabs );
       System.out.printf("Programs        |    %2d%%  |     %4.0f |   %4.0f |%6.2f\n", programsWeight, possiblePrograms, actualPrograms, scorePrograms );
       System.out.printf("Exams           |    %2d%%  |     %4.0f |   %4.0f |%6.2f\n", examsWeight, possibleExams, actualExams, scoreExams );
       System.out.printf("Final           |    %2d%%  |     %4.0f |   %4.0f |%6.2f\n", finalWeight, possibleFinal, actualFinal, scoreFinal );
       System.out.printf("Program Penalty |----------------------------|%6.2f\n", penaltyProgram);
       System.out.printf("Final Penalty   |----------------------------|%6.2f\n", penaltyFinal);
       System.out.print("===========================================================\n");
       System.out.printf("Course Average  |----------------------------|%6.2f %s\n", scoreTotal, letterGrade);
      
       //table
       //System.out.printf("%d %d", possible, actual);
    }//main
}//public
