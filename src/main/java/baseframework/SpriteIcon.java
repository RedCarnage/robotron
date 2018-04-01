package baseframework;

import java.awt.image.BufferedImage;
import java.util.List;

import enums.ObjectTypes;
import interfaces.DrawObject;

/**
 * Class displays a non animating sprite.
 * @author Carl Stika
 *
 */
public class SpriteIcon extends SpriteBase {
	
	public static final int ICON_FAMILY_DEATH = 0;
	public static final int ICON_1000 = 1;
	public static final int ICON_2000 = 2;
	public static final int ICON_3000 = 3;
	public static final int ICON_4000 = 4;
	
	private int[][] spriteSheet = {
			{0, 1, 25, 23},  //Family death
			{35, 0, 27, 13}, //1000  
			{70, 0, 26, 14}, //2000 
			{105, 0, 21, 13}, //3000
			{139, 0, 22, 14}, //4000
			{172, 0, 22, 13} //5000
	};
	private float speed = 1.5f;
	private long time = System.currentTimeMillis();
	private int currentFrame = 0;
	
	/**
	 * 
	 */
	public SpriteIcon(BufferedImage image, int iconNum) {
		super(image);

		hitPoints = -1; //Player can not destroy
		objectType = ObjectTypes.OBJECT_TYPE_FAMILY_DEATH;
		currentFrame = iconNum;
		
		if(currentFrame>=spriteSheet.length) {
			currentFrame = spriteSheet.length - 1;
		}
		width = spriteSheet[currentFrame][2];
	    height = spriteSheet[currentFrame][3];
	}
	
	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		
		//base this off of time
		int diffTime =(int)(System.currentTimeMillis() - time);

		//Need IDLE
		if(diffTime>=(1000*5)) { //5 seconds
			dead = true;
		}
		
		int[] coords = spriteSheet[currentFrame];

		setTextureCoords(coords[0], coords[1], coords[2], coords[3]);

		width = coords[2];
	    height = coords[3];
	}

	@Override
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
		// TODO Auto-generated method stub
		
	}

}
