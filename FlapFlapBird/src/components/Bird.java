package components;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * The class representing the bird in the game
 * 
 * @author cooperbaird
 */
public class Bird {
	private int birdY, birdImageX;
	private double time, vertSpeed, angle;
	private static final int birdX = 100;
	private static final int width = 40;
	private static final int height = 28;
	private static final double jumpSpeed = 1.6;
	private static final double fallingConstant = 0.5;
	
	public Bird() {
		birdImageX = 0;
		time = 0;
		vertSpeed = 0;
		angle = 0;
	}
	
	public void resetValues() {
		time = 0;
		vertSpeed = jumpSpeed;
	}
	
	public void hover() {
		birdY = (int) (220 + 12*Math.sin(Math.toRadians(angle)));
		angle++;
		if(angle > 360) angle = 0;
	}
	
	/**
	 * Jumping physics inspired by 1st answer to this question:
	 * https://gamedev.stackexchange.com/questions/70268/can-someone-explain-flappy-birds-physics-to-me
	 */
	public void moveBird() {
		time += 0.005;
		birdY -= vertSpeed * time;
		vertSpeed -= fallingConstant * time;
	}
	
	public void flap() {
		birdImageX += 36;
		if(birdImageX > 72) birdImageX = 0;
	}
	
	/**
	 * @return the value for the bird's y location
	 */
	public int getBirdY() {
		return birdY;
	}
	
	/**
	 * @return an ellipse with the bounds of the bird
	 */
	public Ellipse2D getBounds() {
		return new Ellipse2D.Double(birdX, birdY, width, height);
	}
	
	/**
	 * @param g the bird's graphics object
	 * @param img the BufferedImage of the bird
	 */
	public void paint(Graphics g, BufferedImage img) {
		g.drawImage(img, birdX, birdY, birdX + width, birdY + height, birdImageX, 0, birdImageX + 34, 24, null);
	}
}