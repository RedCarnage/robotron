package interfaces;

import java.awt.Graphics2D;

/**
 * Used by BaseGame to provide how to uppdate a implementation.
 * 
 * @author Carl Stika
 *
 */
public interface GameUpdate {
	public void updateGame(long interval);
	public void renderGame(Graphics2D g2d);
	
	public void mousePosition(int x, int y);
	public void mouseButtonPress(int buttons);
	public void mouseButtonRelease(int buttons);
	
	
	public void keyboardPressed(int key);
	public void keyboardReleased(int key);

}
