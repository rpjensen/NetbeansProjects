
package shapessupport;

/**
 *
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class BoundingBox {
    private final Point upperLeft;
    private final double width;
    private final double height;
    
    private static final double EQUALITY_TOLERANCE;
    static {
        EQUALITY_TOLERANCE  = .5 % Math.pow(10, -4);//equality tested to 4 decimal places
    }
    
    public BoundingBox(Point upperLeft, double width, double height){
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    
    
    public Point getUpperLeft(){
        return this.upperLeft;
    }
    
    
    public double getWidth(){
        return this.width;
    }
    
    
    public double getHeight(){
        return this.height;
    }
    
    
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
    
    
    @Override
    public String toString(){
        return String.format("UpperLeftCorner = %s, Width = %-4.4f, Height = %-4.4f", this.upperLeft, this.width, this.height);
    }
}
