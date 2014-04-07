
package shapessupport;

/**
 * A class that represents a single immutable point
 * @author Ryan Jensen
 * @version Mar 21, 2014
 */
public class Point {
    private final double xCoord;
    private final double yCoord;
    
    /**
     * Initialize a new point with given x,y coordinates.
     * @param xCoord the new x coordinate
     * @param yCoord the new y coordinate
     */
    public Point(double xCoord, double yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
    
    /**
     * @return the x coordinate
     */
    public double getXCoord(){
        return this.xCoord;
    }
    
    /**
     * @return the y coordinate
     */
    public double getYCoord(){
        return this.yCoord;
    }
    
    /**
     * Tests the quality of two points.
     * @param object the point to test against
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Point)){
            return false;
        }
        Point point = (Point)object;
        return (Math.abs(this.xCoord - point.xCoord) <= BoundingBox.EQUALITY_TOLERANCE) && (Math.abs(this.yCoord - point.yCoord) <= BoundingBox.EQUALITY_TOLERANCE);
    }
    
    /**
     * Returns a string representation of a point. "{x,y}".
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("{x = %-4.4f, y = %-4.4f", this.xCoord, this.yCoord);
    }
}
