package baseframework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.List;

import enums.ObjectTypes;
import game.GlobalState;
import interfaces.DrawObject;
import utilitys.GameUtils;

/*
 * Basic quad based sprite
 */
public abstract class SpriteBase implements DrawObject {
	
	//This should be split into a logical object and display object
	protected static final int BUTTON_DIR_PAD_UP = 10;
	protected static final int BUTTON_DIR_PAD_RIGHT = 11;
	protected static final int BUTTON_DIR_PAD_DOWN = 12;
	protected static final int BUTTON_DIR_PAD_LEFT = 13;
	/*
     * buttons
     * 0 - A
     * 1 - B
     * 2 - X
     * 3 - Y
     * 4 - left top shoulder
     * 5 - right top shoulder
     * 6 - back
     * 7 - start
     * 8 - left bottom shoulder - doesn't seem to work 
     * 9 - right bottom shoulder - doesn't seem to work 
     * 10 - up
     * 11 - right
     * 12 - down
     * 13 - left
     * 
     */	

	//Shared by all sprites
	protected float posX = 0.0f;
	protected float posY = 0.0f;
	protected float width = 640.0f;  //640, 576
	protected float height = 576.0f;
	
	protected int tx, ty, tw, th;
	
	protected BufferedImage image;
	protected boolean dead = false;
	protected int hitPoints = 1;
	protected ObjectTypes objectType = ObjectTypes.OBJECT_TYPE_UNKOWN;
	protected float scalex = 1.0f;
	protected float scaley = 1.0f;
	
	protected int dieAnimationStep = 0;
	protected boolean dying = false;
	
	public SpriteBase(String file) {
		createTexture(file);
	}
	
	public SpriteBase(BufferedImage image) {
		this.image = image;
	}

	protected void createTexture(String file)  {
		image = GameUtils.loadImage("file");
    }

    public void setTextureCoords(int x, int  y, int w, int  h) {
    	tx = x;
    	ty = y;
    	tw = w;
    	th = h;
    }
    
    public boolean isDead() {
    	return dead;
    }

    public void setDead() {
    	//check for type
    	dying = true;
    	dieAnimationStep = 1;
    }

    public void objectHit(int hitPointsTaken, float[] hitDirection) {
    	if(hitPoints>0) {
    		hitPoints -= hitPointsTaken;
    		if(hitPoints<=0) {
    			hitPoints = 0;
    			setDead();
    		}
    	}
    }
    
    public void setPos(float x, float y) {
    	posX = x;
    	posY = y;
    }
	
    public float getPosX() {
    	return posX;
    }

    public float getPosY() {
    	return posY;
    }
    
    public float getWidth() {
    	return width;
    }

    public float getHeight() {
    	return height;
    }

    public void render(Graphics2D g2d) {
    	
    	if(GlobalState.getInstance().enemiesAnimatingOn>0) {
    		int explodespace = GlobalState.getInstance().enemiesAnimatingOn*4;
    		
    		int halfHeight = (int)((width*explodespace)/2);
    		int y = (int)posY - halfHeight;
    		for(int i=0;i<height;i++) {
        		g2d.drawImage(image, (int)posX, y+i*explodespace, (int)posX+(int)width, y+i*explodespace+1, 
        						tx, ty+i, tx+tw, ty+i+1, null);
    		}
    	}
    	else {
    		if(dieAnimationStep>0) {
        		int explodespace = dieAnimationStep*4;
        		
        		int halfHeight = (int)((width*explodespace)/2);
        		int y = (int)posY - halfHeight;
        		for(int i=0;i<height;i++) {
            		g2d.drawImage(image, (int)posX, y+i*explodespace, (int)posX+(int)width, y+i*explodespace+1, 
            						tx, ty+i, tx+tw, ty+i+1, null);
        		}
    		}
    		else {
    			g2d.drawImage(image, (int)posX, (int)posY, (int)posX+(int)width, (int)posY+(int)height, tx, ty, tx+tw, ty+th, null);
    		}
    	}
	}

	//The list of objects can be used for collision
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		if(dying) {
			if(dieAnimationStep==4) {
				dead = true;
			}
			else {
				dieAnimationStep++;
			}
		}

	}

	public void controllerButtons(ByteBuffer buttons) {
	}	

	public void controllerLeftStick(float xAxis, float yAxes) {
	}	

	public void controllerRightStick(float xAxis, float yAxes) {
	}	

	public ObjectTypes getObjectType() {
		return objectType;
	}
	
	public boolean outOfBounds() {
		if(posX<5.0f) {
			return true;
		}
		if(posY<12.0f) {
			return true;
		}
		if((posX+width)>(640.0f-5.0f)) {
			return true;
		}
		if((posY+height)>(480.0f-5.0f)) {
			return true;
		}
		return false;
	}
}
