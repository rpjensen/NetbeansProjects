

package encryption_utility;

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
    public static long gcd(long a, long b){
        if (b == 0){
            return a;
        }
        else {
            return gcd(b, a%b);
        }
    }
    
    /**
     * Uses extended Euclid's Algorithm to calculate d,x,y of the equation 
     * ax + by = d, where d = gcd(a,b) and x,y are integers
     * @param a the first value
     * @param b the second value
     * @return a Triple with the values (d, x, y) in fields (getA(), getB(), getC()) of the triple
     */
    public static Triple<Long, Long, Long> extendedEuclid(long a, long b){
        if (b == 0){
            return new Triple<>(a, (long)1, (long)0);
        }
        else {
            Triple<Long, Long, Long> trip = extendedEuclid(b, a % b);
            return new Triple<>(trip.getA(), trip.getC(), trip.getB() - a / b);
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
    public static long modularLinearEquationSolver(long a, long b, long n){
        if (a < 0){throw new IllegalArgumentException("a should be greater than zero: " + a);}
        if (a < 0){throw new IllegalArgumentException("n should be greater than zero: " + n);}
        //if b < 0 or b >= n make sure 0 <= b < n
        b = b % n;
        if (b < 0){
            b += n;
        }
        
        Triple<Long, Long, Long> result = extendedEuclid(a, n);
        long d = result.getA();//gcd
        long xi = result.getB();//d = a*xi + b*yi
        if (b % d == 0){
            return (xi * (b / d)) % n;
        }
        else {
            return -1;
        }
    }
    
    /**
     * Find a inverse mod n or x such that ax = 1 (mod n)
     * @param a the inverse to find
     * @param n the base of the mod
     * @return a inverse mode n or -1 of it doesn't have one
     */
    public static long getInverseMod(long a, long n){
        if (gcd(a, n) == 1){
            return modularLinearEquationSolver(a, 1, n);
        }
        else {
            return -1;
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
    public static long modularExponent(long a, long b, long n){
        if (a < 0 || b < 0 || n <= 0){throw new IllegalArgumentException("One of the following conditions were not met: a,b >= 0 and n > 0");}
        
        long d = 1;
        String binary = Long.toBinaryString(b);
        for (int i = 0; i < binary.length(); i++){
            //adding a new digit to the binary means doubling the binary we have seen so far
            //which means double the exponent which means square the base of the exponent
            d = (d * d) % n;//a^(2(oldNum)) = (a^(oldNum))^2 = d^2 since d = a^(oldNum)
            if (Character.getNumericValue(binary.charAt(i)) == 1){
                //if the added value is one then we have 2*oldNum + 1 in the exponent
                d = (d * a) % n;//a^(2*oldNum+1)= a*a^(2*oldNum)=a*d since we alread squared d for this iteration
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
    private static boolean testWitness(long a, long n){
        String binary = Long.toBinaryString(n-1);
        int index = binary.lastIndexOf("1");
        String odd = binary.substring(0, index+1);
        String even = binary.substring(index + 1);
        long t = even.length();
        long u = Long.parseLong(odd, 2);
        long x0 = modularExponent(a, u, n);
        long x1 = x0;
        for (int i = 1; i <= t; i++){
            x0 = x1;//the final result from last iteration becomes the initial for this iteration
            x1 = (x0 * x0) % n;
            if (x1 == 1 && x0 != 1 && x0 != (n - 1)){
                return true;
            }
        }
        if (x1 != 1){
            return true;
        }
        return false;
    }
    
    /**
     * Test whether n is prime with a statistical probability 2^(-s) that n is a false prime
     * There is not a false detection of composite nature (if false is returned n is not prime with 100% certainty)
     * Pre-Condition: n is an odd, positive integer greater than 2
     * @param n the value to check for primality
     * @param s the number of witnesses to check
     * @return true if n is (1- 2^(-s)) sure that n is prime, else false if n is definitely composite
     */
    public static boolean millerRabinPrimality(long n, long s){
        Random gen = new Random();
        for (int j = 0; j < s; j++){
            long a;
            if (n > Integer.MAX_VALUE){
                a = nextLong(gen, n - 1) + 1;//[0,n-1) + 1 = [1, n) = [1, n-1]
            }
            else {
                a = gen.nextInt((int)n - 1) + 1;//[0,n-1) + 1 = [1, n) = [1, n-1]
            }
            if (testWitness(a, n)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Helper method to get a random long within a range from 0 (inclusive) to n (exclusive)
     * @param rng random number generator
     * @param n the exclusive upper bound
     * @return a pseudorandom number from 0 to n-1
     */
    private static long nextLong(Random rng, long n) {
        if (n < 0){throw new IllegalArgumentException("n should be non-negative");}
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits-val+(n-1) < 0L);
        return val;
    }
}
