package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import Effects.TronPalette;
import enums.ObjectTypes;
import interfaces.DrawObject;
import utilitys.GameUtils;

public class Title implements DrawObject  {

	private int colorIndex = 0;
	private int numTicks = 0;
	//title pallete
	private BufferedImage titleImage;
	
	public Title() {
		titleImage = GameUtils.loadImage("robotron.png");
	}

	@Override
	public void render(Graphics2D g2d) {
		
		g2d.drawImage(titleImage, 320-titleImage.getWidth()/2,  50, null);
		g2d.setColor(TronPalette.colorArray[colorIndex]);
		
		
//		g2d.fillRect(320-200, 120-50, 400, 200);
//		g2d.clipRect(320-200, 120-50, 400, 200); //we could do a shape

		
//		g2d.setClip(null);
	}

	@Override
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites) {
		numTicks++;
		
		if(numTicks==5) {
			colorIndex++;
			colorIndex %= TronPalette.colorArray.length;
			
			numTicks = 0;
		}
		
	}

	@Override
	public ObjectTypes getObjectType() {
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
