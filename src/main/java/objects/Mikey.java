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
public class Mikey extends SpriteBase {
	private static final int MIKEY_LEFT = 0;
	private static final int MIKEY_RIGHT = 3;
	private static final int MIKEY_DOWN = 6;
	private static final int MIKEY_UP = 9;

	private int[][] spriteSheet = {
			{272, 37, 9, 24},  //boy l1
			{292, 36, 13, 25}, //boy l2
			{315, 38, 14, 23}, //boy l3
			{336, 36, 10, 26}, //boy r1
			{358, 37, 11, 25}, //boy r2
			{377, 36, 16, 26}, //boy r3
			{400, 38, 17, 24}, //boy d1
			{424, 36, 16, 25}, //boy d2
			{445, 37, 13, 26}, //boy d3
			{465, 36, 16, 27}, //boy u1
			{488, 38, 18, 26}, //bou u2
			{512, 36, 12, 25}, //boy u3
	};
	private float speed = 3.0f;
	private long time = System.currentTimeMillis();
	private int frameoffset = 0;
	private int currentFrame = 0;
	float dx = 0.0f;
	float dy = 1.0f;
	Random random = new Random();
	
	/**
	 * 
	 */
	public Mikey(BufferedImage image) {
		super(image);

		hitPoints = -1; //indestructable from player
		objectType = ObjectTypes.OBJECT_TYPE_MIKEY;
		
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
		super.update(npcObjects, newSprites);
		
		if(!dying) {
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
							currentFrame = MIKEY_LEFT;
						}
						else {
							currentFrame = MIKEY_RIGHT;
						}
					}
					else {
						if(dy<0) {
							currentFrame = MIKEY_UP;
						}
						else {
							currentFrame = MIKEY_DOWN;
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
		
	}

	@Override
	public void render(Graphics2D g2d) {
		int[] coords = spriteSheet[currentFrame+frameoffset];

		setTextureCoords(coords[0], coords[1], coords[2], coords[3]);

		width = coords[2];
	    height = coords[3];

		g2d.drawImage(image, (int)posX, (int)posY, (int)posX+(int)width, (int)posY+(int)height, tx, ty, tx+tw, ty+th, null);
	}

}
