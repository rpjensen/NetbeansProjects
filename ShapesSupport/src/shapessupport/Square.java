

package shapessupport;

import java.awt.Color;

/**
 * A class that represents a square by extending a rectangle.  This is merely a 
 * convenience class for defining a rectangle with equal sides.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class Square extends Rectangle{
    
    public Square(double xCoord, double yCoord, double length, Color color){
        super(xCoord, yCoord, length, length, color);
    }
    
    public double getLength(){
        return this.getWidth();
    }
    
   /*
    * I don't think that square needs to override rectangles equals method.
    * Just because we didn't define a rectangle to be of the class square doesn't
    * mean that it isn't a rectangle with equal sides thus being a square.
    * So I think that a rectangle should be equal to a square if their bounding box
    * is equal and their color is equal.  Thus the only difference between this and
    * super's equals method is the instanceof Square.
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Square)){
            return false;
        }
        return super.equals(object);
    }*/
}
