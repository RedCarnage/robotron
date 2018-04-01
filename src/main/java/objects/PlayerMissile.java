package objects;

import java.awt.Graphics2D;
import java.util.List;

import baseframework.SpriteBase;
import enums.ObjectTypes;
import interfaces.DrawObject;

/**
 * Player Missile
 * 
 * @author Carl Stika
 *
 * Logic for the player missiles
 */
public class PlayerMissile implements DrawObject {
	public static final float missleLength = 10;
	public static final float missleWidth = 1;
	
	private VectorObject missile = new VectorObject();
	private float x, y;
	
	private float dirX, dirY;
	private long lastTime;
	
	private boolean dead = false;
	private int numSteps = 100;
	
	private int colorIndex = 0;
	private float[][] colorPallette = { {228.0f/225.0f, 236.0f/225.0f, 7.0f/225.0f, 1.0f},
										{1.0f, 1.0f, 1.0f, 1.0f},
										{0.80f, 0.20f, 0.15f, 1.0f},
										{0.15f, 0.80f, 0.15f, 1.0f},
										{0.20f, 0.20f, 0.80f, 1.0f}
									  };
	
	
	/**
	 * 
	 * @param sx - x position of missile start
	 * @param sy - y position of missile start
	 * @param dx - x direction missile is travling
	 * @param dy - y direction missile is traveling
	 */
	public PlayerMissile(float sx, float sy, float dx, float dy) {
		float len = (float)Math.sqrt(dx*dx+dy*dy);
		dx /= len;
		dy /= len;
		
		dirX =dx; 
		dirY =dy; 
		x = sx;
		y = sy;
		
		float ex = 0 + dirX*missleLength;
		float ey = 0 + dirY*missleLength;

		missile.addLine(0, 0, ex, ey, missleWidth);
		missile.finish();
		
		float color[] = colorPallette[colorIndex];
		missile.setColor(color);
		missile.updatePos(x, y);
		
		colorIndex = (colorIndex+1)%colorPallette.length;
		
		lastTime = System.currentTimeMillis();
	}
	
	
	@Override
	public void render(Graphics2D g2d) {
		missile.render(g2d);
	}

	@Override
	public boolean isDead() {
		return dead;
	}


	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		long difftime = System.currentTimeMillis() - lastTime;
		
		if(numSteps>0) {
			//check if missle hits a npc
			//We will need hit points
			float missileBoxWidth = dirX*(missleLength);
			float missileBoxHeight = dirY*(missleLength);
			float[] missileDir = {dirX, dirY};
			
			for(DrawObject obj : npcObjects) {
				if(	obj.getObjectType()!=ObjectTypes.OBJECT_TYPE_UNKOWN && 
					obj.getObjectType()!=ObjectTypes.OBJECT_TYPE_MAN &&
					obj.getObjectType()!=ObjectTypes.OBJECT_TYPE_WOMAN &&
					obj.getObjectType()!=ObjectTypes.OBJECT_TYPE_MIKEY &&
					obj.getObjectType()!=ObjectTypes.OBJECT_TYPE_PLAYER) {
							
					if( x<=(obj.getPosX()+obj.getWidth()) &&
						(x+missileBoxWidth)>=obj.getPosX() &&  
						y<=(obj.getPosY()+obj.getHeight()) &&
						(y+missileBoxHeight)>=obj.getPosY()					
						) {
						//change this to subtract hit points
						obj.objectHit(1, missileDir);
	
						//kill the missile
						dead = true;
						numSteps = 0;
						break;
					}
				}
			}
			
			if(!dead && difftime>=10) {
				x += dirX*(missleLength);
				y += dirY*(missleLength);
				
				//Need to check for collision
				missile.updatePos(x, y);
				
				numSteps--;
				lastTime = System.currentTimeMillis();
			}
		}
		else {
			dead = true;
		}
	}


	@Override
	public ObjectTypes getObjectType() {
		return ObjectTypes.OBJECT_TYPE_PLAYER_MISSLE; 
	}


	@Override
	public float getPosX() {
		return x;
	}


	@Override
	public float getPosY() {
		return y;
	}


	@Override
	public float getWidth() {
		return Math.min(Math.abs(dirX*missleLength), Math.abs(dirY*missleLength));
	}


	@Override
	public float getHeight() {
		return Math.min(Math.abs(dirY*missleLength), Math.abs(-dirX*missleLength));
	}


	@Override
	public void setDead() {
		dead = true;
	}


	@Override
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
		//this object can't be hit.
	}


	@Override
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

}
