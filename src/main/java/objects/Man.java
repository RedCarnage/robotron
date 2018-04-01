package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import baseframework.SpriteBase;
import enums.ObjectTypes;
import interfaces.DrawObject;

public class Man extends SpriteBase {
	private static final int MAN_LEFT = 0;
	private static final int MAN_RIGHT = 3;
	private static final int MAN_DOWN = 6;
	private static final int MAN_UP = 9;

	private int[][] spriteSheet = {
		{520, 0, 16, 28}, //man l1
		{576, 0, 17, 27}, //man l2
		{547, 1, 17, 26},  //man l3
		{0, 37, 17, 27}, //man r1
		{32, 37, 16, 27}, //man r2
		{56, 38, 23, 26}, //man r3
		{88, 36, 25, 28}, //man d1
		{120, 38, 18, 32}, //man d2
		{148, 36, 21, 30}, //man d3
		{183, 38, 18, 26}, //man u1
		{211, 35, 20, 31}, //man u2
		{243, 38, 21, 31}, //man u3
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
	public Man(BufferedImage image) {
		super(image);

		hitPoints = -1; //indestructable from player
		objectType = ObjectTypes.OBJECT_TYPE_MAN;
		
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
						currentFrame = MAN_LEFT;
					}
					else {
						currentFrame = MAN_RIGHT;
					}
				}
				else {
					if(dy<0) {
						currentFrame = MAN_UP;
					}
					else {
						currentFrame = MAN_DOWN;
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
