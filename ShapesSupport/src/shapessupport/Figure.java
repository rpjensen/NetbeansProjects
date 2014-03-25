

package shapessupport;

import java.awt.Color;

/**
 * An abstract drawing figure.  The figure is contained in a bounding box, has
 * color, and can be either visible or invisible.
 * @author Ryan Jensen
 * @version Mar 19, 2014
 */
public abstract class Figure {
    protected BoundingBox boundingBox;
    protected Color color;
    protected boolean isVisible;
    
    /**
     * Initialize a new figure. (Must be called in subclass)
     * @param boundingBox the bounding box that defines the size of the figure
     * @param color the color of the figure
     * @param isVisible the visibility status of the figure
     */
    public Figure(BoundingBox boundingBox, Color color, boolean isVisible){
        this.boundingBox = boundingBox;
        this.color = color;
        this.isVisible = isVisible;
    }
    
    /**
     * Make the figure visible
     */
    public void makeVisible(){
        this.isVisible = true;
    }
    
    /**
     * Make the figure invisible
     */
    public void makeInVisible(){
        this.isVisible = false;
    }
    
    /**
     * Set a new color.
     * @param color the new color
     */
    public void setColor(Color color){
        this.color = color;
    }
    
    /**
     * Must be implemented in subclass
     * @return the area of the shape inside the figure
     */
    public abstract double area();
    
    /**
     * Scale the figure by the given dilation factor while maintaining the original
     * aspect ratio.  Negative numbers could invert the image in a future program but
     * currently have the same effect as positive numbers because the figure doesn't
     * know how to change the image inside of it.
     * @param dilationFactor the factor to scale the figure by 
     */
    public void dilate(double dilationFactor){
        BoundingBox tempBox = new BoundingBox(this.boundingBox.getUpperLeft(), this.boundingBox.getWidth() * dilationFactor, this.boundingBox.getHeight() * dilationFactor);
        this.boundingBox = tempBox;
    }
    
    /**
     * Translate the figure by the given distances.
     * @param xDisplacement the x displacement (x>0 right, x<0 left)
     * @param yDisplacement the y displacement (y>0 down, y<0 up)
     */
    public void translate(double xDisplacement, double yDisplacement){
        double newX = this.boundingBox.getUpperLeft().getXCoord() + xDisplacement;
        double newY = this.boundingBox.getUpperLeft().getYCoord() + yDisplacement;
        BoundingBox tempBox = new BoundingBox(new Point(newX, newY), this.boundingBox.getWidth(), this.boundingBox.getHeight());
        this.boundingBox = tempBox;
    }
    
    /**
     * Tests the equality of two figures.  A figure doesn't know about the shape inside
     * the figure.  To test whether two shapes differ us a most specific equality
     * test. (Note equality is not determined by visibility)
     * @param object the figure to test against
     * @return True if equal, else false
     */
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
    
    /**
     * Creates a string representation of the figure. "BoundingBox, Color, Visibility"
     * @return the string representation
     */
    @Override
    public String toString(){
        return String.format("%s Color(rgb):%3d,%3d,%3d ,%s", this.boundingBox.toString(), this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.isVisible ? "Visible" : "Invisible");
    }
}
