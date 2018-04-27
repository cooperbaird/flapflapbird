package core;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.JPanel;

import components.Bird;
import components.Ground;
import components.Pipe;
import factory.GroundFactory;
import factory.PipeFactory;

/**
 * Flap Flap Bird's game engine
 * 
 * @author cooperbaird
 */
public class Engine {
	private JPanel panel;
	private Bird bird;
	private GroundFactory groundFactory;
	private PipeFactory pipeFactory;
	private ScoreOperations scoreOperations;
	private List<Ground> grounds;
	private List<Pipe> topPipes, bottomPipes;
	private boolean preGame, postGame;
	
	/**
	 * @param panel the panel to paint the game on
	 */
	public Engine(JPanel panel) {
		this.panel = panel;
		bird = new Bird();
		groundFactory = new GroundFactory();
		pipeFactory = new PipeFactory();
		scoreOperations = new ScoreOperations();
		grounds = groundFactory.getGrounds();
		topPipes = pipeFactory.getTopPipes();
		bottomPipes = pipeFactory.getBottomPipes();
		preGame = true;
		postGame = false;
	}
	
	public void animate() {
		Thread animate = new Thread() {
			@Override
			public void run() {
				while(!postGame) {
					gameMovement();
					panel.repaint();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) { 
						e.printStackTrace(); 
					}
				}
			}
		};
		
		Thread flapWings = new Thread() {
			@Override
			public void run() {
				while(!postGame) {
					flapWings();
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
	
	private void animateDeath() {
		Thread die = new Thread() {
			@Override
			public void run() {
				bird.setDeadValues();
				while(bird.getBirdY() < 500) {
					bird.moveBird();
					panel.repaint();
					try {
						Thread.sleep(5);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		die.start();
	}
	
	private void gameMovement() {
		groundFactory.moveGrounds();
		groundFactory.addGround();
		
		if(preGame) {
			bird.hover();
		} else if(!preGame) {
			pipeFactory.movePipes();
			pipeFactory.addPipes();
			bird.moveBird();
			collision();
			score();
		}
	}
	
	private void flapWings() {
		bird.flap();
	}
	
	private void collision() {
		double y = bird.getBirdY();
		if(y <= 0 || y >= 352) // hits top or bottom
			endGame();
		
		Ellipse2D bEllipse = bird.getBounds();
		Rectangle topPRect = topPipes.get(0).getBounds();
		Rectangle bottomPRect = bottomPipes.get(0).getBounds();
		
		if(bEllipse.intersects(topPRect) || bEllipse.intersects(bottomPRect)) // hits a pipe
			endGame();
	}
	
	private void endGame() {
		scoreOperations.editHighScore();
		postGame = true;
		animateDeath();
	}
	
	private void score() {
		int p = topPipes.get(0).x;
		if(p == 40) // gets past the leading pipe
			scoreOperations.increaseScore();
	}
	
	public void jump() {
		preGame = false;
		bird.resetValues();
	}
	
	public void resetGame() {
		preGame = true;
		postGame = false;
		pipeFactory.clearPipes();
		scoreOperations.resetScore();
	}
	
	/**
	 * @return the bird
	 */
	public Bird getBird() {
		return bird;
	}
	
	/**
	 * @return the List of grounds
	 */
	public List<Ground> getGrounds() {
		return grounds;
	}
	
	/**
	 * @return the List of top pipes
	 */
	public List<Pipe> getTopPipes() {
		return topPipes;
	}
	
	/**
	 * @return the List of bottom pipes
	 */
	public List<Pipe> getBottomPipes() {
		return bottomPipes;
	}
	
	/**
	 * @return the score int value
	 */
	public int getScore() {
		return scoreOperations.getScore();
	}
	
	/**
	 * @return the high score int value
	 */
	public int getHighScore() {
		return scoreOperations.getHighScore();
	}
	
	/**
	 * @return the boolean for preGame
	 */
	public boolean getPreGame() {
		return preGame;
	}
	
	/**
	 * @return the boolean for postGame
	 */
	public boolean getPostGame() {
		return postGame;
	}
}