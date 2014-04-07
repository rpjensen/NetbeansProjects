
package shapessupport;

/**
 * An immutable bounding box used to contain various shapes.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class BoundingBox {
    private final Point upperLeft;
    private final double width;
    private final double height;
    
    /**
     * Static initialization of the tolerance used to test equality.
     * EQUALITY_TOLERANCE is public so that other classes in the drawing program
     * can all use the same tolerance.
     */
    public static final int EQUALITY_TO_N_DECIMALS;
    public static final double EQUALITY_TOLERANCE;
    static {
        EQUALITY_TO_N_DECIMALS = 3;
        EQUALITY_TOLERANCE  = .5 % Math.pow(10, -EQUALITY_TO_N_DECIMALS);//equality tested to N decimal places
    }
    
    /**
     * Initialize a new bounding box with the upper left point the width and height.
     * @param upperLeft the upper left point
     * @param width the width of the box
     * @param height the height of the box
     */
    public BoundingBox(Point upperLeft, double width, double height){
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    
    /**
     * @return the upper left point 
     */
    public Point getUpperLeft(){
        return this.upperLeft;
    }
    
    /**
     * @return the width of the box 
     */
    public double getWidth(){
        return this.width;
    }
    
    /**
     * @return the height of the box 
     */
    public double getHeight(){
        return this.height;
    }
    
    /**
     * Tests the equality of two bounding boxes.
     * @param object The box to test against
     * @return True if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof BoundingBox)){
            return false;
        }
        BoundingBox boundingBox = (BoundingBox)object;
        return this.upperLeft.equals(boundingBox.upperLeft) && (Math.abs(this.width - boundingBox.width) <= BoundingBox.EQUALITY_TOLERANCE) && (Math.abs(this.height - boundingBox.height) <= BoundingBox.EQUALITY_TOLERANCE);
    }
    
    /**
     * Return a string representation of a box of the form "Point, Width, Height"
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("UpperLeftCorner = %s, Width = %-4.4f, Height = %-4.4f", this.upperLeft, this.width, this.height);
    }
}
