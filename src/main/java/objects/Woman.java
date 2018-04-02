package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import baseframework.SpriteBase;
import enums.ObjectTypes;
import interfaces.DrawObject;

/***
 * Part of the human family. Just has a random walk.
 * @author Carl Stika
 *
 */
public class Woman extends SpriteBase {
	private static final int WOMAN_LEFT = 0;
	private static final int WOMAN_RIGHT = 3;
	private static final int WOMAN_DOWN = 6;
	private static final int WOMAN_UP = 9;

	private int[][] spriteSheet = {
			{206, 1, 16, 27}, //women l1
			{229, 0, 20, 29}, //women l2
			{256, 0, 17, 30}, //women l3
			{307, 1, 17, 29}, //women r1
			{281, 0, 16, 28}, //women r2
			{336, 0, 16, 33}, //women r3
			{360, 0, 17, 28}, //women d1
			{384, 0, 22, 31}, //women d2
			{407, 1, 27, 32}, //women d3
			{435, 0, 22, 31}, //women u1
			{464, 0, 17, 32}, //women u2
			{488, 0, 24, 31}, //women u3
	};
	private float speed = 1.5f;
	private long time = System.currentTimeMillis();
	private int frameoffset = 0;
	private int currentFrame = 0;
	float dx = 0.0f;
	float dy = 1.0f;
	Random random = new Random();
	
	/**
	 * 
	 */
	public Woman(BufferedImage image) {
		super(image);

		hitPoints = -1; //indestructable from player
		objectType = ObjectTypes.OBJECT_TYPE_WOMAN;
		
		switch(random.nextInt(4)) {
			case 0:
				dx = 1.0f;
				dy = 0.0f;
				break;
			case 1:
				dx = -1.0f;
				dy = 0.0f;
				break;
			case 2:
				dx = 0.0f;
				dy = 1.0f;
				break;
			case 3:
				dx = 0.0f;
				dy = -1.0f;
				break;
		}
		width = spriteSheet[currentFrame+frameoffset][2];
	    height = spriteSheet[currentFrame+frameoffset][3];
	}
	
	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		
		//check if a hulk bot ran over him.
		float len = (float)Math.sqrt(dx*dx+dy*dy);
		
		dx /= len;
		dy /= len;
		
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
				time = System.currentTimeMillis();
				
				if(Math.abs(dx)>Math.abs(dy)) {
					if(dx<0) {
						currentFrame = WOMAN_LEFT;
					}
					else {
						currentFrame = WOMAN_RIGHT;
					}
				}
				else {
					if(dy<0) {
						currentFrame = WOMAN_UP;
					}
					else {
						currentFrame = WOMAN_DOWN;
					}
					
				}
			}
			else {
				posX = oldPosX;
				posY = oldPosY;
				switch(random.nextInt(4)) {
				case 0:
					dx = 1.0f;
					dy = 0.0f;
					break;
				case 1:
					dx = -1.0f;
					dy = 0.0f;
					break;
				case 2:
					dx = 0.0f;
					dy = 1.0f;
					break;
				case 3:
					dx = 0.0f;
					dy = -1.0f;
					break;
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
	}

}
