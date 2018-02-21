package core;

import java.util.prefs.Preferences;

/**
 * Class for managing the current and high scores
 * 
 * @author cooperbaird
 */
public class ScoreOperations {
	private int score, highScore;
	private Preferences prefs;
	
	public ScoreOperations() {
		score = 0;	
		prefs = Preferences.userNodeForPackage(ScoreOperations.class);
		highScore = prefs.getInt("flapflapbird_highscore", 0);
	}
    
    public void editHighScore() {
        if(score > highScore) {
        		highScore = score;
        		prefs.putInt("flapflapbird_highscore", highScore);
        }
    }
    
    public void increaseScore() {
    		score++;
    }
    
    public void resetScore() {
    		score = 0;
    }
    
    /**
     * @return the integer value for the player's score
     */
    public int getScore() {
    		return score;
    }
    
    /**
     * @return the integer value for the player's high score
     */
    public int getHighScore() {
    		return highScore;
    }
}