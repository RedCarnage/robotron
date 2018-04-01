package Effects;

import java.awt.Graphics2D;
import java.util.List;

import enums.ObjectTypes;
import interfaces.DrawObject;
import objects.BoundingBox;
import objects.VectorObject;

/***
 * Class implements the Laser Projector like Writer
 *  
 *  To simulate the laser affect you just need a time delay on the alpha.
 *  Need a attack a delay time for the string render.
 * @author Carl Stika
 *
 */
public class LaserWriter implements DrawObject {
	private String text;
	private float stringPercent = 0.0f;
	private VectorObject stringObject = null;
	private VectorObject laserObject = null;
	
	public LaserWriter(String text) {
		this.text = text;
		float x = 100.0f, y = 200.0f;
		
		//SpriteBase
		stringObject  = new VectorObject();
		stringObject .setColor(0.0f, 1.0f, 0.0f, 1.0f);
		for(char c : text.toCharArray()) {
			charToVector(c, x, y, 2, stringObject );
			x += CHAR_WIDTH + 5;
//			textObject.addLine(100,  100, 100+30, 100, 2);
//			textObject.addLine(115,  100, 115, 100+30, 2);
		}
		stringObject.finish();
		
		createConeOfLight();
	}
	
	public void setPos(int x, int y) {
		stringObject.updatePos(x, y);
	}
	
	private static final int CHAR_WIDTH = 25;
	private static final int CHAR_HEIGHT = 30;
	private void charToVector(char c, float x, float y, int lineWidth, VectorObject textObject) {
		c = Character.toUpperCase(c);
		switch(c) {
			default :
			case 'A':
				textObject.addLine(x, y + CHAR_HEIGHT, x + CHAR_WIDTH/2,  y, lineWidth);
				textObject.addLine(x + CHAR_WIDTH/2,  y, x + CHAR_WIDTH, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH/4,  y + CHAR_HEIGHT*5/8, x + CHAR_WIDTH*3/4, y + CHAR_HEIGHT*5/8, 2);
				break;
			case 'B':
				textObject.addLine(x + CHAR_WIDTH, y, x ,  y, lineWidth);
				textObject.addLine(x, y, x, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x ,  y+ CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x + CHAR_WIDTH, y, lineWidth);
				textObject.addLine(x, y+ CHAR_HEIGHT/2, x + CHAR_WIDTH, y + CHAR_HEIGHT/2, lineWidth);
				break;
			case 'C':
				textObject.addLine(x + CHAR_WIDTH, y, x ,  y, lineWidth);
				textObject.addLine(x, y, x, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x ,  y+ CHAR_HEIGHT, lineWidth);
				break;
			case 'D': //Need to make a better D.
				textObject.addLine(x + CHAR_WIDTH*7/8, y, x + CHAR_WIDTH/8,  y, lineWidth);
				textObject.addLine(x+ CHAR_WIDTH/8, y, x+ CHAR_WIDTH/8, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH/8, y + CHAR_HEIGHT, x + CHAR_WIDTH*7/8,  y+ CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH*7/8, y + CHAR_HEIGHT, x + + CHAR_WIDTH*7/8, y, lineWidth);
				break;
			case 'E':
				textObject.addLine(x + CHAR_WIDTH, y, x ,  y, lineWidth);
				textObject.addLine(x, y, x, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x ,  y+ CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT/2 , x ,  y+ CHAR_HEIGHT/2, lineWidth);
				break;
			case 'L':
				textObject.addLine(x, y, x, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x ,  y+ CHAR_HEIGHT, lineWidth);
				break;
			case 'N':
				textObject.addLine(x , y, x ,  y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x , y, x + CHAR_WIDTH, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x + CHAR_WIDTH, y, lineWidth);
				break;
			case 'O':
				textObject.addLine(x + CHAR_WIDTH, y, x ,  y, lineWidth);
				textObject.addLine(x , y, x, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x , y + CHAR_HEIGHT, x + CHAR_WIDTH,  y+ CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y + CHAR_HEIGHT, x + + CHAR_WIDTH, y, lineWidth);
				break;
			case 'R':
				textObject.addLine(x + CHAR_WIDTH, y, x ,  y, lineWidth);
				textObject.addLine(x ,  y + CHAR_HEIGHT/2, x + CHAR_WIDTH , y+ CHAR_HEIGHT/2, lineWidth);
				textObject.addLine(x, y, x, y + CHAR_HEIGHT, lineWidth);
				textObject.addLine(x + CHAR_WIDTH, y, x + CHAR_WIDTH, y + CHAR_HEIGHT/2, lineWidth);
				textObject.addLine(x , y + CHAR_HEIGHT/2, x +CHAR_WIDTH,  y + CHAR_HEIGHT, lineWidth);
				break;
			case 'T':
				textObject.addLine(x + CHAR_WIDTH, y, x ,  y, lineWidth);
				textObject.addLine(x + CHAR_WIDTH/2, y, x+ CHAR_WIDTH/2, y + CHAR_HEIGHT, lineWidth);
				break;
			case ' ':
				break;
		}
	}

	private void createConeOfLight() {
//		Vector3f apex = new Vector3f(320.0f, 240.0f, -10.0f); //need to know where is intersects the image plane
		BoundingBox box1 = stringObject.getboundingbox();
		
		laserObject = new VectorObject();
		
		//Just to the bounding box for now
		laserObject.addLine(box1.minx, box1.miny, box1.maxx, box1.miny, 1);
		laserObject.addLine(box1.maxx, box1.miny, box1.maxx, box1.maxy, 1);
		laserObject.addLine(box1.maxx, box1.maxy, box1.minx, box1.maxy, 1);
		laserObject.addLine(box1.minx, box1.maxy, box1.minx, box1.miny, 1);
		laserObject.setColor(1.0f, 0.0f, 0.0f, 1.0f);

		//Should we chaneg this to update when ever it changes
		laserObject.finish();
		//create cone
	}
	
	@Override
	public void render(Graphics2D g2d) {
		stringPercent += 0.5f;
		laserObject.render(g2d);
//		stringObject.render(g2d, stringPercent);
	}

	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		
	}

	@Override
	public ObjectTypes getObjectType() {
		// TODO Auto-generated method stub
		return ObjectTypes.OBJECT_TYPE_UNKOWN;
	}

	@Override
	public void setPos(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPosX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPosY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void objectHit(int hitPointsTaken, float[] hitDirection) {
		// TODO Auto-generated method stub
		
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
