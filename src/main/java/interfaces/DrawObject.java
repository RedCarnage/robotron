package interfaces;

import java.awt.Graphics2D;
import java.util.List;

import enums.ObjectTypes;

public interface DrawObject {
    public void render(Graphics2D g2d);
	public void update(List<DrawObject> npcObjects, List<DrawObject> newSprites);
	public ObjectTypes getObjectType();
	
	
    public void setPos(float x, float y);
    public float getPosX();
    public float getPosY();
    public float getWidth();
    public float getHeight();
    
    public void objectHit(int hitPointsTaken, float[] hitDirection);
    public void setDead();
    public boolean isDead();
}
