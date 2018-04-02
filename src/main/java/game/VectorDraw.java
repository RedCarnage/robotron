package game;

import java.awt.Color;


import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import baseframework.BaseGame;

public class VectorDraw extends BaseGame {

	class VectorPoint {
		int x, y;

		public VectorPoint(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3794829568742190962L;
	private int cursorX, cursorY;
	private int screenWidth;
	private int screenHeight;
	
	private boolean recordingShape = false;
	private List<VectorPoint> points = new ArrayList<>();
	private List<Polygon> shapes = new ArrayList<>();
	
	public VectorDraw(String title, int width, int height) {
		super(title, width, height);
		
		this.screenWidth = width;
		this.screenHeight = height;
		
		cursorX = width/2;
		cursorY = height/2;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new VectorDraw("Vector draw", 640+10, 480+30);
			}
		});	
	}

	@Override
	public void updateGame(long interval) {
		// TODO Auto-generated method stub
		
	}

	private int cursorLength = 5;
	@Override
	public void renderGame(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, screenWidth, screenHeight);
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("recording = " + recordingShape, 40, 20);

		//Show points
		for(VectorPoint point : points) {
			g2d.drawLine(point.x, point.y, point.x, point.y);
		}
		g2d.setColor(Color.RED);
		for(Shape shape : shapes) {
			g2d.draw(shape);
		}
//		g2d.drawLine(cursorX-cursorLength, cursorY, cursorX+cursorLength, cursorY);
//		g2d.drawLine(cursorX, cursorY-cursorLength, cursorX, cursorY+cursorLength);
	}

	@Override
	public void mousePosition(int x, int y) {
		// TODO Auto-generated method stub
		cursorX = x;		
		cursorY = y;		
	}

	@Override
	public void mouseButtonPress(int button) {
		System.out.println("button pressed = " + button);
		if(recordingShape) {
			points.add(new VectorPoint(cursorX, cursorY));
		}
	}

	@Override
	public void mouseButtonRelease(int button) {
		System.out.println("button release = " + button);
	}

	@Override
	public void keyboardPressed(int key) {
		
	}

	@Override
	public void keyboardReleased(int key) {
		if(key==32) {
			recordingShape ^= true;
			
			if(recordingShape==false) {
				saveShape();
			}
		}
	}

	private void saveShape() {
		Polygon shape = new Polygon();
		
		for(VectorPoint point : points) {
			shape.addPoint(point.x, point.y);
		}
		shapes.add(shape);		
		points.clear();
	}

}
