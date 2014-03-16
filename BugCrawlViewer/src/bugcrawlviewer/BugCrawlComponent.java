/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bugcrawlviewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author Emma L. Anderson
 * Last Modified:  March 7, 2014
 */
public class BugCrawlComponent extends JComponent{
    private static final double MINX = 0;
    private static final double MAXX = 420;
    Bug myBuggy;
    
    /**
     *   this constructor initializes bug object and sets the maxX value for bug to travel
     */
    public BugCrawlComponent(){
        myBuggy = new Bug(250,250, 50, Color.blue);
    }//BugCrawlComponent Constructor
    /**
     * Graphics for component
     * @param g -- graphics object used for canvas 
     */
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphObj = (Graphics2D)g;
        Line2D.Double bugBar = new Line2D.Double(0,250, 500,250);
        graphObj.setStroke(new BasicStroke(8));
        graphObj.setColor(Color.red);
        graphObj.draw(bugBar);        
        graphObj.setStroke(new BasicStroke(1));
        myBuggy.draw(graphObj);
    }//paintComponent
    
    /**
     * This method animates the **bug** traveling alone a line.
     */
    public void animate(){        
        while (true){//infinite loop
            double x = myBuggy.getX();
            double y = myBuggy.getY();
           
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(BugCrawlViewer.class.getName()).log(Level.SEVERE, null, ex);
            }//slow down the animation
            
            if ((x >= MAXX) || (x<=MINX)){
                myBuggy.turn();
            }//turn code
            myBuggy.move();
            this.repaint();
        }//while
    }//animate    
}//BugCrawlComponent
