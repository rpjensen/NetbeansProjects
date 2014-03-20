

package shapessupport;

import java.awt.Color;

/**
 * A class that represents a circle by extending 
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public class Circle extends Ellipse{
    
    public Circle(double centerX, double centerY, double radius, Color color){
        super(centerX, centerY, radius, radius, color);
        //I am aware that the directions said to intialize with the upper left corner
        //as the points but I think if the application is used for drawing, the most
        //logical way to conceptualize drawing circles for client code is using the center
        //as the client defined point.  Also there is the fact that the super class initlilzer
        //is also expecting the shape to be defined by the center.
    }
    
    public double getRadius(){
        return this.getHorizontalRadius();
    }
    
    /*
     * In the same way as the square, I believe an ellipse with an equal bounding
     * box and color as a circle they should be equal.  Thus the only part of this
     * equals method that is different from super's is the instanceof Circle.
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Circle)){
            return false;
        }
        return super.equals(object);
    }*/
}
