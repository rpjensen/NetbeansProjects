/*
 * This program generates a bowler score file.  
 * The program generates a random number of bowlers between 10 and 200
 * Number of games must be a multiple of 3.  Each bowler must have a min of 21 games
 * Max number of weeks in bowling league is 33.
 * Bowler Score is between 60 and 300 inclusively.
 */
package bowlinggames;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Archeress
 */
public class BowlingGames {
    static final int MAX_WEEKS = 33; //number of weeks in league
    static final int MIN_GAMES = 21; //min number of games bowler must have to 
                                     //compete in tournament
    static final int MIN_BOWLERCOUNT = 10;     //min number of bowlers
    static final int MAX_BOWLERCOUNT = 200;
    static final int MAX_BOWLER_SCORE = 300;
    static final int MIN_BOWLER_SCORE = 60;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter name for bowling file: ");
        String fileName = keyboard.next();
        PrintStream bowlerFile = new PrintStream(fileName);
        Random gen = new Random();
        int bowlerCount = gen.nextInt(MAX_BOWLERCOUNT-MIN_BOWLERCOUNT+1) + MIN_BOWLERCOUNT;
        .println(bowlerCount);
        for (int bowler = 1; bowler <= bowlerCount; bowler++){
            int gameCount = gen.nextInt(MAX_WEEKS) * 3 + MIN_GAMES;
            bowlerFile.printf("%3d ", gameCount);
            for (int game=1; game <= gameCount; game++){
                int score = gen.nextInt(MAX_BOWLERCOUNT-MIN_BOWLERCOUNT+1) + MIN_BOWLERCOUNT;
                bowlerFile.printf("%3d ", score);
            }//for each game
            bowlerFile.println();                
        }//for each bowler
        bowlerFile.O();                
        System.out.printf("Created file %s with %d bowlers.\n", fileName, bowlerCount);
    }//end of main
}//end of class