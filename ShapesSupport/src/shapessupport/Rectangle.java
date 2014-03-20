

package shapessupport;

import java.awt.Color;

/**
 * A class that represents a Rectangle and extends a drawing figure.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class Rectangle extends Figure{
    
    public Rectangle(double xCoord, double yCoord, double width, double height, Color color){
        super(new BoundingBox(new Point(xCoord, yCoord), width, height), color, false);
    }
    
    public double getXCoord(){
        return this.boundingBox.getUpperLeft().getXCoord();
    }
    
    public double getYCoord(){
        return this.boundingBox.getUpperLeft().getYCoord();
    }
    
    public double getWidth(){
        return this.boundingBox.getWidth();
    }
    
    public double getHeight(){
        return this.boundingBox.getHeight();
    }

    @Override
    public double area(){
        return this.boundingBox.getWidth() * this.boundingBox.getHeight();
    }
    
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
    
    @Override
    public String toString(){
        return String.format("Rectangle: %s", super.toString());
    }
}
