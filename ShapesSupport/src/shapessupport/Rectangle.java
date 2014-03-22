

package shapessupport;

import java.awt.Color;

/**
 * A class that represents a Rectangle and extends a drawing figure.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class Rectangle extends Figure{
    
    /**
     * Creates and initializes a new rectangle with the given parameters.
     * Note the rectangle is invisible until that property is changed.
     * @param xCoord the x coordinate of the upper left corner
     * @param yCoord the y coordinate of the upper left corner
     * @param width the width
     * @param height the height
     * @param color  the color
     */
    public Rectangle(double xCoord, double yCoord, double width, double height, Color color){
        super(new BoundingBox(new Point(xCoord, yCoord), width, height), color, false);
    }
    
    /**
     * @return the x coordinate of the upper left corner
     */
    public double getXCoord(){
        return this.boundingBox.getUpperLeft().getXCoord();
    }
    
    /**
     * @return the y coordinate of the upper left corner
     */
    public double getYCoord(){
        return this.boundingBox.getUpperLeft().getYCoord();
    }
    
    /**
     * @return the width
     */
    public double getWidth(){
        return this.boundingBox.getWidth();
    }
    
    /**
     * @return the height
     */
    public double getHeight(){
        return this.boundingBox.getHeight();
    }
    
    /**
     * Computes the area of the rectangle
     * @return the area
     */
    @Override
    public double area(){
        return this.boundingBox.getWidth() * this.boundingBox.getHeight();
    }
    
    /**
     * Tests the equality of two rectangles.
     * @param object The rectangle to test against
     * @return True if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Rectangle)){
            return false;
        }
        return super.equals(object);
    }
    
    /**
     * Returns a string representation, "Rectangle, figure"
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("Rectangle: %s", super.toString());
    }
}
