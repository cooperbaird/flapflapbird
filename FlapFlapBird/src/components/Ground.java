package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Class representing a ground piece in the game
 * 
 * @author cooperbaird
 */
public class Ground {
	public int x;
	private static final int y = 380;
	private static final int width = 650;
	private static final int height = 20;
	
	/**
	 * @param x is the x location of the ground
	 */
	public Ground(int x) {
		this.x = x;
	}
	
	/**
	 * @param g the graphics object
	 * @param img the ground image
	 */
	public void paint(Graphics g, BufferedImage img) {
		g.drawImage(img, x, y, width, height, null);
	}
}