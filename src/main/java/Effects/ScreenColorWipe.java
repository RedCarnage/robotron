package Effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import interfaces.ScreenWipe;

/***
 * Simple RobotTron style of screenwipe.
 * 
 * @author Carl Stika
 *
 */
public class ScreenColorWipe implements ScreenWipe {
	private int screenWidth;
	private int screenHeight;
	private int sideWidth;
	private int maxBoxes;
	private int waitTicks = 0;
	
	private int curX;
	private int curY;
	private int widthX;
	private int widthY;
	
	private int curWidth;
	private int curHeight;
	private int numSteps = 0;;
	
	private boolean addingBoxes = true;
	private int colorIndex = 0;
	private Color[] colorArray = {
							new Color(255, 0, 0),
							new Color(0, 255, 0),
							new Color(255, 255, 0),
							new Color(0, 0, 255),
							new Color(0, 255, 255)
	};
	
	public class WipeBox {
		int x,y, w, h;

		public WipeBox(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}

	List<WipeBox> boxes = new ArrayList<>();
	
	public ScreenColorWipe(int screenWidth, int screenHeight, int sideWidth) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.sideWidth = sideWidth;
		
		init();
	}
	
	private void init() {
		
		if(screenWidth>screenHeight) {
			maxBoxes = ((screenHeight/2)+sideWidth)/sideWidth;

			curWidth = screenWidth-(maxBoxes*sideWidth*2);
			curHeight = 0;
			
			widthX = sideWidth; //*screenWidth/screenHeight;
			widthY = sideWidth;
		}
		else {
			maxBoxes = ((screenWidth/2)+sideWidth)/sideWidth;

			curWidth = 0;
			curHeight = screenHeight-(maxBoxes*sideWidth*2);

			widthX = sideWidth;
			widthY = sideWidth; //*screenHeight/screenWidth;
		}
		curX = screenWidth/2 - curWidth/2;
		curY = screenHeight/2 - curHeight/2;
	}
	
	@Override
	public void render(Graphics2D g2d) {
		int c = colorIndex;
		for(WipeBox box : boxes) {
			g2d.setColor(colorArray[c]);
			
			//Draw four sides
			g2d.fillRect(box.x, box.y, box.w, widthX);
			g2d.fillRect(box.x, box.y+box.h-widthY, box.w, widthX);

			g2d.fillRect(box.x, box.y, widthX, box.h);
			g2d.fillRect(box.x+box.w-widthX, box.y, widthX, box.h);
			
			c = (c+1)%colorArray.length;
		}
	}

	@Override
	public int update() {
		waitTicks--;
		if(waitTicks<=0) {
			numSteps++;
			waitTicks =1;
			if(addingBoxes) {
				colorIndex = (colorIndex+1)%colorArray.length;
				if(boxes.size()>=maxBoxes) {
					addingBoxes = false;
				}
				else {
					boxes.add(new WipeBox(curX, curY, curWidth, curHeight));
					curX-=widthX;
					curY-=widthY;
					curWidth+=widthX*2;
					curHeight+=widthY*2;
				}
			}
			else {
				colorIndex = (colorIndex+colorArray.length-1)%colorArray.length;
				//take away a box
				if(boxes.size()>0) {
					boxes.remove(0);
				}
			}
		}
		return (numSteps*100)/(maxBoxes*2);
	}
	

}
