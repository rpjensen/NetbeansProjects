

package shapessupport;

import java.awt.Color;

/**
 * A class that represents an ellipse by extending the figure class.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class Ellipse extends Figure{
    
    /**
     * A static utility method to create a bounding box using the ellipses center
     * point, width radius, and height radius.  Because we have to call super() on
     * the first line of our constructor and figure needs the upper left point
     * this cuts down on doing all the computation on one line.
     */
    private static BoundingBox getBoundingBoxFromCenter(double centerX, double centerY, double semiWidth, double semiHeight){
        Point upperLeft = new Point(centerX - semiWidth, centerY - semiHeight);
        double width = 2 * semiWidth;
        double height = 2 * semiHeight;
        return new BoundingBox(upperLeft, width, height);
    }
    
    /**
     * Creates and initializes a new ellipse.  New ellipse are initialized to be
     * invisible.
     * @param centerX the x coordinate of the center
     * @param centerY the y coordinate of the center
     * @param horizontalRadius the horizontal semi-axis
     * @param verticalRadius the vertical semi-axis
     * @param color the color
     */
    public Ellipse(double centerX, double centerY, double horizontalRadius, double verticalRadius, Color color){
        super(Ellipse.getBoundingBoxFromCenter(centerX, centerY, horizontalRadius, verticalRadius), color, false);
    }
    
    /**
     * @return the x coordinate of the center of the ellipse 
     */
    public double getCenterX(){
        return this.boundingBox.getUpperLeft().getXCoord() + this.getHorizontalRadius();
    }
    
    /**
     * @return the x coordinate of the center of the ellipse
     */
    public double getCenterY(){
        return this.boundingBox.getUpperLeft().getYCoord() + this.getVerticalRadius();
    }
    
    /**
     * @return the length of the horizontal semi-axis 
     */
    public double getHorizontalRadius(){
        return this.boundingBox.getWidth() / 2;
    }
    
    /**
     * @return the length of the vertical semi-axis 
     */
    public double getVerticalRadius(){
        return this.boundingBox.getHeight() / 2;
    }
    
    /**
     * Computes the area of the enclosed ellipse.
     * @return the area
     */
    @Override
    public double area(){
        return Math.PI * this.getHorizontalRadius() * this.getVerticalRadius();
    }
    
    /**
     * Tests the equality of two ellipse
     * @param object the ellipse to test against
     * @return True if equal, else false
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Ellipse)){
            return false;
        }
        return super.equals(object);
    }
    
    /**
     * Return a string representation of an ellipse. Format "Ellipse: figure"
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("Ellipse: %s", super.toString());
    }
}
