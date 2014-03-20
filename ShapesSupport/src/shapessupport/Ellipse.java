

package shapessupport;

import java.awt.Color;

/**
 * A class that represents an ellipse by extending the figure class.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class Ellipse extends Figure{
    
    
    private static BoundingBox getBoundingBoxFromCenter(double centerX, double centerY, double semiWidth, double semiHeight){
        Point upperLeft = new Point(centerX - semiWidth, centerY - semiHeight);
        double width = 2 * semiWidth;
        double height = 2 * semiHeight;
        return new BoundingBox(upperLeft, width, height);
    }
    
    public Ellipse(double centerX, double centerY, double horizontalRadius, double verticalRadius, Color color){
        super(Ellipse.getBoundingBoxFromCenter(centerX, centerY, horizontalRadius, verticalRadius), color, false);
    }
    
    public double getCenterX(){
        return this.boundingBox.getUpperLeft().getXCoord() + this.getHorizontalRadius();
    }
    
    public double getCenterY(){
        return this.boundingBox.getUpperLeft().getYCoord() + this.getVerticalRadius();
    }
    
    public double getHorizontalRadius(){
        return this.boundingBox.getWidth() / 2;
    }
    
    public double getVerticalRadius(){
        return this.boundingBox.getHeight() / 2;
    }
    
    @Override
    public double area(){
        return Math.PI * this.getHorizontalRadius() * this.getVerticalRadius();
    }
    
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
    
    @Override
    public String toString(){
        return String.format("Ellipse: %s", super.toString());
    }
}
