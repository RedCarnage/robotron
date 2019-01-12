package baseframework;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import interfaces.GameUpdate;

/***
 * Extend this class to implement a basic game.
 * The allocation of the screen and mouse inputs will be provided for you.
 * 
 * @author Carl Stika
 *
 */
public abstract class BaseGame extends JFrame implements ActionListener, GameUpdate {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int fps = 60;
	GameCanvas canvas;
	
	public BaseGame(String title, int width, int height) {
		// frame description
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//need to stop the timer.
		
		// our Canvas
		canvas = new GameCanvas(this);
		add(canvas, BorderLayout.CENTER);
		// set it's size and make it visible
		setSize(width, height);
		setVisible(true);		
		this.setResizable(false);
		// now that is visible we can tell it that we will use 2 buffers to do the repaint
		// befor being able to do that, the Canvas as to be visible
		canvas.createBufferStrategy(2);
		canvas.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition(e.getX(), e.getY());
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseButtonRelease(e.getButton());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mouseButtonPress(e.getButton());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		canvas.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("key typed = " + e.getKeyChar());
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				keyboardReleased(e.getKeyCode());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				keyboardPressed(e.getKeyCode());
			}
		});
		
		// ask the chrono to calll me every 60 times a second so every 16 ms
		new Timer(1000/fps, this).start();

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		canvas.myRepaint();
	}
}
