package bugcrawlviewer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Emma L. Anderson
 */
public class Bug {

    // instant variables 
    private double locationX;
    private double locationY;
    private double bugRadius;
    private double direction;
    private Color bugColor;

    public Bug(int x, int y, int radius, Color bugColor) {
        locationX = x - radius;
        locationY = y - 2 * radius;
        bugRadius = radius;
        this.bugColor = bugColor;
        direction = 1;
    }//constructor

    public void draw(Graphics2D drawItem) {
        Ellipse2D.Double bug = new Ellipse2D.Double(locationX, locationY, 2 * bugRadius, 2 * bugRadius);
        drawItem.setColor(bugColor);
        drawItem.fill(bug);
    }

    public double getX() {
        return locationX;
    }

    public double getY() {
        return locationY;
    }

    public void move() {
        locationX = locationX + direction;
    }

    public void turn() {
        direction = -1 * direction;
    }
}//bugclass