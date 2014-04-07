
package shapessupport;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Driver/tester class for programming assignment two
 * @author Ryan Jensen
 * @version Mar 22, 2014
 */
public class ShapesSupportDriver {

    /**
     * 0) Tests toString and getters throughout
     * 1) Creates shapes
     * 2) Tests setting the visibility
     * 3) Tests unsetting the visibility
     * 4) Tests against equal objects unequal references
     * 5) Tests against equal references
     * 6) Tests Translation
     * 7) Tests Dilation
     * 8) Tests changing the color
     */
    public static void main(String[] args) {
        ArrayList<Figure> figures = new ArrayList<>();
        figures.add(new Rectangle(0,0,100,200, Color.BLACK));
        figures.add(new Square(100, 150, 50, Color.RED));
        figures.add(new Ellipse(50, 150, 50, 100, Color.ORANGE));
        figures.add(new Circle(500, 400, 25, Color.PINK));
        
        for (int i = 0; i < figures.size(); i++){
            boolean expectedVisibility = (Math.random() < .5) ? true : false;
            if (expectedVisibility){
                figures.get(i).makeVisible();
            }
            System.out.printf("%s ---- %B\n", figures.get(i), expectedVisibility);
        }
        System.out.println();
        System.out.println();
        
        for (int i = 0; i < figures.size(); i++){
            figures.get(i).makeVisible();
            boolean expectedVisibility = (Math.random() < .5) ? true : false;
            if (expectedVisibility){
                figures.get(i).makeInVisible();
            }
            System.out.printf("%s ---- %s\n", figures.get(i), expectedVisibility ? "Invisible" : "Visible");
        }
        System.out.println();
        System.out.println();
        
        figures.add(new Rectangle(0,0,100,200, Color.BLACK));
        figures.add(new Square(100, 150, 50, Color.RED));
        figures.add(new Ellipse(50, 150, 50, 100, Color.ORANGE));
        figures.add(new Circle(500, 400, 25, Color.PINK));
        
        for (Figure figure : figures){
            System.out.println("-------------------");
            System.out.println(figure);
            System.out.println("-------------------");
            for (Figure otherFigure : figures){
                System.out.printf("%12s >>-------> %s\n", figure.equals(otherFigure) ? "Equal To" : "Not-Equal-To", otherFigure);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        
        ArrayList<Figure> otherFigures = new ArrayList<>();
        
        for (int i = figures.size()/2; i >= 0; i--){
            otherFigures.add(figures.get(i));
        }
        
        for (Figure figure : figures){
            System.out.println("-------------------");
            System.out.println(figure);
            System.out.println("-------------------");
            for (Figure otherFigure : otherFigures){
                System.out.printf("%12s >>-------> %s\n", figure.equals(otherFigure) ? "Equal To" : "Not-Equal-To", otherFigure);
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println();
        
        for (int i = 0; i < figures.size(); i++){
            double displacementX = 500 * Math.random() - 250;
            double displacementY = 500 * Math.random() - 250;
            double oldX = figures.get(i).boundingBox.getUpperLeft().getXCoord();
            double oldY = figures.get(i).boundingBox.getUpperLeft().getYCoord();
            
            figures.get(i).translate(displacementX, displacementY);
            double newX = figures.get(i).boundingBox.getUpperLeft().getXCoord();
            double newY = figures.get(i).boundingBox.getUpperLeft().getYCoord();
            System.out.printf("X: %.2f + %.2f = %.2f ,Y: %.2f + %.2f = %.2f\n", oldX, displacementX, newX, oldY, displacementY, newY);
        }
        
        System.out.println();
        System.out.println();
        
        for (int i = 0; i < figures.size(); i++){
            double dilationFactor = 4 * Math.random() - 2;
            double oldWidth = figures.get(i).boundingBox.getWidth();
            double oldHeight = figures.get(i).boundingBox.getHeight();
            
            figures.get(i).dilate(dilationFactor);
            double newWidth = figures.get(i).boundingBox.getWidth();
            double newHeight = figures.get(i).boundingBox.getHeight();
            System.out.printf("Width: %.2f * %.2f = %.2f, Height: %.2f * %.2f = %.2f \n", oldWidth, dilationFactor, newWidth, oldHeight, dilationFactor, newHeight);
        }
        
        System.out.println();
        System.out.println();
        
        for (int i = 0; i < figures.size(); i++){
            Color oldColor = figures.get(i).color;
            Color newColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            figures.get(i).setColor(newColor);
            System.out.printf("Old Color %s, New Color %s, Set Color %s\n", oldColor, newColor, figures.get(i).color);
        }
        
        
        
       
                
        
    }
}
