package gameoflife;

/**
* Class LifeWindow draws the cell display.
* @author Dr. Charles Sheaffer
* @version 1/22/14
*
*/

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class LifeWindow extends JFrame {

	private int [][] world;
	private int scale;

/**
* Constructor
* @param world a two-dimensional array containing the cells
* @param scale number of pixels comprising the side of the box
*		to display for each cell
*/
	public LifeWindow(int [][] world, int scale) {
		this.world = world;
		this.scale = scale;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(world.length * scale, world[0].length * scale);
		this.setUndecorated(true);
		this.setVisible(true);
		this.createBufferStrategy(2);
	}
	
// Pauses the cell display so the animation is more visible.
/**
* Invoke this method to display a graphical image of the current state 
* of the automaton.
*/
	public void displayLife() {
		try { Thread.sleep(300); }
		catch (InterruptedException e) { }
                drawWorld();
	}
	

/*
* Redraws the cell display.
*/	
	private void drawWorld() {
		BufferStrategy strategy = this.getBufferStrategy();
		Graphics graphics = strategy.getDrawGraphics();
		graphics.clearRect(0, 0, world.length*scale, world[0].length*scale);
		for (int i=0; i<world.length; i++) {
                    for (int j=0; j<world[i].length; j++) {
                        if (world[i][j] == 1){
                                graphics.fillRect(scale*i, scale*j, scale, scale);
                        }
                     }
                }
                graphics.dispose();
                strategy.show();
                    
                
    }
}