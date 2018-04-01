package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import baseframework.SpriteBase;
import enums.ObjectTypes;
import game.GlobalState;
import interfaces.DrawObject;
import sounds.TronSounds;

public class BadRobot extends SpriteBase {
	private int[][] spriteSheet = {
			{149, 232, 25, 28}, //bad robot 1
			{178, 232, 23, 32}, //bad robot 2
			{208, 235, 25, 29}, //bad robot 3
	};

	public BadRobot(BufferedImage image) {
		super(image);

		posX = 640.0f/2.0f;
		posY = 480.0f/2.0f;
		objectType = ObjectTypes.OBJECT_TYPE_GRUNT;

		width = spriteSheet[currentFrame+frameoffset][2];
	    height = spriteSheet[currentFrame+frameoffset][3];
		
	}
	
	private static float speed = 3.0f;
	private long time = System.currentTimeMillis();
	private int frameoffset = 0;
	private int currentFrame = 0;
	
	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		DrawObject mainPlayer = npcObjects.stream().filter(d -> (d.getObjectType()==ObjectTypes.OBJECT_TYPE_PLAYER)).findFirst().orElse(null);
		float dx = 320;
		float dy = 240;
		
		if(mainPlayer!=null) {
			dx = mainPlayer.getPosX() - posX;
			dy = mainPlayer.getPosY() - posY;
		}
		
		float len = (float)Math.sqrt(dx*dx+dy*dy);
		
		if(len>0.0f) {
			dx /= len;
			dy /= len;
		}
		
		//base this off of time
		int diffTime =(int)(System.currentTimeMillis() - time);

		//Need IDLE
		if(diffTime>=(1000/6)) {
			float oldPosX = posX;
			float oldPosY = posY;
			posX += dx*speed;  //speed
			posY += dy*speed;  //speed
			
			if(!outOfBounds()) {
				frameoffset = (frameoffset+1)%3;
			}
			else {
				posX = oldPosX;
				posY = oldPosY;
			}
			time = System.currentTimeMillis();
		}
		
	}

	@Override
	public void render(Graphics2D g2d) {
		int[] coords = spriteSheet[currentFrame+frameoffset];

		setTextureCoords(coords[0], coords[1], coords[2], coords[3]);

		width = coords[2];
	    height = coords[3];

	    super.render(g2d);
	}
	
	@Override
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
		super.objectHit(hitPointsTaken, hitDirection);
		if(dead) {
			TronSounds.getInstance().playRobotDir();
			GlobalState.getInstance().addScore(100);
		}
    }

}
