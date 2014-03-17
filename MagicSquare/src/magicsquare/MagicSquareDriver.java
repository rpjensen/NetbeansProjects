/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicsquare;

/**
 * A class that tests the capabilities of the MagicSquare class.
 * @author Ryan Jensen, Chudier Pal
 * @version 1/27/14
 */
public class MagicSquareDriver {
    public static void main(String[] args){
        System.out.println("User input test");
        MagicSquare mS1 = new MagicSquare();
        mS1.setSizeFromUser();
        int mSOneSize = mS1.getSize();
        mS1.printTestedMagicSquare();
        /*System.out.println("The size of the first matrix is " + mSOneSize);
        
        System.out.println("2x2 matrix internal initilization test");
        MagicSquare mS2 = new MagicSquare(5);
        mS2.setSize(2);
        int[][] squareArray = {{2,2},{1,1}};
        mS2.setValues(squareArray);
        mS2.printTestedMagicSquare();
        
        System.out.println("4x4 matrix test plus toString() method test");
        MagicSquare mS3 = new MagicSquare(4);
        mS3.setValuesFromUser();
        String mSThreeString = mS3.toString();
        System.out.println(mS3);
        System.out.println(mSThreeString);
        System.out.println(mS3.testMagicSquare());
        
        */
        
    }
    
}
