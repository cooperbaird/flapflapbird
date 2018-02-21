package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import components.Ground;

/**
 * GUI for the Flap Flap Bird Game
 * 
 * @author cooperbaird
 */
public class FlapFlapBird extends JFrame implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private Engine engine;
	
	public FlapFlapBird() {
		engine = new Engine();
		
		setSize(650, 500);
		setTitle("Flap Flap Bird");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);

		add(new GamePanel());
		setVisible(true);
		animate();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SPACE) {
			engine.jump();
		} else if(key == KeyEvent.VK_ESCAPE) {
			if(engine.getPostGame()) {
				engine.resetGame();
				animate();
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		engine.jump();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	private void animate() {
		Thread animate = new Thread() {
			public void run() {
				while(!engine.getPostGame()) {
					engine.gameMovement();
					repaint();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) { 
						e.printStackTrace(); 
					}
				}
			}
		};
		
		Thread flapWings = new Thread() {
			public void run() {
				while(!engine.getPostGame()) {
					engine.flapWings();
					try { 
						Thread.sleep(175); 
					} catch (InterruptedException e) { 
						e.printStackTrace(); 
					}
				}
			}
		};
		
		animate.start();
		flapWings.start();
	}
	
	/**
	 * The GamePanel where everything is painted on
	 * 
	 * @author cooperbaird
	 */
	private class GamePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Font smallFont, largeFont;
		private BufferedImage background, ground, bottom, birdBody, pipe;
		
		public GamePanel() {
			setDoubleBuffered(true); // buffer panel off-screen first and then paint to avoid flickering
			
			try {
				smallFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/OCRAStd.ttf"));
				largeFont = smallFont.deriveFont(36.0f);
				smallFont = smallFont.deriveFont(18.0f);
				
				background = ImageIO.read(getClass().getResource("/resources/background.jpg"));
				ground = ImageIO.read(getClass().getResource("/resources/ground.jpg"));
				bottom = ImageIO.read(getClass().getResource("/resources/bottom.jpg"));
				birdBody = ImageIO.read(getClass().getResource("/resources/birdAnimated.png"));
				pipe = ImageIO.read(getClass().getResource("/resources/pipe.png"));
			} catch(IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(background, 0, 0, 650, 380, null);
			
			for(int p = 0; p < engine.getTopPipes().size(); p++) {
				engine.getTopPipes().get(p).paint(g, pipe);
				engine.getBottomPipes().get(p).paint(g, pipe);
			}
			
			engine.getBird().paint(g, birdBody);
			g.drawImage(bottom, 0, 400, 650, 204, null);
			
			for(Ground gnd : engine.getGrounds())
				gnd.paint(g, ground);
			
			g.setFont(smallFont);
			g.setColor(Color.BLACK);
			g.drawString("Score: " + engine.getScore(), 0, 15);
			
			if(engine.getPreGame()) {
				g.setFont(largeFont);
				g.drawString("Press Space or Click", 65, 140);
				g.drawString("to Begin", 215, 185);
				g.setFont(smallFont);
				g.drawString("High Score: " + engine.getHighScore(), 235, 230);
			}
			
			if(engine.getPostGame()) {
				g.setFont(largeFont);
				g.drawString("Game Over!", 190, 200);
				g.setFont(smallFont);
				g.drawString("Press Escape to Restart", 170, 240);
			}
		}
	}
	
	public static void main(String[] args) {
		new FlapFlapBird();
	}
}