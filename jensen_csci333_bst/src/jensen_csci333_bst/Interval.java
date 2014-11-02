

package jensen_csci333_bst;

/**
 * Represents an inclusive interval
 * @author Ryan Jensen
 * @version October 27, 2014
 */
public class Interval {
    private final int lower;
    private final int upper;
    
    public Interval(int lower, int upper){
        if (lower > upper){throw new IllegalArgumentException("lower should be less than or equal to upper");}
        this.lower = lower;
        this.upper = upper;
    }
    
    public int lower(){
        return lower;
    }
    
    public int upper(){
        return upper;
    }
    
    @Override
    public String toString(){
        return String.format("[%d, %d]", lower, upper);
    }
    
    public boolean intersects(Interval other){
        //if other.lower is between this.lower && this.upper
        //or other.upper is between this.lower && this.upper
        return !(this.upper < other.lower || other.upper < this.lower);
    }
    

}
