/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bugcrawlviewer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Emma L. Anderson
 * Last Modified: March 7, 2014
 */
public class BugCrawlViewer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        frame.setSize(500, 500);
        frame.setTitle("Exam2: Buggy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BugCrawlComponent myBug = new BugCrawlComponent();
        frame.add(myBug);
        frame.setVisible(true);
        
        myBug.animate();
    }//main
    
          
    
}//BugCrawlViewer
