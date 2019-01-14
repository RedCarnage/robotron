package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import baseframework.SpriteBase;
import baseframework.SpriteIcon;
import enums.ObjectTypes;
import interfaces.DrawObject;

/***
 * Tank robots
 * 
 * @author Carl Stika
 *
 */
public class Tank extends SpriteBase {
	private int[][] spriteSheet = {
			{0, 274, 34, 37}, //tank 1
			{38, 274, 28, 35}, //tank 2
			{76, 274, 29, 37}, //tank 3
			{115, 275, 26, 33}, //tank 4
		};

	public Tank(BufferedImage image) {
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
			
			float dx = 320;
			float dy = 240;
			if(closestFamily!=null) {
				if( posX <=(closestFamily.getPosX()+closestFamily.getWidth()) &&
						(posX+width)>=closestFamily.getPosX() &&  
						posY<=(closestFamily.getPosY()+closestFamily.getHeight()) &&
						(posY+height)>=closestFamily.getPosY()					
						) {
					closestFamily.setDead();
					//create a new sprite icon
					SpriteBase skull = new SpriteIcon(image, SpriteIcon.ICON_FAMILY_DEATH);
					skull.setPos(closestFamily.getPosX(), closestFamily.getPosY());
					newSprites.add(skull);
				}
				
				dx = closestFamily.getPosX() - posX;
				dy = closestFamily.getPosY() - posY;
			}
			else {
				//TODO what to do when there are no more family.
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
				
				frameoffset = (frameoffset+1)%spriteSheet.length;
				time = System.currentTimeMillis();
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
	
	//Object hit. need missle directions
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
    	super.objectHit(hitPointsTaken, hitDirection);

    }
	
}
