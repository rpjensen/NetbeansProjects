
package shapessupport;

/**
 * A class that represents a single immutable point
 * @author Ryan Jensen
 * @version April 2, 2014
 */
public class Point {
    private final double xCoord;
    private final double yCoord;
    
    private static final double EQUALITY_TOLERANCE;
    static {
        EQUALITY_TOLERANCE  = .5 % Math.pow(10, -4);//equality tested to 4 decimal places
    }
    
    
    public Point(double xCoord, double yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
    
    
    public double getXCoord(){
        return this.xCoord;
    }
    
    
    public double getYCoord(){
        return this.yCoord;
    }
    
    
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Point)){
            return false;
        }
        Point point = (Point)object;
        return (Math.abs(this.xCoord - point.xCoord) <= Point.EQUALITY_TOLERANCE) && (Math.abs(this.yCoord - point.yCoord) <= Point.EQUALITY_TOLERANCE);
    }
    
    
    @Override
    public String toString(){
        return String.format("{x = %-4.4f, y = %-4.4f", this.xCoord, this.yCoord);
    }
}
