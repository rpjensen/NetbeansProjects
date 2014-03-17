/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printingpowers;

/**
 *
 * @author jensenrp
 */
public class PrintingPowers {

    static void printPowers(int i){
        int p = 1;
        while (p*i > 0){
            System.out.printf("%20d %35s\n", p, Integer.toBinaryString(p));
            p *= i;
        }
    }
    public static void main(String[] args) {
       printPowers(2);
       printPowers(6);
       printPowers(10);
    }
}
