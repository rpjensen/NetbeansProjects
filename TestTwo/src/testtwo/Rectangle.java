/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testtwo;

/**
 *
 * @author Ryan
 */
public class Rectangle {
    private double length = 0;
    private double width = 0;
    //constuctors
    Rectangle(){
        
    }//default constructor
    Rectangle(double initialLength, double initialWidth){
        length= initialLength;
        width = initialWidth;
    }//other constuctor
    
    //mutator methods
    public void setLength(double initialLength){
        length = initialLength;
    }
    public void setWidth(double initialWidth){
        width = initialWidth;
    }
    
    //accessor methods
    public double getWidth(){
        return width;
    }
    public double getLength(){
        return length;
    }
    
    //member methods
    public double area(){
        return width*length;
    }
    public double perimeter(){
        return 2*length+2*width;
    }
}//class
