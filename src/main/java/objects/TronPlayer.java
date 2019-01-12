package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import baseframework.SpriteBase;
import baseframework.SpriteIcon;
import enums.ObjectTypes;
import game.GlobalState;
import interfaces.DrawObject;
import sounds.TronSounds;

/**
 * Main Player class
 * 
 * @author Carl Stika
 *
 * 
 */

public class TronPlayer extends SpriteBase {

	private static final int PLAYER_LEFT = 0;
	private static final int PLAYER_RIGHT = 3;
	private static final int PLAYER_DOWN = 6;
	private static final int PLAYER_UP = 9;
	private int[][] spriteSheet = {
		{192, 163, 17, 26}, //player l1
		{216, 160, 19, 30}, //player l2
		{240, 163, 17, 25}, //player l3
		{264, 163, 17, 25}, //player r1
		{294, 159, 14, 30}, //player r2
		{320, 163, 17, 24}, //player r3
		{344, 161, 20, 28}, //player d1
		{371, 165, 22, 24}, //player d2
		{398, 161, 20, 30}, //player d3
		{424, 163, 17, 24}, //player u1
		{448, 162, 21, 28}, //player u2
		{472, 163, 25, 27}, //player u3
	};

	private long lastTime = System.currentTimeMillis();
	private int currentFrame = 0;
	private int frameoffset = 0;
	private boolean move = false; 
	private List<PlayerMissile> missleList = new ArrayList<>();
	private long lastTimeFiredMS = System.currentTimeMillis();
	private static final long timeBetweenFireMS = 100; //1/2 second
	private long lastTimeMovedMS = System.currentTimeMillis();
	private static final long  timeBetweenMovementMS = 0; //5;
	private static final float playerMovementMuliplyer = 5.0f; //5;
	
	private int numFamilyTaken = 0;
	
	private boolean playerHittable = false;
	private int playerInvincableTime = 60;

	public TronPlayer(BufferedImage spriteSheet) {
		super(spriteSheet);
		
		init();
	}

	private void init() {
		//place on the ground
		//Need a pivot point
		posX = 640.0f/2.0f;
		posY = 480.0f/2.0f;
		
		objectType = ObjectTypes.OBJECT_TYPE_PLAYER;
		width = spriteSheet[currentFrame+frameoffset][2];
	    height = spriteSheet[currentFrame+frameoffset][3];
	}
	
	public void clear() {
		posX = 640.0f/2.0f;
		posY = 480.0f/2.0f;
		missleList.clear();
	}

	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		if(playerInvincableTime>0) {
			playerInvincableTime--;
			if(playerInvincableTime==0) {
				playerHittable = true;
			}
		}
		
		//base this off of time. since we have a constant frame rate we could use ticks.
		int diffTime =(int)(System.currentTimeMillis() - lastTime);

		//Increment animation frame.
		if(diffTime>=(1000/6)) {
			if(move) {
				frameoffset = (frameoffset+1)%3;
			}
			lastTime = System.currentTimeMillis();

		}
		
		//Check missle collisions with NPC objects. 
		List<DrawObject> misslesToRemove = new ArrayList<>(); 
		for(PlayerMissile missle : missleList) {
			if(!missle.isDead()) {
				missle.update(npcObjects, newSprites);
			}
			else {
				//need to remove
				misslesToRemove.add(missle);
			}
		}
		
		missleList.removeAll(misslesToRemove);
		

	    /**
	     * Check for collisions with player
	     */
	    for(DrawObject obj : npcObjects) {
	    	if(obj!=this) {
	    		float objX = obj.getPosX();
	    		float objY = obj.getPosY();
	    		float objWidth = obj.getWidth();
	    		float objHeight = obj.getHeight();
	    		
			    if( posX <=(objX+objWidth) &&
						(posX+width)>=objX &&  
						posY<=(objY+objHeight) &&
						(posY+height)>=objY					
						) {
			    	SpriteBase icon =  null;
			    	switch(obj.getObjectType()) {
			    		case OBJECT_TYPE_MAN :
			    		case OBJECT_TYPE_WOMAN :
			    		case OBJECT_TYPE_MIKEY :
			    			obj.setDead(); //to get cleaned up
			    			int iconOffset = numFamilyTaken;
			    			if(iconOffset>4) {
			    				iconOffset = 4;
			    			}
			    			icon = new SpriteIcon(image, SpriteIcon.ICON_1000+iconOffset);
			    			icon.setPos(obj.getPosX(), obj.getPosY());
			    			numFamilyTaken++;
			    			TronSounds.getInstance().playPickupFamily();
			    			
			    			GlobalState.getInstance().addScore(1000*iconOffset);
			    			break;
			    		case OBJECT_TYPE_ENFORCERS:
			    		case OBJECT_TYPE_GRUNT:
			    		case OBJECT_TYPE_HULK:
			    		case OBJECT_TYPE_OBSTACLE:
			    			if(playerHittable) {
			    				GlobalState.getInstance().removePlayerLife();
								playerInvincableTime = 120;
								playerHittable = false;
			    			}
			    			break; 
			    		default:
			    			break;
			    	}
			    	if(icon!=null) {
		    			newSprites.add(icon);
			    	}
				}
	    	}
	    }
	}

	@Override
	public void render(Graphics2D g2d) {
		
		int[] coords = spriteSheet[currentFrame+frameoffset];
		setTextureCoords(coords[0], coords[1], coords[2], coords[3]);

		width = coords[2];
	    height = coords[3];

	    super.render(g2d);
		
		//draw the missles
		for(PlayerMissile missle : missleList) {
			missle.render(g2d);
		}

	}
	//should this be in the base class.
	public void directionalPad(ByteBuffer buttons) {
	}

	@Override
	public void controllerLeftStick(float xAxis, float yAxis) {
		long curTime = System.currentTimeMillis();
		long diffTime = curTime - lastTimeMovedMS;

		int xOffset = (int)(xAxis*playerMovementMuliplyer); 
		int yOffset = -(int)(yAxis*playerMovementMuliplyer); 
		
		float oldPosX = posX;
		float oldPosY = posY;

		if(!(xAxis==0.0f && yAxis==0.0f)) {
			if(diffTime>timeBetweenMovementMS) 
			{
				lastTimeMovedMS = curTime;
				posX += xOffset ;
				posY += yOffset;
			    if(!outOfBounds()) 
				{
					if(Math.abs(xOffset)>Math.abs(yOffset)) {
						if(xOffset<0) {
							currentFrame = PLAYER_LEFT;
						}
						else {
							currentFrame = PLAYER_RIGHT;
						}
					}
					else {
						if(yOffset<0) {
							currentFrame = PLAYER_UP;
						}
						else {
							currentFrame = PLAYER_DOWN;
						}
						
					}
					move = true;
				}
				else {
					move = false;
					posX = oldPosX;
					posY = oldPosY;
				}
			}
		}
		else {
			move = false;
		}
		
	}	
	
	@Override
	public void controllerRightStick(float xAxis, float yAxis) {
		long curTime = System.currentTimeMillis();
		long diffTime = curTime - lastTimeFiredMS;
		if(!(xAxis==0.0f && yAxis==0.0f)) {
			//normalize the axis
			float len = (float)Math.sqrt(xAxis*xAxis + yAxis*yAxis);
			xAxis /= len;
			yAxis /= len;
			if(diffTime>=timeBetweenFireMS) {
				//Need to only fire in 8 directions.
				float diff = Math.abs(Math.abs(xAxis) - Math.abs(yAxis));
				if(diff<(1.0f/4.0f)) {
					diff = (float)Math.sqrt(0.5);
					xAxis = diff*Math.signum(xAxis);
					yAxis = diff*Math.signum(yAxis);
				}
				else {
					//the biggest will take over
					if(Math.abs(xAxis) > Math.abs(yAxis)) {
						xAxis = Math.signum(xAxis);
						yAxis = 0.0f;
					}
					else {
						xAxis = 0.0f; 
						yAxis = Math.signum(yAxis);
					}
				}

				fireProjectile(xAxis, -yAxis);
				lastTimeFiredMS = curTime; 
			}
		}
	}

	private void fireProjectile(float xDir, float yDir) {
		TronSounds.getInstance().playfire();
		missleList.add(new PlayerMissile(posX+10.0f, posY+18.0f, xDir, yDir));
	}

}
