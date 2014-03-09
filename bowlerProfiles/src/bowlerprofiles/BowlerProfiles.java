package bowlerprofiles;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author KH037s
 */
public class BowlerProfiles {

    /**
     * // max = -infinity min = infinity
        // maxID = -1 minID = -1
        // Ask for file name
        //  Bowlfile
        // Get number of bowlers
        //  bowlerCount
        // For bowler ID = 1 to number of bowlers
        //  sum = 0
        //  Get number of games
        //      gameCount
        //  For each game get score
        //      sum = sum + score
        //  Average = sum/gameCount
        //  if average > max
        //      max = average maxID = bowlerID
        //  if average < max
        //      min = average minID = bowlerID
        // Print results

     */
    public static void main(String[] args) throws Exception {
       Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter file name: ");
        String filename = keyboard.next();
        Scanner bowlFile = new Scanner(new File(filename));
        
        //read in number of bowlers
        int bowlerCount = bowlFile.nextInt();
        
        if (bowlerCount < 1){
                System.out.printf("%d is not a valid bowler count. \n", bowlerCount);
                System.exit(0);
            }//if invalid bowlercount
        
        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        int maxID = 0, minID = 0;
        double averageSum = 0;
        int noGameBowlers = 0;
        for (int bowlerID = 1; bowlerID <= bowlerCount; bowlerID++){
           
            double sum = 0;
            int gameCount = bowlFile.nextInt();
            
            for (int game = 1; game <= gameCount; game++){
                int score = bowlFile.nextInt();
                sum = sum +score;
            }//for each game
            if (gameCount<1){
                System.out.printf("Bowler #%d: No games\n", bowlerID);
                noGameBowlers++;
            }//if no games
            else {
                double average = sum / gameCount;
            averageSum = averageSum + average; 
            System.out.printf("Bowler #%d: %1.2f\n", bowlerID, average);
            if (average > max){
                max = average;
                maxID = bowlerID;
            }//if higher than max
            if (average < min){
                min = average;
                minID = bowlerID;
            }//if lower than max
        }//else gameCount is > 0
        }//for each bowler
        if (bowlerCount-noGameBowlers == 0){
            System.out.println("All bowlers are gameless!!");
        }
        else{
        double overallAverage = averageSum / (bowlerCount-noGameBowlers);
        
        System.out.printf("High Bowler: %d Average: %1.2f\n", maxID, max);
        System.out.printf("Low Bowler: %d Average: %1.2f\n", minID, min);
        System.out.printf("Overall Average: %1.2f\n", overallAverage);
        }
    }
}