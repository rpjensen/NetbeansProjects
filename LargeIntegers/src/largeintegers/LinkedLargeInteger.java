

package largeintegers;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * An arbitrary length integer that can do basic computations and arithmetic whose size is
 * constrained either by your memory or by the upper limit of 2^63 - 1 decimal places
 * or within the range {-10^(2^63-1),+10^(2^63-1)}
 * @author Ryan Jensen
 * @version Apr 28, 2014
 */
public class LinkedLargeInteger implements LargeInteger{
    private static final LinkedLargeInteger MAX_LONG;
    private static final LinkedLargeInteger MIN_LONG;
    
    /**
     * Store the max/min long as a Linked large integer so that I can use the native
     * compareTo method to test when it is safe to convert to a long from my class.
     */
    static {
        MAX_LONG = new LinkedLargeInteger(Long.MAX_VALUE);
        MIN_LONG = new LinkedLargeInteger(Long.MIN_VALUE);
    }
    
    protected Node sign;
    protected Node mostSignificantDigit;
    protected long biggestDecimalPlace;
    protected IntegerIterator iter;
    
    /**
     * Create a new Large Integer from a string consisting of numbers and select
     * special characters.
     * Pre-Condition: the string is either numbers 0-9 led by +/- or nothing. Also
     * commas are acceptable and will be parsed out of the string.
     * @param number the string representation of the number to copy
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public LinkedLargeInteger(String number){
        Scanner stringParser = new Scanner(number);
        stringParser.useDelimiter("");
        int iterator = -2;
        while (stringParser.hasNext()){
            String currentChar = stringParser.next();
            if (iterator == -2){
                if (!(Character.isDigit(currentChar.charAt(0)))){
                    if (currentChar.equals("-")){
                        this.sign = new Node(-1);
                        iterator++;
                    }
                    else if (currentChar.equals("+")) {
                        this.sign = new Node(1);
                        iterator++;
                    }
                    else {throw new IllegalArgumentException("Illegal Character: " + currentChar);}
                }
                else {
                    this.sign = new Node(1,null, new Node(Integer.parseInt(currentChar), this.sign));
                    iterator = 0;
                    this.mostSignificantDigit = this.sign.next;
                }
            }
            else {
                if (!(Character.isDigit(currentChar.charAt(0)))){
                    if (!currentChar.equals(",")){
                        throw new IllegalArgumentException("Error parsing character: " + currentChar);
                    }
                }
                else {
                    if (iterator == -1){
                        this.sign.next = new Node(Integer.parseInt(currentChar), this.sign);
                        this.mostSignificantDigit = this.sign.next;
                    }
                    else {
                        Node tempNode = this.sign.next;
                        this.sign.next = new Node(Integer.parseInt(currentChar), this.sign, tempNode);
                        tempNode.previous = this.sign.next;
                    }
                    iterator++;
                }
            }
        }
        if (iterator >= 0){
            this.biggestDecimalPlace = iterator ;
        }
        else {throw new IllegalArgumentException("Blank String exception");
        }
        this.iter = new IntegerIterator();
        this.iter.gotoMostSignificantDigit();
        while(iter.current() == 0 && iter.hasPrevious()){
            iter.previous();
            if (iter.current() == 0 && iter.currentDecimalIndex == 0){
                this.sign = new Node(0,null, new Node(0, this.sign));
                this.mostSignificantDigit = this.sign.next;
                this.biggestDecimalPlace = 0;
                this.iter.gotoLeastSignificantDigit();
            }
        }
        if (iter.currentDecimalIndex != this.biggestDecimalPlace){
            this.biggestDecimalPlace = iter.currentDecimalIndex;
            this.mostSignificantDigit = iter.currentDecimalPlace;
        }
        
    }
    
    /**
     * Create a new Large Integer from an existing integer.
     * @param number the number to copy
     */
    public LinkedLargeInteger(int number){
        this((long)number);
    }
    
    /**
     * Create a new Large Integer from an existing long.
     * @param number the number to copy
     */
    public LinkedLargeInteger(long number){
        this.biggestDecimalPlace = -1;
        if (number == 0){ this.sign = new Node(0, null, null); }
        else if (number > 0){sign = new Node(1, null, null);}
        else {
            sign = new Node(-1, null, null);
            number = -1 * number;
        }
        this.mostSignificantDigit = this.sign;
        
        do{
            this.mostSignificantDigit.next = new Node((int)Math.abs(number % 10), this.mostSignificantDigit);
            //new value is the ones digit of the current number
            number = number / 10;//throw away the ones digit for next iteration
            this.mostSignificantDigit = this.mostSignificantDigit.next;
            biggestDecimalPlace++;    
        }while (number != 0);
        this.iter = new IntegerIterator();
    }
    
    /**
     * Create a new LinkedLargeInteger from an existing Large Integer..
     * @param otherNumber the large integer to copy
     */
    private LinkedLargeInteger(LinkedLargeInteger otherNumber){
        otherNumber.iter.gotoLeastSignificantDigit();
        //Initialize the first two
        this.sign = new Node(otherNumber.sign.data, null, new Node(otherNumber.iter.current(), this.sign));
        this.mostSignificantDigit = this.sign.next;
        this.biggestDecimalPlace = 0;
        this.iter = new IntegerIterator();
        while (otherNumber.iter.hasNext()){
            this.iter.currentDecimalPlace.next = new Node(otherNumber.iter.next(), this.iter.currentDecimalPlace);
            this.iter.next();
            this.mostSignificantDigit = this.iter.currentDecimalPlace;
            this.biggestDecimalPlace++; 
        }
    }
    
    /**
     * Do nothing constructor for the subclass to use.
     */
    private LinkedLargeInteger(){}
    
    //<<<<<<<<<<<<<<<<<<<<< Public Functional Methods >>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    /**
     * Create and return a new large integer that is the negative of the caller.
     * @return the negative value of the caller
     */
    @Override
    public LargeInteger negate(){
        LargeIntegerBuilder builder = this.mutableCopy();
        builder.setSign(-1 * builder.getSign());
        return builder.copy();
    }
    
    /**
     * Create and return a new large integer that is the sum of the caller and the
     * other number passed into the method.
     * @param largeInt the number to be added to the caller
     * @return a new large integer that is the sum of the two
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger add(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (this.isZero() || otherNum.isZero()){return this.isZero() ? otherNum.copy() : this.copy();}
        if (this.getSign() != otherNum.getSign()){return this.subtract(otherNum.negate());}
        else {
            LargeIntegerBuilder sum = this.addMagnitudes(otherNum);
            if (this.getSign() > 0){sum.setSign(1);}
            else {sum.setSign(-1);}
            return sum.copy();
        }
    }
    
    /**
     * Convenience method for adding a long to a Large Integer.  Immediately converts
     * to Large Integer so see add(LargeInteger largeInt) for more info.
     * @param otherNum the long to add
     * @return the sum of the two numbers as a Large Integer
     */
    @Override
    public LargeInteger add(long otherNum){
        return this.add(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Create and return a new large integer that is the difference of the caller
     * and the other number passed into the method.
     * @param largeInt the number to be subtracted from the caller
     * @return a new large integer that is the difference of the two
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger subtract(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (this.isZero() || otherNum.isZero()){return this.isZero() ? otherNum.copy() : this.copy();}
        if (this.getSign() != otherNum.getSign()){return this.add(otherNum.negate());}
        else {
            LargeIntegerBuilder dif = this.subMagnitudes(otherNum);
            if (dif.isZero()){
                return new LinkedLargeInteger(0);
            }
            if (this.getSign() > 0){ 
                if (this.largerMagnitude(otherNum) > 0){dif.setSign(1);}
                else {dif.setSign(-1);} 
            }
            else {
                if (this.largerMagnitude(otherNum) > 0){dif.setSign(-1);}
                else {dif.setSign(1);
                }
            }
            return dif.copy();
        }
    }
    
    /**
     * Convenience method to take the difference of a Large Integer and a long.
     * Immediately converts long to LinkedLargeInteger so see subtract(LargeInteger largeInt)
     * for more information.
     * @param otherNum the number to subtract from the caller
     * @return the difference of the caller and otherNum
     */
    @Override
    public LargeInteger subtract(long otherNum){
        return this.subtract(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Creates a new Large Integer which is the product of the caller and the other
     * number passed into the method.
     * @param largeInt the number to be multiplied by
     * @return the product of the caller and other number
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger multiply(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (this.isZero() || otherNum.isZero()){return new LinkedLargeInteger(0);}
        LargeIntegerBuilder product = this.multiplyMagnitudes(otherNum);
        if (this.getSign() == otherNum.getSign()){product.setSign(1);}
        else {product.setSign(-1);}
        return product.copy();
        
    }
    
    /**
     * Convenience method to take the product of a Large Integer and a long.
     * Immediately converts long to LinkedLargeInteger so see multiply(LargeInteger largeInt)
     * for more information.
     * @param otherNum the number to multiply by
     * @return the product of the caller and otherNum
     */
    @Override
    public LargeInteger multiply(long otherNum){
        return this.multiply(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Create a new Large Integer which is the whole number quotient of the caller
     * and other number passed into the method.  Decimal is truncated with no rounding.
     * @param largeInt the number to be divided by
     * @return the quotient of caller divided by the other number
     * @throws IllegalArgumentException if the other number is zero
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger dividedBy(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (otherNum.isZero()){throw new IllegalArgumentException("Divide by zero exception");}
        if (this.largerMagnitude(otherNum) < 0){return new LinkedLargeInteger(0);}
        if (this.largerMagnitude(otherNum) == 0){return (this.getSign() == otherNum.getSign()) ? new LinkedLargeInteger(1) : new LinkedLargeInteger(-1);}
        LargeIntegerBuilder quotient = this.divideMagnitudes(otherNum);
        if (this.getSign() == otherNum.getSign()){quotient.setSign(1);}
        else {quotient.setSign(-1);}
        return quotient.copy();
    }
    
    /**
     * Convenience method to divide the caller by a long. This method immediately
     * converts to a Large Integer so see dividedBy(LargeInteger largeInt) 
     * for more info.
     * @param otherNum the number to divide by
     * @return the quotient of the caller divided by other number
     */
    @Override
    public LargeInteger dividedBy(long otherNum){
        return this.dividedBy(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Calculates the remainder of the caller integer divided by the other number
     * using the formula remainder = caller - ((floor)caller/otherNum * otherNum).
     * @param largeInt the other number to take the remainder of division by that number
     * @return the remainder of the integer division of caller and the other number
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger remainder(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (otherNum.isZero()){throw new IllegalArgumentException("Divide by zero exception");}
        if (this.isZero()){return new LinkedLargeInteger(0);}
        else{
            return this.subtract(this.dividedBy(otherNum).multiply(otherNum));    
        }
    }
    
    /**
     * Convenience method that converts otherNum to a LinkedLargeInteger so see
     * remainder(LargeInteger largeInt) for more information.
     * @param otherNum the number to take the remainder by
     * @return the remainder of the caller integer divided by otherNum
     */
    @Override
    public LargeInteger remainder(long otherNum){
        return this.remainder(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Takes the number theory definition of caller mod(largeInt) so if largeInt is
     * positive the return value is always positive(or zero) and if largeInt is negative
     * the return value is always negative(or zero).
     * @param largeInt the number to mod by
     * @return the group that the caller is in in terms of modular arithmetic by largeInt
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger modulo(LargeInteger largeInt){
        if (!(largeInt instanceof LinkedLargeInteger)){throw new ClassCastException("Not an instance of LinkedLargeInteger");}
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (otherNum.isZero()){throw new IllegalArgumentException("Division by zero exception");}
        if (this.isZero()){return new LinkedLargeInteger(0);}
        else{
            if (this.compareTo(0) > 0){
                if (otherNum.compareTo(0) > 0){return this.remainder(otherNum);}
                else {return this.remainder(otherNum).add(otherNum);}
            }
            else {
                if (otherNum.compareTo(0) > 0){return this.remainder(otherNum).add(otherNum);}
                else {return this.remainder(otherNum);}
            }
        }
    }
    
    /**
     * Convenience method that converts otherNum to LinkedLargeInteger so see 
     * modulo(LargeInteger largeInt) for more information.
     * @param otherNum the number to mod by
     * @return the group that the caller is in in terms of modular arithmetic by largeInt
     */
    @Override
    public LargeInteger modulo(long otherNum){
        return this.modulo(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Returns a new Large Integer equal to the caller to the power of other number.
     * Pre-Condition: other number should be greater than or equal to zero
     * @param largeInt the power to raise the caller to
     * @return the caller to the power of other number
     * @throws IllegalArgumentException if other number is negative
     * @throws ClassCastException if largeInt isn't a LinkedLargeInteger
     */
    @Override
    public LargeInteger pow(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (otherNum.compareTo(0) < 0){
            throw new IllegalArgumentException("Cannot use negative exponents" + otherNum);
        }
        if (this.isZero()){return new LinkedLargeInteger(0);}
        if (otherNum.isZero()){return new LinkedLargeInteger(1);}
        if (otherNum.compareTo(1) == 0){return this.copy();}
        else {
            LinkedLargeInteger product;
            if (otherNum.isEven()){
                product = (LinkedLargeInteger)this.pow(otherNum.dividedBy(2));
                return product.multiply(product);
            }
            else {
                product = (LinkedLargeInteger)this.pow(otherNum.subtract(1).dividedBy(2));
                return this.multiply(product).multiply(product);
            }
        }
    }
    
    /**
     * Convenience method that converts other number to a Large Integer so see
     * pow(LargeInteger largeInt) for more information.
     * @param otherNum the number to raise the caller to
     * @return the caller raised to the power of otherNum
     * @throws IllegalArgumentException if otherNum is negative
     */
    @Override
    public LargeInteger pow(long otherNum){
        if (otherNum < 0){throw new IllegalArgumentException("Cannot use negative exponents" + otherNum);}
        if (otherNum == 0){return new LinkedLargeInteger(1);}
        if (otherNum == 1){return this.copy();}
        return this.pow(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Returns the integer digit for a given decimal place. The zeroth decimal place
     * represents the one's place and the first decimal place represents the ten's
     * place and so on.
     * Pre-Condition: the decimal place should be greater than or equal to zero
     * @param decimalPlace the decimal place to retrieve the digit from
     * @return the integer value stored at that decimal place
     * @throws IllegalArgumentException if the decimal place is less than 0
     */
    public Integer getIntegerAtDecimalPlace(long decimalPlace){
        if (decimalPlace < 0){
            throw new IllegalArgumentException("Decimal Places are greater than or equal to zero " + decimalPlace);
        }
        else {
            if (decimalPlace == this.iter.currentDecimalIndex){ return this.iter.current();}
            else if (decimalPlace > this.iter.currentDecimalIndex){
                while (this.iter.hasNext()){
                    this.iter.next();
                    if (decimalPlace == this.iter.currentDecimalIndex){return this.iter.current();}
                }
                return 0;
            }else {
                while (this.iter.hasPrevious()){
                    this.iter.previous();
                    if (decimalPlace == this.iter.currentDecimalIndex){return this.iter.current();}
                }
                return 0;
            }
        }
    }
    
    /**
     * @return the sign of the large integer either 1,-1,0 representing positive, negative, or zero
     */
    public int getSign(){
        return this.sign.data;
    }
    
    /**
     * @return the decimal place of the most significant digit
     */
    public long getMostSignificantDigitIndex(){
        return this.biggestDecimalPlace;
    }
    
    /**
     * @return the integer stored in the most significant decimal place
     */
    public Integer getMostSignificantDigit(){
        return this.mostSignificantDigit.data;
    }
      
    /**
     * @return true if the number is zero, else false
     */
    public boolean isZero(){
        return this.mostSignificantDigit == this.sign.next && this.mostSignificantDigit.data == 0;
    }
    
    @Override
    public boolean isNegative(){
        return this.getSign() == -1;
    }
    /**
     * @return true if the caller is even
     */
    public boolean isEven(){
        return this.getIntegerAtDecimalPlace(0) % 2 == 0;
    }
    
    /**
     * @return true if the caller is odd
     */
    public boolean isOdd(){
        return this.getIntegerAtDecimalPlace(0) % 2 == 1;
    }
     
    /**
     * @return a new Large Integer which is the absolute value of the caller
     */
    public LinkedLargeInteger absoluteValue(){
        LargeIntegerBuilder builder = this.mutableCopy();
        if (!builder.isZero()){
            builder.setSign(1);
        }
        return builder.copy();
    }
    
    /**
     * Compares two large integers and returns 1 if the caller is larger, -1 if
     * the other number is larger, or zero if they are equal.
     * @param largeInt the large integer to compare against
     * @return 1 if caller is larger, -1 if other is bigger and 0 if they are equal
     */
    @Override
    public int compareTo(LargeInteger largeInt){
        LinkedLargeInteger otherNum = (LinkedLargeInteger)largeInt;
        if (this.getSign() > otherNum.getSign()){return 1;}
        if (this.getSign() < otherNum.getSign()){return -1;}
        if (this.isZero() && otherNum.isZero()){return 0;}
        //They now must have equal sign -- Compare magnitude and sign and return accordingly
        if (this.largerMagnitude(otherNum) == 0){return 0;}
        if (this.largerMagnitude(otherNum) > 0){return (this.getSign() > 0) ? 1 : -1;}
        else{return (this.getSign() > 0) ? -1 : 1;}
        //Catch all -- if the magnitude is smaller and it is positive return -1, but if
        //negative return 1
    }
    
    /**
     * Convenience method that converts other number into a Large Integer so see
     * compareTo(LargeInteger largeInt) for more details.
     * @param otherNum the number to compare against
     * @return 1,-1, or 0 if caller is greater than, less than, or equal respectively
     */
    public int compareTo(long otherNum){
        return this.compareTo(new LinkedLargeInteger(otherNum));
    }
    
    /**
     * Compares two Large Integers in absolute value. Returns 1,-1 or 0 if the caller
     * is larger, smaller, or equal to other number respectively in magnitude. 
     * @param otherNum the number to be compared against
     * @return 1,-1, or 0 if the caller is greater,smaller, or equal in abs to other number
     */
    public int absCompare(LinkedLargeInteger otherNum){
        return this.largerMagnitude(otherNum);
    }
    
    /**
     * Convenience method that converts other number into a Large Integer so see
     * absCompare(LinkedLargeInteger otherNum) for details
     * @param otherNum the number to compare against
     * @return 1,-1, or 0 if the caller is greater,smaller, or equal in abs to other number
     */
    public int absCompare(long otherNum){
        return this.absCompare(new LinkedLargeInteger(otherNum));
    }
    /**
     * Tests the equality against another object.  Two Large Integers are considered
     * equal if they have the same sign and value.
     * @param object the object to test against
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){return true;}
        if (!(object instanceof LinkedLargeInteger)){return false;}
        return this.compareTo((LinkedLargeInteger)object) == 0;
    }
    
    /**
     * Create a string representation of a Large Integer.  This string contains
     * commas to mark decimal places but this string can initialize a new Large
     * Integer as is, commas included.
     * @return the string representation of a Large Integer
     */
    @Override
    public String toString(){
        if (this.isZero()){return "0";}
        StringBuilder string = new StringBuilder();
        if (this.getSign() < 0){string.append("-");}
        for (long i = this.biggestDecimalPlace; i >= 0; i--){
            string.append(this.getIntegerAtDecimalPlace(i));
            if (i % 3 == 0 && i != 0){string.append(",");}
        }
        return string.toString();
    }
    
    /**
     * @return a new large integer copy of the caller
     */
    public LinkedLargeInteger copy(){
        return new LinkedLargeInteger(this);
    }
    
    /**
     * Test if the passed Large Integer is in the range of the long integer representation.
     * @param number the number to be tested
     * @return true if the number is in the range of the long representation, else false
     */
    public static boolean canUseLong(LinkedLargeInteger number){
        return (number.compareTo(MAX_LONG) <= 0) && (number.compareTo(MIN_LONG) >= 0);
    }
    
    /**
     * Converts the caller to a long representation of the number if the number is
     * in range.  Should be used in conjunction with LinkedLargeInteger.canUseLong
     * to ensure that the number is in range.
     * @return a long representation of the number
     * @throws IllegalArgumentException if the number is out of long's bounds
     */
    public long toLong(){
        if (!canUseLong(this)){
            throw new IllegalArgumentException("Number out of bounds");
        }
        long result = this.getSign() * this.sign.next.data;
        long power = 10;
        for (long i = 1; i <= this.biggestDecimalPlace; i++){
            result += this.getIntegerAtDecimalPlace(i) * power;
            power *= 10;
        }
        return result;
    }
     
    //<<<<<<<<<<<<<<<<<<<<<< Private Helper Methods >>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * @return a new mutable copy of the caller to use for long computations
     */
    private LargeIntegerBuilder mutableCopy(){
        return new LargeIntegerBuilder(this);
    }
    
    /**
     * Adds the magnitudes of the caller and other number and returns the positive
     * result.  Equal to adding their absolute values.  Note the returned value
     * is the mutable version because this is used only in computations.
     * @param otherNum the number to add in absolute value
     * @return a new mutable large integer that is the sum of the absolute values
     */
    private LargeIntegerBuilder addMagnitudes(LinkedLargeInteger otherNum){
        LargeIntegerBuilder sum = new LargeIntegerBuilder();
        long maxDecimalPlace = Math.max(this.biggestDecimalPlace, otherNum.biggestDecimalPlace);
        for (long i = 0; i <= maxDecimalPlace; i++){
            sum.addToDecimalPlace(this.getIntegerAtDecimalPlace(i) + otherNum.getIntegerAtDecimalPlace(i), i);
        }
        return sum;
    }
    
    /**
     * Takes the difference of the caller and other number and returns the positive
     * result.  Equal to taking the difference and then taking the absolute value
     * of the result.  Return value is the mutable version as this is used in computation only.
     * @param otherNum  the number to take the difference with
     * @return a new mutable large integer that is the positive difference of the two values
     */
    private LargeIntegerBuilder subMagnitudes(LinkedLargeInteger otherNum){
        if (this.largerMagnitude(otherNum) == 0){return new LargeIntegerBuilder();}
        if (this.isZero() || otherNum.isZero()){
            LargeIntegerBuilder returnValue;
            if (this.isZero()){
                returnValue = otherNum.mutableCopy();
            }else {
                returnValue = this.mutableCopy();
            }
            returnValue.setSign(1);
            return returnValue;
        }
        LargeIntegerBuilder biggerNumber;
        LinkedLargeInteger smallerNumber;
        if (this.largerMagnitude(otherNum) > 0){
            biggerNumber = this.mutableCopy();
            smallerNumber = otherNum;
        }
        else {
            biggerNumber = otherNum.mutableCopy();
            smallerNumber = this;
        }
        
        for (long i = 0; i <= smallerNumber.biggestDecimalPlace; i++){
            int result = biggerNumber.getIntegerAtDecimalPlace(i) - smallerNumber.getIntegerAtDecimalPlace(i);
            if (result < 0){
                result += 10;
                biggerNumber.borrowFromNextDecimalPlace(i);
            }
            biggerNumber.setDigitAtDecimalPlace(result, i);
        }
        biggerNumber.setSign(1);
        return biggerNumber;
    }
    
    /**
     * Multiplies out the digits of caller and otherNum and returns a positive
     * product regardless of their signs.
     * @param otherNum the number to multiply by
     * @return the positive product of caller and other number
     */
    private LargeIntegerBuilder multiplyMagnitudes(LinkedLargeInteger otherNum){
        LargeIntegerBuilder product = new LargeIntegerBuilder();
        for (long i = 0; i <= this.biggestDecimalPlace; i++){
            for (long j = 0; j <= otherNum.biggestDecimalPlace; j++){
                product.addToDecimalPlace(this.getIntegerAtDecimalPlace(i) * otherNum.getIntegerAtDecimalPlace(j), i + j);
            }
        }
        return product;
    }
    
    /**
     * Divides the caller by other number and returns the positive quotient regardless
     * of their signs.
     * @param otherNum the number to divide by
     * @return the positive quotient of the caller divided by other number
     * @throws IllegalArgumentException if the other number is zero
     * @throws ClassCastException if the value returned from multiply isn't a 
     * LinkedLargeInteger
     */
    private LargeIntegerBuilder divideMagnitudes(LinkedLargeInteger otherNum){
        if (otherNum.isZero()){throw new IllegalArgumentException("Divide by zero exception");}
        boolean canUseLong = canUseLong((LinkedLargeInteger)otherNum.absoluteValue().multiply(new LinkedLargeInteger(10)));
        if (!canUseLong){
            return this.divideMagnitudesWOLong(otherNum);
        }
        else {
            LargeIntegerBuilder quotient = new LargeIntegerBuilder();
            long otherNumLong = otherNum.absoluteValue().toLong();
            long workingNumber = 0;
            for (long i = this.biggestDecimalPlace; i >= 0; i--){
                workingNumber *= 10;
                workingNumber += this.getIntegerAtDecimalPlace(i);
                int result = (int)(workingNumber / otherNumLong);//this number is less than 10 by design
                if (result > 0){
                    quotient.setDigitAtDecimalPlace(result, i);
                    workingNumber -= result * otherNumLong;
                }
                
            }
            return quotient;
        }
    }
    
    /**
     * The long division process is made up of smaller divisions of numbers up to
     * ten times the size of the number you divide by(in this case otherNum). If
     * other number is close to the range of a long then we cannot simply convert
     * everything to longs and use the native division operator on longs.  This method
     * covers that rare but necessary case by working only in LinkedLargeIntegers.
     * @param otherNum the number to divide by
     * @return the absolute value of the quotient of the caller and other number
     * @throws IllegalArgumentException if other number is zero
     */
    private LargeIntegerBuilder divideMagnitudesWOLong(LinkedLargeInteger otherNum){
        if (otherNum.isZero()){throw new IllegalArgumentException("Divide by zero exception");}
        LinkedLargeInteger posOtherNum = otherNum.absoluteValue();
        LargeIntegerBuilder quotient = new LargeIntegerBuilder();
        LargeIntegerBuilder workingNumber = new LargeIntegerBuilder();
        for (long i = this.biggestDecimalPlace; i >= 0; i--){
            workingNumber.multiplyByTen();
            workingNumber.setDigitAtDecimalPlace(this.getIntegerAtDecimalPlace(i), 0);
            int result = 0;
            if (workingNumber.absCompare(otherNum) == 0){
                result = 1;
            }
            else if (workingNumber.absCompare(otherNum) > 0){
                result = 9;
                while (posOtherNum.multiply(result).compareTo(workingNumber) > 0){
                    result--;
                }
            }
            if (result > 0){
                quotient.setDigitAtDecimalPlace(result, i);
                workingNumber = ((LinkedLargeInteger)workingNumber.subtract(posOtherNum.multiply(result))).mutableCopy();
            }
        }
        return quotient;
    }
    
    /**
     * Tests whether caller is larger than other number by magnitude only.  Equal
     * to comparing the absolute values. Returns 1,-1, or 0 if caller is larger,smaller,
     * or equal to other number respectively.
     * @param otherNum the large integer to compare against
     * @return 1,-1, or 0 if caller is larger,smaller, or equal to other number
     */
    private int largerMagnitude(LinkedLargeInteger otherNum){
        if (this.biggestDecimalPlace == otherNum.biggestDecimalPlace){
            if (this.getMostSignificantDigit() == otherNum.getMostSignificantDigit()){
                this.iter.gotoMostSignificantDigit();
                otherNum.iter.gotoMostSignificantDigit();
                while (this.iter.current() == otherNum.iter.current()){
                    if (this.iter.hasPrevious() && otherNum.iter.hasPrevious()){
                        this.iter.previous();
                        otherNum.iter.previous();
                    }
                    else {return 0;}
                }
                return (this.iter.current() > otherNum.iter.current()) ? 1 : -1;
            }
            else {return (this.getMostSignificantDigit() > otherNum.getMostSignificantDigit()) ? 1 : -1;}
        }
        else {return (this.biggestDecimalPlace > otherNum.biggestDecimalPlace) ? 1 : -1;}
    }
    
    //<<<<<<<<<<<<<<<<<<<<<< Utility Classes Below >>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    /**
     * A node holds one digit of the number and holds references to the previous
     * and next digit.
     */
    protected class Node {
        private Integer data;
        private Node previous;
        private Node next;
        
        /**
         * Create a new node with the given parameters
         * @param data the integer value of the digit
         * @param previous a reference to the previous digit
         * @param next a reference to the next digit
         */
        private Node(Integer data, Node previous, Node next){
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
        
        /**
         * Create a new node with the given number and no connected digits
         * @param data the integer to be stored as a digit
         */
        private Node(Integer data){
            this(data, null, null);
        }
        
        /**
         * Create a new node with the given number and previous digit
         * @param data the integer to be stored as a digit
         * @param previous a reference to the previous digit
         */
        private Node(Integer data, Node previous){
            this(data, previous, null);
        }
        
    }
    
    /**
     * An iterator specialized to work back and forth over the linked large integer
     * and retrieve values, nodes, or what decimal place it is currently on.
     */
    protected class IntegerIterator{
        private Node currentDecimalPlace;
        private long currentDecimalIndex;
        
        /**
         * Initialize a new iterator that starts at decimal place 0
         */
        private IntegerIterator(){
            this.currentDecimalIndex = 0;
            this.currentDecimalPlace = LinkedLargeInteger.this.sign.next;
        }
        
        /**
         * @return the integer stored at the current location
         */
        private Integer current() {
            return this.currentDecimalPlace.data;
        }
        
        /**
         * Tests if the iterator can go further up the number toward a more 
         * significant decimal places.
         * @return true if it can progress, else false
         */
        private boolean hasNext(){
            return this.currentDecimalPlace.next != null;
        }
        
        /**
         * Tests if the iterator can go down the number toward the less significant
         * decimal places.
         * @return true if it can progress, else false
         */
        private boolean hasPrevious(){
            return this.currentDecimalIndex > 0 && this.currentDecimalPlace.previous != null;
        }
        
        /**
         * Iterate one decimal place to a more significant decimal place and return
         * the integer stored there if one is interested in the value.
         * Pre-Condition: hasNext() should return true before this method is called
         * @return the integer stored at the next decimal place
         * @throws NoSuchElementException if there is no decimal place to iterate to
         */
        private Integer next(){
            if (!this.hasNext()){
                throw new NoSuchElementException("No integer at decimal place: " + (this.currentDecimalIndex + 1));
            }
            else {
                this.currentDecimalIndex++;
                this.currentDecimalPlace = this.currentDecimalPlace.next;
                return this.currentDecimalPlace.data;
            } 
        }
        
        /**
         * Iterate one decimal place to a less significant decimal place and return
         * the integer stored there if one is interested in the value.
         * Pre-Condition: hasPrevious() should return true before this method is called
         * @return the integer stored at the previous decimal place
         * @throws NoSuchElementException if there is no decimal place to iterate to
         */
        private Integer previous(){
            if (!this.hasPrevious()){
                throw new NoSuchElementException("No integer at decimal place: " + (this.currentDecimalIndex - 1));
            }
            else {
                this.currentDecimalIndex--;
                this.currentDecimalPlace = this.currentDecimalPlace.previous;
                return this.currentDecimalPlace.data;
            }
        }
        
        /**
         * Iterate directly to the most significant digit.  It should be noted
         * that this method runs in constant time O(1)
         */
        private void gotoMostSignificantDigit(){
            this.currentDecimalPlace = LinkedLargeInteger.this.mostSignificantDigit;
            this.currentDecimalIndex = LinkedLargeInteger.this.biggestDecimalPlace;
        }
        
        /**
         * Iterate directly to the least significant digit.  It should be noted
         * that this method runs in constant time O(1)
         */
        private void gotoLeastSignificantDigit(){
            this.currentDecimalPlace = LinkedLargeInteger.this.sign.next;
            this.currentDecimalIndex = 0;
        }
        

    }
    
    /**
     * A mutable large integer which is a private utility class used to build up
     * a new number based on a collection of results during a long computation.
     * This allows you to do multiplication, addition, and subtraction by working
     * digit by digit without having to repeatedly copy the number over and over
     * again.
     */
    private class LargeIntegerBuilder extends LinkedLargeInteger{
        
        /**
         * Initialize a new mutable large integer equal to zero.
         */
        private LargeIntegerBuilder(){
            this.sign = new Node(0, null, new Node(0, this.sign));
            this.mostSignificantDigit = this.sign.next;
            this.biggestDecimalPlace = 0;
            this.iter = new IntegerIterator();
            
        }
        
        private LargeIntegerBuilder(long num){
            super(num);
        }
        
        /**
         * Initialize a new mutable large integer equal to the value of the 
         * large integer passed.
         * @param largeInt the large integer to be copied
         */
        private LargeIntegerBuilder(LinkedLargeInteger largeInt){
            largeInt.iter.gotoLeastSignificantDigit();
            //Initialize the first two
            this.sign = new Node(largeInt.sign.data, null, new Node(largeInt.iter.current(), this.sign));
            this.mostSignificantDigit = this.sign.next;
            this.biggestDecimalPlace = 0;
            this.iter = new IntegerIterator();
            while (largeInt.iter.hasNext()){
                this.iter.currentDecimalPlace.next = new Node(largeInt.iter.next(), this.iter.currentDecimalPlace);
                this.iter.next();
                this.mostSignificantDigit = this.iter.currentDecimalPlace;
                this.biggestDecimalPlace++; 
            }
        }
        
        /**
         * Set the digit at a given decimal place.  This method overrides the 
         * original value in that decimal place. Note decimal place 0 is the ones digit
         * and digits should be positive.
         * Pre-Condition: decimal places are greater than or equal to zero and 
         * digit should be greater than or equal to zero and less than ten.
         * @param digit the new integer to be stored
         * @param decimalPlace the decimal place to store the new value in
         * @return true if the value was set, false if it couldn't be set for
         * unknown reasons.  Used in debugging only.
         * @throws IllegalArgumentException if the decimal place is less than zero or
         * if the digit is less than zero or greater than 9.
         */
        private boolean setDigitAtDecimalPlace(int digit, long decimalPlace){
            if (!(decimalPlace >= 0)){
                throw new IllegalArgumentException("Decimal places are greater than or equal to zero: " + decimalPlace);
            }
            if (digit > 9 || digit < 0){
                throw new IllegalArgumentException("Digits are positive numbers between zero and nine: " + digit);
            }
            boolean shouldUpdateSigDigit = false;
            boolean shouldTrimZeros = false;
            while (decimalPlace > this.iter.currentDecimalIndex){
                if (!this.iter.hasNext()){
                    if (digit == 0){return true;}//No need extending the number since they will all be leading zeros
                    this.iter.currentDecimalPlace.next = new Node(0, this.iter.currentDecimalPlace);
                    shouldUpdateSigDigit = true;
                }
                this.iter.next();
            }
            while (decimalPlace < this.iter.currentDecimalIndex && this.iter.hasPrevious()){
                this.iter.previous();
            }
            if (decimalPlace == this.iter.currentDecimalIndex){
                if (decimalPlace == this.biggestDecimalPlace && digit == 0){
                    shouldTrimZeros = true;
                }
                if (this.isZero() && digit != 0){
                    this.setSign(1);
                }
                this.iter.currentDecimalPlace.data = digit;
                if (shouldUpdateSigDigit){this.updateMostSigDigit();}
                if (shouldTrimZeros){this.trimLeadingZeros();}
                return true;
            }
            else {return false;}//Number not set for unknown reason    
        }
        
        /**
         * A quick way to multiply a mutable Large Integer by ten using an insert
         * into the linked list at index 0
         */
        private void multiplyByTen(){
            if (!this.isZero()){
                this.sign.next.previous = new Node(0, this.sign, this.sign.next);
                this.sign.next = this.sign.next.previous;
                this.biggestDecimalPlace++;
            }
            this.iter.gotoLeastSignificantDigit();
        }
        
        /**
         * Trims and leading zeros and resets most significant digit place and index
         * to the new highest non-zero digit.  Does nothing if nothing should be trimmed
         */
        private void trimLeadingZeros(){
            this.iter.gotoMostSignificantDigit();
            while (this.iter.current() == 0 && this.iter.hasPrevious()){
                this.iter.previous();
            }
            if (this.iter.currentDecimalIndex == 0 && this.iter.current() == 0){
                this.clear();
                return;
            }
            this.mostSignificantDigit = this.iter.currentDecimalPlace;
            this.mostSignificantDigit.next = null;
            this.biggestDecimalPlace = this.iter.currentDecimalIndex;
        }
        
        /**
         * Updates the most significant digit and index to if their are nodes further
         * up the chain from the current m.s.d.. This would occur during carries in 
         * addition and multiplication.
         */
        private void updateMostSigDigit(){
            this.iter.gotoMostSignificantDigit();
            while (this.iter.hasNext()){
                this.iter.next();
            }
            if (this.iter.currentDecimalPlace != this.mostSignificantDigit){
                this.mostSignificantDigit = this.iter.currentDecimalPlace;
                this.biggestDecimalPlace = this.iter.currentDecimalIndex;
            }
        }
        
        /**
         * Adds a given number number to a given decimal place.  If there is already
         * a value in the decimal place that number is added to the result before it 
         * is set back into the appropriate decimal places.  This is a recursive method
         * that will propagate multi-digit sums up the decimal places appropriately. 
         * @param numberToAdd the number to add
         * @param decimalPlace the decimal place to add at
         * @throws IllegalArgumentException if the number to add is less than 0 or
         * if the decimal place is less than zero
         */
        private void addToDecimalPlace(long numberToAdd, long decimalPlace){
            if (numberToAdd < 0){ throw new IllegalArgumentException("Number should be positive: " + numberToAdd); }
            if (decimalPlace < 0){ throw new IllegalArgumentException("Digit should be greater than or equal to zero: " + decimalPlace); }
            if (numberToAdd == 0){return;}//valid but does nothing.
            numberToAdd += this.getIntegerAtDecimalPlace(decimalPlace);
            this.setDigitAtDecimalPlace((int)(numberToAdd % 10), decimalPlace);
            if (numberToAdd >= 10){
                this.addToDecimalPlace(numberToAdd / 10, decimalPlace + 1);
            }
        }
        
        /**
         * Subtract one from the next decimal place for subtracting.  This is a 
         * recursive method that propagates borrowing up the decimal places if 
         * borrowing is required again: example -> (200 - 1).  The decimal place
         * is required to have a higher decimal place to borrow from.
         * @param decimalPlace the decimal place executing the borrow, note that
         * the borrow is taken from the decimal place one higher in magnitude
         * @throws IllegalArgumentException if the decimal place is greater than or equal
         * to the highest decimal place or less than 0
         */
        private void borrowFromNextDecimalPlace(long decimalPlace){
            if (decimalPlace >= this.biggestDecimalPlace){throw new IllegalArgumentException("No decimal place to borrow from: " + decimalPlace);}
            if (decimalPlace < 0){throw new IllegalArgumentException("Decimal places are greater than or equal to zero: " + decimalPlace);}
            if (this.getIntegerAtDecimalPlace(decimalPlace + 1) == 0){
                this.borrowFromNextDecimalPlace(decimalPlace + 1);
                this.setDigitAtDecimalPlace(9, decimalPlace + 1);
                return;
            }
            boolean shouldTrimZeros = false;
            if (this.getIntegerAtDecimalPlace(decimalPlace + 1) == 1){shouldTrimZeros = true;}
            this.setDigitAtDecimalPlace(this.getIntegerAtDecimalPlace(decimalPlace + 1) - 1, decimalPlace + 1);
            if (shouldTrimZeros){
                this.trimLeadingZeros();
            }
        }
        
        /**
         * Set a new sign for the numbers.  Numbers greater than 0 sign to 1, numbers
         * less than 0 sign to -1 and 0 signs to 0.
         * @param signedNumber a signed number for the given sign +/- or 0 if
         * you want to zero out the number.
         */
        private void setSign(int signedNumber){
            if (signedNumber == 0){
                this.clear();
            }
            else if (signedNumber > 0){this.sign.data = 1;}
            else if (signedNumber < 0){this.sign.data = -1;}
        }
        
        /**
         * Clear the value of the number to zero.
         */
        private void clear(){
            this.sign = new Node(0, null, new Node(0, this.sign));
            this.mostSignificantDigit = this.sign.next;
            this.biggestDecimalPlace = 0;
            this.iter = new IntegerIterator();
        }
        
    }
    
    
    
}
