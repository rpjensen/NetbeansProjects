

package largeintegers;

import java.util.NoSuchElementException;

/**
 *
 * @author Ryan Jensen
 * @version Apr 7, 2014
 */
public class LinkedLargeInteger implements LargeInteger{
    private Node sign;
    private Node mostSignificantDigit;
    private int biggestDecimalPlace;
    private IntegerIterator iter;
    
    
    public LinkedLargeInteger(String number){
        this(Long.parseLong(number));
    }
    
    public LinkedLargeInteger(int number){
        this((long)number);
    }
    
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
            this.mostSignificantDigit.next = new Node((int)(number % 10), this.mostSignificantDigit, null);
            //new value is the ones digit of the current number
            number = number / 10;//throw away the ones digit for next iteration
            this.mostSignificantDigit = this.mostSignificantDigit.next;
            biggestDecimalPlace++;    
        }while (number != 0);
        this.iter = new IntegerIterator();
    }
    
    private LinkedLargeInteger(LargeIntegerBuilder builder){
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
    
    private LinkedLargeInteger(){}
    
    //<<<<<<<<<<<<<<<<<<<<< Public Functional Methods >>>>>>>>>>>>>>>>>>>>>>
    @Override
    public LinkedLargeInteger negate(){
        LargeIntegerBuilder builder = this.mutableCopy();
        builder.setSign(-1 * builder.getSign());
        return builder.copy();
    }
    
    @Override
    public LinkedLargeInteger add(LinkedLargeInteger otherNum){
        
    }
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
    
    public int getSign(){
        return this.sign.data;
    }
    
    public int getMostSignificantDigitIndex(){
        return this.biggestDecimalPlace;
    }
    
    public Integer getMostSignificantDigit(){
        return this.mostSignificantDigit.data;
    }
    
    //<<<<<<<<<<<<<<<<<<<<<< Private Helper Methods >>>>>>>>>>>>>>>>>>>>>>
    
    private LargeIntegerBuilder mutableCopy(){
        return new LargeIntegerBuilder(this);
    }
    //<<<<<<<<<<<<<<<<<<<<<< Utility Classes Below >>>>>>>>>>>>>>>>>>>>>>>
    
    private class Node {
        private Integer data;
        private Node previous;
        private Node next;
        
        private Node(Integer data, Node previous, Node next){
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
        
        private Node(Integer data){
            this(data, null, null);
        }
        
        private Node(Integer data, Node previous){
            this(data, previous, null);
        }
        
    }
    
    private class IntegerIterator{
        private Node currentDecimalPlace;
        private int currentDecimalIndex;
        
        private IntegerIterator(){
            this.currentDecimalIndex = 0;
            this.currentDecimalPlace = LinkedLargeInteger.this.mostSignificantDigit;
        }
        
        private Integer current() {
            return this.currentDecimalPlace.data;
        }
        
        private int currentDecimalPlace(){
            return this.currentDecimalIndex;
        }
        
        private boolean hasNext(){
            return this.currentDecimalPlace.next != null;
        }
        
        private boolean hasPrevious(){
            return this.currentDecimalPlace.previous != null;
        }
        
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
        
        private void gotoMostSignificantDigit(){
            this.currentDecimalPlace = LinkedLargeInteger.this.mostSignificantDigit;
            this.currentDecimalIndex = LinkedLargeInteger.this.biggestDecimalPlace;
        }
        
        private void gotoLeastSignificantDigit(){
            this.currentDecimalPlace = LinkedLargeInteger.this.sign.next;
            this.currentDecimalIndex = 0;
        }
        

    }
    
    private class LargeIntegerBuilder extends LinkedLargeInteger{
        private Node sign;
        private Node mostSignificantDigit;
        private int biggestDecimalPlace;
        private IntegerIterator iter;
        
        private LargeIntegerBuilder(){
            this.sign = new Node(0, null, new Node(0, this.sign));
            this.mostSignificantDigit = this.sign.next;
            this.biggestDecimalPlace = 0;
            this.iter = new IntegerIterator();
        }
        
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
        
        private boolean setDigitAtDecimalPlace(Integer digit, int decimalPlace){
            if (!(decimalPlace >= 0)){
                throw new IllegalArgumentException("Decimal places are greater than or equal to zero: " + decimalPlace);
            }
            while (decimalPlace > this.iter.currentDecimalIndex){
                if (!this.iter.hasNext()){
                    if (digit == 0){return true;}//No need extending the number since they will all be leading zeros
                    this.iter.currentDecimalPlace.next = new Node(0, this.iter.currentDecimalPlace);
                }
                this.iter.next();
            }
            while (decimalPlace < this.iter.currentDecimalIndex){
                if (this.iter.currentDecimalIndex >  0){
                    this.iter.previous();
                }
            }
            if (decimalPlace == this.iter.currentDecimalIndex){
                if (decimalPlace > this.biggestDecimalPlace){
                    this.biggestDecimalPlace = decimalPlace;
                    this.mostSignificantDigit = this.iter.currentDecimalPlace;
                }
                this.iter.currentDecimalPlace.data = digit;
                return true;
            }
            else {return false;}//Number not set for unknown reason    
        }
        
        private void setSign(int signedNumber){
            if (signedNumber == 0){
                this.clear();
            }
            else if (signedNumber > 0){this.sign.data = 1;}
            else if (signedNumber < 0){this.sign.data = -1;}
        }
        
        private void clear(){
            this.sign = new Node(0, null, new Node(0, this.sign));
            this.mostSignificantDigit = this.sign.next;
            this.biggestDecimalPlace = 0;
        }
        
        private LinkedLargeInteger copy(){
            return new LinkedLargeInteger(this);
        }
    }
    
    
    
}
