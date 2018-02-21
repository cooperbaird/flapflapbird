package factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.Pipe;

/**
 * Factory for managing the pipe pieces on screen
 * 
 * @author cooperbaird
 */
public class PipeFactory {
	private List<Pipe> topPipes, bottomPipes;
	private static final Random rand = new Random();
	
	public PipeFactory() {
		topPipes = new ArrayList<>();
		bottomPipes = new ArrayList<>();
		addPipes();
	}
	
	/**
	 * @return a randomized y location between 160 and 300
	 */
	private int yLocation() {
		return rand.nextInt((300 - 160) + 1) + 160;
	}
	
	public void movePipes() {
		for(int i = 0; i < topPipes.size(); i++) {
			topPipes.get(i).x--;
			bottomPipes.get(i).x--;
			
			if(topPipes.get(i).x <= -60) {
				topPipes.remove(i);
				bottomPipes.remove(i);
			}
		}
	}
	
	public void addPipes() {
		if(topPipes.isEmpty() || topPipes.get(topPipes.size() - 1).x == 500) {
			int y1 = yLocation();
			int y2 = y1 - 322;
			bottomPipes.add(new Pipe(700, y1));
			topPipes.add(new Pipe(700, y2));
		}
	}
	
	public void clearPipes() {
		topPipes.clear();
		bottomPipes.clear();
		addPipes();
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
}