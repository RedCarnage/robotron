package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.List;

import enums.ObjectTypes;
import interfaces.DrawObject;

/**
 * graphical string display
 * @author Carl
 *
 */
public class FontBase implements DrawObject{
	private static final int redZero = 0;
	private static final int redA = 10;
	private static final int whiteZero = 36;
	private static final int whiteA = 46;

	private float[][] spriteSheet = {
			{153, 275, 6, 10}, // 0 red
			{173, 274, 3, 12}, // 1 red
			{185, 272, 15, 17}, // 2 red
			{206, 272, 9, 16},  // 3 red
			{224, 275, 9, 11},  // 4 red
			{240, 272, 15, 17},  // 5 red
			{261, 272, 11, 17},  // 6 red
			{278, 272, 11, 17},  // 7 red
			{296, 275, 8, 10},   // 8 red
			{312, 272, 11, 17},  // 9 red
			{329, 272, 16, 18},  // A red
			{350, 272, 10, 17},  // B red
			{369, 274, 7, 12},  // C  red
			{387, 272, 8, 17},  //D red
		 	{405, 272, 12, 17}, //E red
			{422, 272, 11, 18}, //F red
			{441, 274, 7, 11},  //G red
			{456, 272, 11, 16}, //H  red
			{477, 274, 7, 12},  //I red
			{492, 274, 13, 15},  //J red
			{512, 274, 8, 12}, //K red
			{531, 274, 7, 14},  //L red
			{544, 274, 17, 15}, //M red
			{571, 272, 8, 17},  //N red
			{588, 275, 8, 10},  //O red
			{0, 317, 8, 10},  //p  red
			{16, 314, 14, 15},  //q  red
			{35, 316, 10, 12},  //r  red
			{54, 313, 11, 14},  //s  red
			{72, 317, 8, 11},  //t red
			{88, 315, 14, 14},  //u  red
			{104, 316, 17, 12}, //v  red
			{125, 316, 17, 12}, //w  red
			{144, 316, 17, 12},  //x  red
			{166, 315, 11, 13},  //y  red
			{184, 315, 8, 13}, //z  red
			{202, 315, 6, 13},  //right p rewd
			{222, 315, 10, 13}, //left p red
			{238, 317, 12, 12}, //0  white
			{263, 317, 8, 13}, //1  white
			{283, 317, 10, 13}, //2  white
			{305, 317, 10, 13}, //3  white
			{326, 317, 11, 12}, //4  white
			{349, 317, 10, 13}, //5  white
			{371, 317, 10, 12}, //6  white
			{393, 317, 10, 12}, //7  white
			{414, 317, 12, 12}, //8  Green
			{437, 317, 10, 13}, //9  Green
			{480, 313, 16, 20}, //A Green
			{568, 314, 14, 19}, //B Green
			{500, 315, 16, 17}, //C Green 
			{523, 315, 14, 17}, //D  Green
			{588, 315, 16, 17},// E  Green
			{456, 316, 17, 15}, //F  Green
			{544, 316, 16, 15}, //G  Green
			{0, 336, 14, 17}, //H Green
			{24, 338, 7, 14},  //I  Green
			{43, 337, 13, 16}, //J  Green
			{64, 336, 18, 18}, //K  Green
			{88, 337, 13, 15}, //L  Green
			{109, 338, 14, 14}, //M  Green
			{130, 336, 15, 17}, //N  Green
			{154, 336, 16, 18},  //O  Green,
			{176, 336, 15, 16}, //P  Green
			{197, 338, 15, 14},  //Q  Green
			{218, 336, 15, 17},  //R  Green
			{240, 339, 17, 13},  //S  Green
			{264, 336, 14, 17},  //T  Green
			{285, 338, 15, 14},  //U  Green
			{307, 337, 13, 15},  //V  Green
			{329, 338, 15, 15}, //W  Green
			{352, 337, 17, 16},  //X  Green
			{372, 338, 17, 13},  //Y  Green
			{395, 336, 14, 17},  //Z  Green
	};
	
	private static int shaderProgram = -1; //The same shader is used for all sprites.
	private static int quadProjID = 0;
	private static int quadModelID = 0;
	private static int inputPosition = 0;
	private static int inputTextureCoords = 0;
	private static int fragmentFontColor = 0;

	private int vao = 0;

	protected static float mainPlayerPosX = 0.0f;
	protected static float mainPlayerPosY = 0.0f;
	protected int textureID = -1;
	protected float posX = 0.0f;
	protected float posY = 0.0f;
	protected float width = 640.0f;  //640, 576
	protected float height = 576.0f;
	
	protected int textureWidth = 0;
	protected int textureHeight = 0;
	protected boolean dead = false;
	protected int hitPoints = 1;
	protected float scalex = 1.0f;
	protected float scaley = 1.0f;
	private float[] colorArray = new float[4];
	private String fontString = "";
	
	
	public FontBase(BufferedImage image) {
		
		colorArray[0] = 1.0f;
		colorArray[1] = 1.0f;
		colorArray[2] = 1.0f;
		colorArray[3] = 1.0f;
		
	}


	public boolean addString(String string) {
		fontString = string;

	    return true;
	}

    private void createFontProgram() {
    }

    public boolean isObjectDead() {
//    	System.out.println("dead = " + dead);
    	return dead;
    }

	public void objectHit(int hitPointsTaken, float[] hitDirection) {
    	if(hitPoints>0) {
    		hitPoints -= hitPointsTaken;
    		if(hitPoints<=0) {
    			hitPoints = 0;
    			dead = true;
    		}
    	}
    }
    
    public void setMainPlayerPos(float x, float y) {
    	mainPlayerPosX = x;
    	mainPlayerPosY = y;
//    	System.out.printf("set player %f, %f\n",mainPlayerPosX, mainPlayerPosY); 
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

	@Override
    public void render(Graphics2D g2d) {
        g2d.setColor(new Color(colorArray[0], colorArray[1], colorArray[2], colorArray[3]));
		g2d.drawString(fontString, posX,  posY);
	}

	public void controllerButtons(ByteBuffer buttons) {
	}	

	public void controllerLeftStick(float xAxis, float yAxes) {
	}	

	public void controllerRightStick(float xAxis, float yAxes) {
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


	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ObjectTypes getObjectType() {
		// TODO Auto-generated method stub
		return ObjectTypes.OBJECT_TYPE_UNKOWN;
	}


	@Override
	public void setDead() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
