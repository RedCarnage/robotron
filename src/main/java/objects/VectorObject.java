package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

//This object will draw lines, like a vector objects
public class VectorObject {
	
	public class LineInfo {
		public float x1, y1;
		public float x2, y2;
		public float lineWidth;
		
		public LineInfo(float x1, float y1, float x2, float y2, float lineWidth) {
			this.x1 = x1;
			this.y1 = y1;

			this.x2 = x2;
			this.y2 = y2;

			this.lineWidth = lineWidth;
			
		}
	}
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

	//Shared by all Vector drawing
	protected float posX = 0.0f;
	protected float posY = 0.0f;
	protected float width = 640.0f;  //640, 576
	protected float height = 576.0f;
	
	protected int textureWidth = 0;
	protected int textureHeight = 0;
	private float[] colorArray = new float[4];
	
	private List<LineInfo> lineArray = new ArrayList<>();
	private BoundingBox boundingBox;
	
	public VectorObject() {
		
		colorArray[0] = 1.0f;
		colorArray[1] = 1.0f;
		colorArray[2] = 1.0f;
		colorArray[3] = 1.0f;
		
//	    createFullScreenQuad();
	}
	
	public void setColor(float r, float g, float b, float a) {
		colorArray[0] = r;
		colorArray[1] = g;
		colorArray[2] = b;
		colorArray[3] = a;
		
		for(int i=0;i<colorArray.length;i++) {
			if(colorArray[i]<0.0f) colorArray[i] = 0.0f;
			if(colorArray[i]>1.0f) colorArray[i] = 1.0f;
		}
	}
	public void setColor(float[] color) {
		for(int i=0;i<colorArray.length;i++) {
			colorArray[i] = color[i];
			if(colorArray[i]<0.0f) colorArray[i] = 0.0f;
			if(colorArray[i]>1.0f) colorArray[i] = 1.0f;
		}
	}
	
	public void addLine(float x1, float y1, float x2, float y2, float width) {
		if(boundingBox==null) {
			boundingBox = new BoundingBox(x1, y1, x1, y1);
		}
		
		lineArray.add(new LineInfo(x1, y1, x2, y2, width));

		//expand the bounding box
		boundingBox.addPoint(x1,  y1);
		boundingBox.addPoint(x2,  y2);
//		createVertexArray();
	}
	
	public BoundingBox getboundingbox() {
		return boundingBox;
	}
	
	public void updatePos(float x, float y) {
		posX = x;
		posY = y;
	}

	public void finish() {
	}

	
    private void drawLines(Graphics2D g2d) {
		if(lineArray.size()>0) {
	        //Put the pivot in the center. Need to be able to put a offset in.
//			System.out.printf("color %f, %f, %f\n",  colorArray[0], colorArray[1], colorArray[2]);
			g2d.setColor(new Color(colorArray[0], colorArray[1], colorArray[2]));
			//set stroke
			
	        for(LineInfo line : lineArray) {
				g2d.setStroke(new BasicStroke(line.lineWidth));
	        	g2d.drawLine((int)(posX+line.x1), (int)(posY+line.y1), (int)(posX+line.x2), (int)(posY+line.y2));
	        }
		}
    }

	public void render(Graphics2D g2d) {
		if(lineArray.size()>0) {
			drawLines(g2d);
		}
	}

	public void update() {
	}

	public void controllerButtons(ByteBuffer buttons) {
	}	

	public void controllerLeftStick(float xAxis, float yAxes) {
	}	

	public void controllerRightStick(float xAxis, float yAxes) {
	}


/*	public void render(float pathPercent) {
		if(lineArray.size()>0) {
			model.identity();
	        model.translate(posX, posY, 0.0f);
	        //model.scale(width, height, 1.0f); //the size of the quad
			
			glEnable (GL_BLEND); 
			glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);		
			
	        glBindTexture(GL_TEXTURE_2D, textureID);        
			glUseProgram(shaderProgram);
	        glUniform4f(lineColor,	colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
	        glUniformMatrix4fv(projID, false, proj.get(matrixBuffer));
	        glUniformMatrix4fv(modelID, false, model.get(matrixBuffer));
	        glBindVertexArray(vao);
	        glDrawArrays(GL_TRIANGLES, 0, lineArray.size() * 6);
	        glBindVertexArray(0);
		}
	}	
*/
}
