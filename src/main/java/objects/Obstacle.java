package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.List;

import Effects.TronPalette;
import enums.ObjectTypes;
import enums.ObstacleTypes;
import interfaces.DrawObject;

public class Obstacle implements DrawObject {

	private Shape shape = null;
	private boolean dead = false;
	private int x, y;
	private int colorIndex = 0;
	private int ticks = 0;
	
	
	public Obstacle(ObstacleTypes type, int x, int y) {
		this.x = x;
		this.y = y;
		
		switch(type) {
			case OBSTACLE_STAR:
				Path2D.Float star = new Path2D.Float();
				star.moveTo(x+0,  y-5);
				star.lineTo(x+0,  y+5);
				star.moveTo(x-5,  y);
				star.lineTo(x+5,  y);
				star.moveTo(x-5,  y-5);
				star.moveTo(y+5,  y+5);
				
				shape = star;
				break;
			case OBSTACLE_SQUARE:
				shape = new Rectangle(x-5, y+5, 10, 10);
				break;
			case OBSTACLE_RIGHT_TRIANGLE:
				break;
			case OBSTACLE_RECTANGLE:
				break;
		
		}
		
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(TronPalette.colorArray[colorIndex]);
		
		g2d.draw(shape);
	}

	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		ticks++;
		
		if(ticks>=5) {
			ticks = 0;
			colorIndex = (colorIndex+1)%TronPalette.colorArray.length;
		}
		
	}

	@Override
	public ObjectTypes getObjectType() {
		// TODO Auto-generated method stub
		return ObjectTypes.OBJECT_TYPE_OBSTACLE;
	}

	@Override
	public void setPos(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPosX() {
		return x;
	}

	@Override
	public float getPosY() {
		return y;
	}

	//replace with a real check?
	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
		setDead();
	}

	@Override
	public void setDead() {
		dead = true;
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return dead;
	}


}
