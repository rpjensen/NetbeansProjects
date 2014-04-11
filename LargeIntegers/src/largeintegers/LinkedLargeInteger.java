

package largeintegers;

import java.util.NoSuchElementException;

/**
 * An arbitrary length integer that can do basic computations and arithmetic whose size
 * is only limited by your CPU.
 * @author Ryan Jensen
 * @version Apr 28, 2014
 */
public class LinkedLargeInteger implements LargeInteger{
    private Node sign;
    private Node mostSignificantDigit;
    private int biggestDecimalPlace;
    private IntegerIterator iter;
    
    /**
     * Create a new Large Integer from a string consisting of numbers and select
     * special characters.
     * Pre-Condition: the string is either numbers 0-9 led by +/- or nothing. Also
     * commas are acceptable and will be parsed out of the string.
     * @param number - the string representation of the number to copy
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public LinkedLargeInteger(String number){
        this(Long.parseLong(number));
    }
    
    /**
     * Create a new Large Integer from an existing integer.
     * @param number - the number to copy
     */
    public LinkedLargeInteger(int number){
        this((long)number);
    }
    
    /**
     * Create a new Large Integer from an existing long.
     * @param number - the number to copy
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
            this.mostSignificantDigit.next = new Node((int)(number % 10), this.mostSignificantDigit);
            //new value is the ones digit of the current number
            number = number / 10;//throw away the ones digit for next iteration
            this.mostSignificantDigit = this.mostSignificantDigit.next;
            biggestDecimalPlace++;    
        }while (number != 0);
        this.iter = new IntegerIterator();
    }
    
    /**
     * Create a new LinkedLargeInteger from an existing Large Integer..
     * @param builder - the large integer to copy
     */
    private LinkedLargeInteger(LinkedLargeInteger builder){
        builder.iter.gotoLeastSignificantDigit();
        //Initialize the first two
        this.sign = new Node(builder.sign.data, null, new Node(builder.iter.current(), this.sign));
        this.mostSignificantDigit = this.sign.next;
        this.biggestDecimalPlace = 0;
        this.iter = new IntegerIterator();
        while (builder.iter.hasNext()){
            this.iter.currentDecimalPlace.next = new Node(builder.iter.next(), this.iter.currentDecimalPlace);
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
    public LinkedLargeInteger negate(){
        LargeIntegerBuilder builder = this.mutableCopy();
        builder.setSign(-1 * builder.getSign());
        return builder.copy();
    }
    
    /**
     * Create and return a new large integer that is the sum of the caller and the
     * other number passed into the method.
     * @param otherNum - the number to be added to the caller
     * @return a new large integer that is the sum of the two
     */
    @Override
    public LinkedLargeInteger add(LinkedLargeInteger otherNum){
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
     * Create and return a new large integer that is the difference of the caller
     * and the other number passed into the method.
     * @param otherNum - the number to be subtracted from the caller
     * @return a new large integer that is the difference of the two
     */
    @Override
    public LinkedLargeInteger subtract(LinkedLargeInteger otherNum){
        if (this.isZero() || otherNum.isZero()){return this.isZero() ? otherNum.copy() : this.copy();}
        if (this.getSign() != otherNum.getSign()){return this.add(otherNum.negate());}
        else {
            LargeIntegerBuilder dif = this.subMagnitudes(otherNum);
            if (dif.isZero()){
                return new LinkedLargeInteger(0);
            }
            if (this.getSign() > 1){ 
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
     * Returns the integer digit for a given decimal place. The zeroth decimal place
     * represents the one's place and the first decimal place represents the ten's
     * place and so on.
     * Pre-Condition: the decimal place should be greater than or equal to zero
     * @param decimalPlace - the decimal place to retrieve the digit from
     * @return the integer value stored at that decimal place
     * @throws IllegalArgumentException if the decimal place is less than 0
     */
    public Integer getIntegerAtDecimalPlace(int decimalPlace){
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
    public int getMostSignificantDigitIndex(){
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
    
    /**
     * Compares two large integers and returns 1 if the caller is larger, -1 if
     * the other number is larger, or zero if they are equal.
     * @param otherNum - the large integer to compare against
     * @return 1 if caller is larger, -1 if other is bigger and 0 if they are equal
     */
    @Override
    public int compareTo(LinkedLargeInteger otherNum){
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
     * Tests the equality against another object.  Two Large Integers are considered
     * equal if they have the same sign and value.
     * @param object- the object to test against
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){return true;}
        if (!(object instanceof LinkedLargeInteger)){return false;}
        return this.compareTo((LinkedLargeInteger)object) == 0;
    }
    
    /**
     * @return a new large integer copy of the caller
     */
    public LinkedLargeInteger copy(){
        return new LinkedLargeInteger(this);
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
     * @param otherNum - the number to add in absolute value
     * @return a new mutable large integer that is the sum of the absolute values
     */
    private LargeIntegerBuilder addMagnitudes(LinkedLargeInteger otherNum){
        LargeIntegerBuilder sum = new LargeIntegerBuilder();
        int maxDecimalPlace = Math.max(this.biggestDecimalPlace, otherNum.biggestDecimalPlace);
        for (int i = 0; i < maxDecimalPlace; i++){
            sum.addToDecimalPlace(i, this.getIntegerAtDecimalPlace(i) + otherNum.getIntegerAtDecimalPlace(maxDecimalPlace));
        }
        return sum;
    }
    
    /**
     * Takes the difference of the caller and other number and returns the positive
     * result.  Equal to taking the difference and then taking the absolute value
     * of the result.  Return value is the mutable version as this is used in computation only.
     * @param otherNum -  the number to take the difference with
     * @return a new mutable large integer that is the positive difference of the two values
     */
    private LargeIntegerBuilder subMagnitudes(LinkedLargeInteger otherNum){
        if (this.largerMagnitude(otherNum) == 0){return new LargeIntegerBuilder();}
        if (this.isZero() || otherNum.isZero()){
            LargeIntegerBuilder returnValue = null;
            if (this.isZero()){
                returnValue = otherNum.mutableCopy();
            }else {
                returnValue = this.mutableCopy();
            }
            returnValue.setSign(1);
            return returnValue;
        }
        LargeIntegerBuilder biggerNumber = null;
        LinkedLargeInteger smallerNumber = null;
        if (this.largerMagnitude(otherNum) > 0){
            biggerNumber = this.mutableCopy();
            smallerNumber = otherNum;
        }
        else {
            biggerNumber = otherNum.mutableCopy();
            smallerNumber = this;
        }
        
        for (int i = 0; i <= smallerNumber.biggestDecimalPlace; i++){
            int result = biggerNumber.getIntegerAtDecimalPlace(i) - smallerNumber.getIntegerAtDecimalPlace(i);
            if (result < 0){
                result += 10;
                biggerNumber.borrowFromNextDecimalPlace(i);
            }
        }
        biggerNumber.setSign(1);
        return biggerNumber;
    }
    
    /**
     * Tests whether caller is larger than other number by magnitude only.  Equal
     * to comparing the absolute values. Returns 1,-1, or 0 if caller is larger,smaller,
     * or equal to other number respectively.
     * @param otherNum - the large integer to compare against
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
    private class Node {
        private Integer data;
        private Node previous;
        private Node next;
        
        /**
         * Create a new node with the given parameters
         * @param data - the integer value of the digit
         * @param previous - a reference to the previous digit
         * @param next - a reference to the next digit
         */
        private Node(Integer data, Node previous, Node next){
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
        
        /**
         * Create a new node with the given number and no connected digits
         * @param data - the integer to be stored as a digit
         */
        private Node(Integer data){
            this(data, null, null);
        }
        
        /**
         * Create a new node with the given number and previous digit
         * @param data - the integer to be stored as a digit
         * @param previous - a reference to the previous digit
         */
        private Node(Integer data, Node previous){
            this(data, previous, null);
        }
        
    }
    
    /**
     * An iterator specialized to work back and forth over the linked large integer
     * and retrieve values, nodes, or what decimal place it is currently on.
     */
    private class IntegerIterator{
        private Node currentDecimalPlace;
        private int currentDecimalIndex;
        
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
         * @return the current decimal place 
         */
        private int currentDecimalPlace(){
            return this.currentDecimalIndex;
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
     * The reason I went for this approach is that LinkedLargeInteger has to be
     * immutable so I wanted a class that would represent a separate mutable version
     * which to encapsulate and cut down on name space clutter put into an inner class.
     * One thing I realized is that if the only way we have to create a new linked
     * large integer was through a long then it would take away from this classes
     * purpose as a variable length integer storage device.  If one large integer
     * is multiplied by another and both are near the range of a long then there would
     * be no way to compute the product and initialize a linked large integer so 
     * I knew I needed a way to do these computations independent of the long class.
     */
    private class LargeIntegerBuilder extends LinkedLargeInteger{
        private Node sign;
        private Node mostSignificantDigit;
        private int biggestDecimalPlace;
        private IntegerIterator iter;
        
        /**
         * Initialize a new mutable large integer equal to zero.
         */
        private LargeIntegerBuilder(){
            this.sign = new Node(0, null, new Node(0, this.sign));
            this.mostSignificantDigit = this.sign.next;
            this.biggestDecimalPlace = 0;
            this.iter = new IntegerIterator();
        }
        
        /**
         * Initialize a new mutable large integer equal to the value of the 
         * large integer passed.
         * @param largeInt - the large integer to be copied
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
         * @param digit - the new integer to be stored
         * @param decimalPlace - the decimal place to store the new value in
         * @return true if the value was set, false if it couldn't be set for
         * unknown reasons.  Used in debugging only.
         * @throws IllegalArgumentException if the decimal place is less than zero or
         * if the digit is less than zero or greater than 9.
         */
        private boolean setDigitAtDecimalPlace(Integer digit, int decimalPlace){
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
                if (this.isZero() && digit == 0){
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
         * @param numberToAdd - the number to add
         * @param decimalPlace - the decimal place to add at
         * @throws IllegalArgumentException if the number to add is less than 0 or
         * if the decimal place is less than zero
         */
        private void addToDecimalPlace(Integer numberToAdd, int decimalPlace){
            if (numberToAdd < 0){ throw new IllegalArgumentException("Number should be positive: " + numberToAdd); }
            if (decimalPlace < 0){ throw new IllegalArgumentException("Digit should be greater than or equal to zero: " + decimalPlace); }
            if (numberToAdd == 0){return;}//valid but does nothing.
            numberToAdd += this.getIntegerAtDecimalPlace(decimalPlace);
            this.setDigitAtDecimalPlace(numberToAdd % 10, decimalPlace);
            if (numberToAdd >= 10){
                this.addToDecimalPlace(numberToAdd / 10, decimalPlace + 1);
            }
        }
        
        /**
         * Subtract one from the next decimal place for subtracting.  This is a 
         * recursive method that propagates borrowing up the decimal places if 
         * borrowing is required again: example -> (200 - 1).  The decimal place
         * is required to have a higher decimal place to borrow from.
         * @param decimalPlace - the decimal place executing the borrow, note that
         * the borrow is taken from the decimal place one higher in magnitude
         * @throws IllegalArgumentException if the decimal place is greater than or equal
         * to the highest decimal place or less than 0
         */
        private void borrowFromNextDecimalPlace(int decimalPlace){
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
         * @param signedNumber - a signed number for the given sign +/- or 0 if
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
