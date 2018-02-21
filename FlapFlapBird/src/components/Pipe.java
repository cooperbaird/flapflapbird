package components;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Class representing a pipe piece in the game
 * 
 * @author cooperbaird
 */
public class Pipe {
	private static final int width = 60;
	private static final int height = 220;
	public int x, y;

	/**
	 * @param x the x location of the pipe
	 * @param y the y location of the pipe
	 */
	public Pipe(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return a rectangle of the bounds of the pipe
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * @param g the graphics object
	 * @param pipe the pipe's image
	 */
	public void paint(Graphics g, BufferedImage pipe) {
		g.drawImage(pipe, x, y, width, height, null);
	}
	
}