package factory;

import java.util.ArrayList;
import java.util.List;

import components.Ground;

/**
 * Factory for managing the ground pieces on-screen
 * 
 * @author cooperbaird
 */
public class GroundFactory {
	private List<Ground> grounds;
	
	public GroundFactory() {
		grounds = new ArrayList<>();
		grounds.add(new Ground(0));
	}
	
	public void moveGrounds() {
		for(int i = 0; i < grounds.size(); i++) {
			grounds.get(i).x--;
			if(grounds.get(i).x <= -650)
				grounds.remove(i);
		}
	}
	
	public void addGround() {
		if(grounds.get(grounds.size() - 1).x == -1)
			grounds.add(new Ground(649));
	}
	
	/**
	 * @return the List of Ground objects
	 */
	public List<Ground> getGrounds() {
		return grounds;
	}
}