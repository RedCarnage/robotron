package interfaces;

import java.awt.Graphics2D;

public interface ScreenWipe {
	
	public void render(Graphics2D g2d);
	public int update();  //returns a number between 0-100. Percent done
	
}
