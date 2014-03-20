

package shapessupport;

import java.awt.Color;

/**
 *
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public abstract class Figure {
    protected BoundingBox boundingBox;
    protected Color color;
    protected boolean isVisible;
    
    public Figure(BoundingBox boundingBox, Color color, boolean isVisible){
        this.boundingBox = boundingBox;
        this.color = color;
        this.isVisible = isVisible;
    }
    
    public void makeVisible(){
        this.isVisible = true;
    }
    
    public void makeInVisible(){
        this.isVisible = false;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public abstract double area();
    
    public void dilate(double dilationFactor){
        BoundingBox tempBox = new BoundingBox(this.boundingBox.getUpperLeft(), this.boundingBox.getWidth() * dilationFactor, this.boundingBox.getHeight() * dilationFactor);
        this.boundingBox = tempBox;
    }
    
    public void translate(double xDisplacement, double yDisplacement){
        double newX = this.boundingBox.getUpperLeft().getXCoord() + xDisplacement;
        double newY = this.boundingBox.getUpperLeft().getYCoord() + yDisplacement;
        BoundingBox tempBox = new BoundingBox(new Point(newX, newY), this.boundingBox.getWidth(), this.boundingBox.getHeight());
        this.boundingBox = tempBox;
    }
    
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (!(object instanceof Figure)){
            return false;
        }
        Figure figure = (Figure)object;
        return this.boundingBox.equals(figure.boundingBox) && this.color.equals(color);
        //I don't believe two figures that are otherwise equal should be unequal if one is showing
        //and the other is not.  That property doesn't seem to be part of the essance of the object.
    }
    
    @Override
    public String toString(){
        return String.format("%s Color(rgb):%3d,%3d,%3d Visible :%s", this.boundingBox.toString(), this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.isVisible ? "True" : "False");
    }
}
