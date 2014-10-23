

package encryption_utility;

import java.math.BigInteger;
import java.util.Random;

/**
 * Math Utilities for asymmetric and symmetric encryption classes
 * @author Ryan Jensen
 * @version Sep 27, 2014
 */
public class MathUtilities {

    /**
     * Uses Euclid's Algorithm to calculate the GCD of a and b
     * @param a the first number
     * @param b the second number
     * @return the greatest common divisor
     */
    public static BigInteger gcd(BigInteger a, BigInteger b){
        if (b.equals(BigInteger.ZERO)){
            return a;
        }
        else {
            return gcd(b, a.mod(b));
        }
    }
    
    /**
     * Uses extended Euclid's Algorithm to calculate d,x,y of the equation 
     * ax + by = d, where d = gcd(a,b) and x,y are integers
     * @param a the first value
     * @param b the second value
     * @return a Triple with the values (d, x, y) in fields (getA(), getB(), getC()) of the triple
     */
    public static Triple<BigInteger, BigInteger, BigInteger> extendedEuclid(BigInteger a, BigInteger b){
        if (b.equals(BigInteger.ZERO)){
            return new Triple<>(a, BigInteger.ONE, BigInteger.ZERO);
        }
        else {
            Triple<BigInteger, BigInteger, BigInteger> result = extendedEuclid(b, a.mod(b));
            BigInteger di = result.getA();
            BigInteger xi = result.getB();
            BigInteger yi = result.getC();
            return new Triple<>(di, yi, xi.subtract(a.divide(b).multiply(yi)));
        }
    }
    
    /**
     * Solves the equation ax = b (mod n) where = means congruent in context
     * Pre-Condition: a is greater than zero
     * Pre-Condition: n is greater than zero
     * @param a the value x is being applied to
     * @param b the value ax is congruent to
     * @param n the base of the mod
     * @return the value of x that solves ax = b(mod n) or -1 if x DNE (no solutions)
     */
    public static BigInteger modularLinearEquationSolver(BigInteger a, BigInteger b, BigInteger n){
        if (a.compareTo(BigInteger.ZERO) < 0){throw new IllegalArgumentException("a should be greater than zero");}
        if (n.compareTo(BigInteger.TEN) <= 0){throw new IllegalArgumentException("n should be greater than zero");}
        //if b < 0 or b >= n make sure 0 <= b < n
        b = b.mod(n);
        if (b.compareTo(BigInteger.ZERO) < 0){
            b = b.add(n);
        }
        
        Triple<BigInteger, BigInteger, BigInteger> result = extendedEuclid(a, n);
        BigInteger d = result.getA();//gcd
        BigInteger xi = result.getB();//d = a*xi + b*yi
        if (b.mod(d).equals(BigInteger.ZERO)){
            return xi.multiply(b.divide(d)).mod(n);
        }
        else {
            return new BigInteger("-1");
        }
    }
    
    /**
     * Find a inverse mod n or x such that ax = 1 (mod n)
     * @param a the inverse to find
     * @param n the base of the mod
     * @return a inverse mode n or -1 of it doesn't have one
     */
    public static BigInteger getInverseMod(BigInteger a, BigInteger n){
        if (gcd(a, n).equals(BigInteger.ONE)){
            return modularLinearEquationSolver(a, BigInteger.ONE, n);
        }
        else {
            return new BigInteger("-1");
        }
    }
    
    /**
     * Find x such that a^b = x (mod n) by way of successive squaring
     * Pre-Condition: a,b are non-negative integers
     * Pre-Condition: n is a positive integer
     * @param a the base of the exponent
     * @param b the power of the exponent
     * @param n the base of the mod
     * @return the congruent value to a^b(mod n)
     */
    public static BigInteger modularExponent(BigInteger a, BigInteger b, BigInteger n){
        if (a.compareTo(BigInteger.ZERO) < 0 || b.compareTo(BigInteger.ZERO) < 0 || n.compareTo(BigInteger.ZERO) <= 0){throw new IllegalArgumentException("One of the following conditions were not met: a,b >= 0 and n > 0");}
        
        BigInteger d = BigInteger.ONE;
        String binary = b.toString(2);
        for (int i = 0; i < binary.length(); i++){
            //adding a new digit to the binary means doubling the binary we have seen so far
            //which means double the exponent which means square the base of the exponent
            d = d.multiply(d).mod(n);//a^(2(oldNum)) = (a^(oldNum))^2 = d^2 since d = a^(oldNum)
            if (Character.getNumericValue(binary.charAt(i)) == 1){
                //if the added value is one then we have 2*oldNum + 1 in the exponent
                d = d.multiply(a).mod(n);//a^(2*oldNum+1)= a*a^(2*oldNum)=a*d since we alread squared d for this iteration
            }
        }
        return d;
    }
    
    /**
     * Test whether a is a witness to n's composite nature
     * Pre-condition: a is a non-negative integer
     * Pre-condition: n is a positive integer
     * @param a the witness
     * @param n the value who is being tested for composite nature
     * @return true if a is a witness to n (composite), else false (inconclusive)
     */
    private static boolean testWitness(BigInteger a, BigInteger n){
        BigInteger negOne = n.subtract(BigInteger.ONE);
        int powOfTwo = negOne.getLowestSetBit();
        String odd = negOne.toString(2);
        odd = odd.substring(0, odd.length()-powOfTwo);
        
        BigInteger u = new BigInteger(odd, 2);
        BigInteger x0 = modularExponent(a, u, n);
        BigInteger x1 = x0;
        for (int i = 1; i <= powOfTwo; i++){
            x0 = x1;//the final result from last iteration becomes the initial for this iteration
            x1 = x0.multiply(x0).mod(n);
            if (x1.equals(BigInteger.ONE) && !x0.equals(BigInteger.ONE) && !x0.equals(negOne)){
                return true;
            }
        }
        if (!x1.equals(BigInteger.ONE)){
            return true;
        }
        return false;
    }
    
    /**
     * Test whether n is prime with a statistical probability 2^(-s) that n is a false prime
     * There is no chance of false detection for composite nature (if false is returned n is not prime with 100% certainty)
     * Pre-Condition: n is an odd, positive integer greater than 2
     * @param n the value to check for primality
     * @param s the number of witnesses to check
     * @return true if n is (1- 2^(-s)) sure that n is prime, else false if n is definitely composite
     */
    public static boolean millerRabinPrimality(BigInteger n, long s){
        Random gen = new Random();
        int bits = n.bitLength() - 1;
        for (long j = 0; j < s; j++){
            BigInteger a = new BigInteger(bits, gen);
            if (testWitness(a, n)){
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args){
        BigInteger b1 = new BigInteger("7");
        BigInteger b2 = new BigInteger("327");
        BigInteger b3 = new BigInteger("853");
        System.out.println(modularExponent(b1, b2, b3));
        
    }
}
