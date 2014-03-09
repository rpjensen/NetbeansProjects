

package magicsquare;

import java.util.Scanner;


/**
 *This is a class that stores a square array and outputs whether it is a valid magic square.
 * @author Ryan Jensen, Chudier Pal
 * @version 01/27/14
 */
public class MagicSquare {

	private int size;
	private int[][] magicSquare;
/**
 * This constructor creates a magic square with dimensions equal to size
 * and initilizes the values to zero.
 * @param size the length and height of the created array.
 */
	public MagicSquare(int size){
            this.size = size;
            this.magicSquare = new int[size][size];
            for (int i = 0; i < magicSquare.length; i++){
                    for(int j = 0; j < magicSquare[i].length; j++){
                    this.magicSquare[i][j] = 0;
                    }//col
            }//row
	}
/**
 * This constructor initilizes a 3 by 3 array to zero and sets the size to three.
 */ 
	public MagicSquare(){
            this.size = 3;
            this.magicSquare = new int[size][size];
            for (int i = 0; i < magicSquare.length; i++){
                    for(int j = 0; j < magicSquare[i].length; j++){
                    this.magicSquare[i][j] = 0;
                    }//col
            }//row
	}
/**
 * This method returns the size of a magicSquare
 * @return size The size of a magic square
 */
	public int getSize(){
            return this.size;
	}
/**
 * This method sets the size of the MagicSquare and initilizes a magic square of that
 * size to 0.
 * @param size The size of the new array's dimensions. 
 */
        public void setSize(int size){
            this.size = size;
            this.magicSquare = new int[size][size];
            for (int i = 0; i < magicSquare.length; i++){
                for(int j = 0; j < magicSquare[i].length; j++){
                    this.magicSquare[i][j] = 0;
                }//col
            }
        }
/**
 * This method prompts the user to set the new size and initilizes a new array with 
 * that dimensions and prompts the user to set the new values.
 */
        public void setSizeFromUser(){
            Scanner keyboard = new Scanner(System.in);
            int size = 0;
            String throwAwayValue = "";
            do{
                System.out.println("Please enter the size of the nXn magic square");
                if (keyboard.hasNextInt()){
                    size = keyboard.nextInt();
                }else {
                    throwAwayValue = keyboard.next();
                }
                
            }while (size <= 0);
            this.setSize(size);
            this.setValuesFromUser();
        }
        
/**
 * This method prompts the user to input values into the MagicSquare row by row.
 */
	public void setValuesFromUser(){
            Scanner keyboard = new Scanner(System.in);
            for (int i = 0; i < magicSquare.length; i++){
                    System.out.println("Please enter " + magicSquare[i].length + " values for row " + (i + 1));
                    for (int j = 0; j < magicSquare[i].length; j++){
                        this.magicSquare[i][j] = keyboard.nextInt();
                    }
            }
	}
        
/**
 * This method takes a square array and duplicates its dimensions and contents
 * into a MagicSquare.
 * @param square The array you want to turn into a MagicSquare
 */
        public void setValues(int[][] square){
            if (square.length == square[0].length){
                this.setSize(square.length);
                for (int i = 0; i < square.length; i++){
                    for (int j = 0; j < square.length; j++){
                        this.magicSquare[i][j] = square[i][j];
                    }
                }
            }
        }
    
/**
 * This method creates a string with each row on its own line.
 * @return The magic square represented as a string
 */
    @Override // I wasn't sure what this did exactly but could see its use since all objects have some form of toString implimentation.
	public String toString(){
            String returnString = "";
            for (int i = 0; i < magicSquare.length ; i++){
                for (int j = 0; j < magicSquare[i].length; j++){
                    returnString += this.magicSquare[i][j] + " ";
                }
                returnString += "\n";
            }
            return returnString;
	}
/**
 * Looks through all the row,col,diagonal sums and finds the most prevalent one to use
 * as the accepted sum.
 * @param results An array of all the sums of rows, cols, and diagonals
 * @return The most prevalent sum
 */
        private int getAcceptedSum(int[] results){
            int uniqueSumsCount = 1;
            int[] uniqueSums = new int[results.length];//there can't be more than results.length unique sums.
            uniqueSums[0] = results[0];//init the first unique sum to the first row sum.
            for (int i = 1; i < results.length; i++){ //for all of the sums
                int numEqual = 0;
                for (int j = 0; j < uniqueSumsCount; j++){ //compare to each unique sum
                    if (uniqueSums[j] == results[i]){
                        numEqual++;
                    }
                }
                if (numEqual == 0){
                    uniqueSums[uniqueSumsCount] = results[i];
                    uniqueSumsCount++;
                }
            }
            int[] frequencyArray = new int[uniqueSums.length];
            for (int i = 0; i < frequencyArray.length; i++){
                frequencyArray[i]=0;
            }
            for (int i = 0; i < uniqueSumsCount; i++){
                for (int j = 0; j < results.length; j++){
                    if (uniqueSums[i] == results[j]){
                        frequencyArray[i]++;
                    }
                }
            }
            int acceptedSum = uniqueSums[0];
            int acceptedSumIndex = 0;
            for (int i = 1; i < uniqueSums.length; i++){
                if (frequencyArray[i] > frequencyArray[acceptedSumIndex]){
                    acceptedSum = uniqueSums[i];
                    acceptedSumIndex = i;
                }
            }
            return acceptedSum;
        }
        
/**
 * This method puts all the sums of rows,cols, and diagonals into an array
 * @return The array of sums
 */
        private int[] getResults(){
            int resultsSize = 2 * magicSquare.length + 2;// numRows + numCols + 2 diagonals
            int[] results = new int[resultsSize];
            for (int i = 0; i < magicSquare.length; i++){
                    results[i] = rowSum(i);
            }
            for (int i = 0; i < magicSquare.length; i++){
                    results[i + magicSquare.length] = colSum(i);
            }
            results[results.length-2] = topLeftDiagonalSum();
            results[results.length-1] = bottomLeftDiagonalSum();
            
            return results;
        }
        
/**
 * Reads which rows, cols, and diagonals didn't match the sum and returns
 * the proper display string.
 * @param didFail The array of boolean values indicating which rows don't match
 * @return The string indicating which rows,cols, diagonals failed to make it a magic square
 */
        private String getFailType(boolean[] didFail){
            String failType = "Valid";
            int rowFails = 0;
            int colFails = 0;
            int diagonalFails = 0;
            int totalFails = 0;
            for (int i = 0; i < magicSquare.length; i++){
                if (didFail[i]){
                    totalFails++;
                    if (totalFails == 1){
                        failType = "Not Valid";
                    }
                    rowFails++;
                    if (rowFails == 1){
                        failType += " (row " + (i + 1);
                    }else {
                        failType+= ", " + (i + 1);
                    }
                    
                }
            }
            if (rowFails > 0){
                    failType+= ") ";
                }
            for (int i = magicSquare.length; i < (2 * magicSquare.length); i++){
                if (didFail[i]){
                    totalFails++;
                    if (totalFails == 1){
                        failType = "Not Valid";
                    }
                    colFails++;
                    if (colFails == 1){
                        failType += " (col " + (i + 1 - magicSquare.length);
                    }else {
                        failType+= ", " + (i + 1 - magicSquare.length);
                    }
                    
                }
            }
            if (colFails > 0){
                    failType+= ") ";
                }
            if (didFail[didFail.length - 2]){
                totalFails++;
                if (totalFails == 1){
                    failType = "Not Valid";
                }
                diagonalFails++;
                failType += " Diagonal: \\";
            }
            if (didFail[didFail.length-1]){
                totalFails++;
                if (totalFails == 1){
                    failType = "Not Valid";
                }
                diagonalFails++;
                if (diagonalFails == 1){
                    failType += " Diagonal: /";
                }else {
                    failType += " and /";
                }
            }
            
            failType += "\n";
            return failType;
        }
        
/**
 * Creates a string that has the valid/not valid magic square test information
 * @return A string with valid/not valid magic square information
 */
	public String testMagicSquare(){
    	int[] results = getResults();
        int acceptedSum = this.getAcceptedSum(results);
        boolean[] didFail = new boolean[results.length];
    	for (int i = 0; i < results.length; i++){
            if (acceptedSum != results[i]){
                didFail[i] = true;
            }else{
                didFail[i] = false;
            }
    	}
        String returnString = getFailType(didFail);

    	return returnString;
	}
        
/**
 * Prints the valid/not valid test data and the toString representation of a magicSquare
 */
        public void printTestedMagicSquare(){
            System.out.println(testMagicSquare() + this.toString());
        }
    
/**
 * Finds the sum of one row in the magic square
 * @param i the row you want to find the sum of
 * @return the sum of the entries in row i
 */
	private int rowSum(int i){
    	int sum = 0;
    	for (int j = 0; j < this.magicSquare[i].length; j++){
        	sum += this.magicSquare[i][j];
    	}
    	return sum;
	}
    
        /**
 * Finds the sum of one column in the magic square
 * @param i the column you want to find the sum of
 * @return the sum of the entries in column i
 */
	private int colSum(int j){
    	int sum = 0;
    	for (int i = 0; i < this.magicSquare.length; i ++){
        	sum += this.magicSquare[i][j];
    	}
    	return sum;
	}
    
/**
 * Finds the sum of the topLeft -> bottomRight diagonal
 * @return the sum of one diagonal
 */
	private int topLeftDiagonalSum(){
    	int sum = 0;
    	for (int i = 0; i < magicSquare.length; i++){
        	sum += magicSquare[i][i];
    	}
    	return sum;
	}
    
/**
 * Finds the sum of the bottomLeft -> topRight diagonal
 * @return the sum of the other diagonal
 */
	private int bottomLeftDiagonalSum(){
    	int sum = 0;
    	int i = magicSquare.length - 1;
    	for (int j = 0; j < magicSquare.length; j++){
        	sum += magicSquare[i][j];
        	i--;
    	}
    	return sum;
	}
    
}


