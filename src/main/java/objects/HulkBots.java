package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import baseframework.SpriteBase;
import baseframework.SpriteIcon;
import enums.ObjectTypes;
import interfaces.DrawObject;
import sounds.TronSounds;

/***
 * Hulkbots can not be killed
 * 
 * @author Carl Stika
 *
 */
public class HulkBots extends SpriteBase {

	private static final int HULK_LEFT = 0;
	private static final int HULK_RIGHT = 6;
	private static final int HULK_DOWN = 3;
	private static final int HULK_UP = 3;

	private int[][] spriteSheet = {
		{540, 37, 17, 32}, //big robot l1
		{572, 36, 29, 36}, //big robot l2
		{0, 80, 26, 32}, //big robot l3
		{36, 81, 33, 31}, //big robot u1 --up and down are the smae
		{74, 80, 31, 34}, //big robot u2
		{112, 80, 33, 38}, //big robot u3
		{190, 80, 27, 33}, //big robot r1
		{155, 81, 19, 31}, //big robot r2
		{224, 80, 33, 33}, //big robot r3
	};

	public HulkBots(BufferedImage image) {
		super(image);
		
		hitPoints = -1; //indestructable
		objectType = ObjectTypes.OBJECT_TYPE_HULK;
		
		width = spriteSheet[currentFrame+frameoffset][2];
	    height = spriteSheet[currentFrame+frameoffset][3];
		
	}

	private float speed = 2.0f;
	private long time = System.currentTimeMillis();
	private int frameoffset = 0;
	private int currentFrame = 0;
	

	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		super.update(npcObjects, newSprites);
		
		if(!dying) {
	
			//need position to the player.
			//currently move to the middle
			DrawObject closestFamily = null;
			float closestFamilyDist = 0.0f;
	
			//Find the closet family member
			for(DrawObject obj : npcObjects) {
				if(	obj.getObjectType()==ObjectTypes.OBJECT_TYPE_MAN ||
					obj.getObjectType()==ObjectTypes.OBJECT_TYPE_WOMAN ||
					obj.getObjectType()==ObjectTypes.OBJECT_TYPE_MIKEY) {
					float dx = posX - obj.getPosX(); 
					float dy = posY - obj.getPosY();
					float dist = dx*dx+dy*dy;
					if(closestFamily==null || dist<closestFamilyDist) {
						closestFamily = obj;
						dist=closestFamilyDist;
					}
				}
			}
			
			float dx = posX;
			float dy = posY;
			if(closestFamily!=null) {
				if( posX <=(closestFamily.getPosX()+closestFamily.getWidth()) &&
						(posX+width)>=closestFamily.getPosX() &&  
						posY<=(closestFamily.getPosY()+closestFamily.getHeight()) &&
						(posY+height)>=closestFamily.getPosY()					
						) {
					closestFamily.setDead();
					TronSounds.getInstance().playFamilyDie();
					
					//create a new sprite icon
					SpriteIcon skull = new SpriteIcon(image, SpriteIcon.ICON_FAMILY_DEATH);
					skull.setPos(closestFamily.getPosX(), closestFamily.getPosY());
					newSprites.add(skull);
				}
				
				dx = closestFamily.getPosX() - posX;
				dy = closestFamily.getPosY() - posY;
			}
			else {
				//No Family go after player
				DrawObject mainPlayer = npcObjects.stream().filter(d -> (d.getObjectType()==ObjectTypes.OBJECT_TYPE_PLAYER)).findFirst().orElse(null);
				
				if(mainPlayer!=null) {
					dx = mainPlayer.getPosX() - posX;
					dy = mainPlayer.getPosY() - posY;
				}
			}
			float len = (float)Math.sqrt(dx*dx+dy*dy);
			
			dx /= len;
			dy /= len;
			
			//base this off of time
			int diffTime =(int)(System.currentTimeMillis() - time);
	
			//Need IDLE
			if(diffTime>=(1000/6)) {
				posX += dx*speed;  //speed
				posY += dy*speed;  //speed
				
				frameoffset = (frameoffset+1)%3;
				time = System.currentTimeMillis();
				
				if(Math.abs(dx)>Math.abs(dy)) {
					if(dx<0) {
						currentFrame = HULK_LEFT;
					}
					else {
						currentFrame = HULK_RIGHT;
					}
				}
				else {
					if(dy<0) {
						currentFrame = HULK_UP;
					}
					else {
						currentFrame = HULK_DOWN;
					}
					
				}
			}
			
			int[] coords = spriteSheet[currentFrame+frameoffset];
	
			setTextureCoords(coords[0], coords[1], coords[2], coords[3]);
	
			width = coords[2];
		    height = coords[3];
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
	

	//Object hit. need missle directions
	@Override
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
    	super.objectHit(hitPointsTaken, hitDirection);
		posX += hitDirection[0]*speed;  //speed
		posY += hitDirection[1]*speed;  //speed
		
    }
	
}
