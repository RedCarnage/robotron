package baseframework;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import interfaces.GameUpdate;

public class GameCanvas extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8238692517089563281L;

	private boolean repaintInProgress = false;
	private GameUpdate gameUpdate;
	
	
	// this is a Canvas but I wont't let the system when to repaint it I will do it myself
	GameCanvas(GameUpdate gameUpdate) {
		this.gameUpdate = gameUpdate;
		
		// so ignore System's paint request I will handle them
		setIgnoreRepaint(true);
	}
	
	public void myRepaint() {
		
		// wasting too much time doing the repaint... ignore it
		if(repaintInProgress)
			return;
		// so I won't be called 2 times in a row for nothing
		repaintInProgress = true;
		
		gameUpdate.updateGame(16);
		
		// ok doing the repaint on the not showed page
		BufferStrategy strategy = getBufferStrategy();
		Graphics graphics = strategy.getDrawGraphics();
		gameUpdate.renderGame((Graphics2D)graphics);

		if(graphics != null)
			graphics.dispose();
		// show next buffer
		strategy.show();
		// synchronized the blitter page shown
		Toolkit.getDefaultToolkit().sync();

		// ok I can be called again
		repaintInProgress = false;
	}
}
