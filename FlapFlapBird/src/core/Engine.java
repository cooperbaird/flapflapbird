package core;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.List;

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
	private Bird bird;
	private GroundFactory groundFactory;
	private PipeFactory pipeFactory;
	private ScoreOperations scoreOperations;
	private List<Ground> grounds;
	private List<Pipe> topPipes, bottomPipes;
	private boolean preGame, postGame;
	
	public Engine() {
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
	
	public void gameMovement() {
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
	
	public void flapWings() {
		bird.flap();
	}
	
	private void collision() {
		double y = bird.getBirdY();
		if(y <= 0 || y >= 352) // hits top or bottom
			endGame();
		
		Ellipse2D bEllipse = bird.getBounds();
		
		for(int p = 0; p < topPipes.size(); p++) {
			Rectangle topPRect = topPipes.get(p).getBounds();
			Rectangle bottomPRect = bottomPipes.get(p).getBounds();
			if(bEllipse.intersects(topPRect) || bEllipse.intersects(bottomPRect)) // hits pipes
				endGame();
		}
	}
	
	private void endGame() {
		scoreOperations.editHighScore();
		postGame = true;
	}
	
	private void score() {
		for(Pipe p : topPipes) {
			if(p.x == 99)
				scoreOperations.increaseScore();
		}
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