package objects;

/**
 * Simple boxing box used for collisions.
 * 
 * @author Carl Stika
 *
 */
public class BoundingBox {
	public float minx, maxx;
	public float miny, maxy;
	
	public BoundingBox(float minx, float miny, float maxx, float maxy) {
		super();
	
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
	}
	
	public BoundingBox(BoundingBox box2) {
		minx = box2.minx;
		maxx = box2.maxx;
		miny = box2.miny;
		maxy = box2.maxy;
	}

	/**
	 * expand the bounding box.
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(float x, float y) {
		if(x<minx) {
			minx = x;
		}
		if(x>maxx) {
			maxx = x;
		}
		if(y<miny) {
			miny = y;
		}
		if(y>maxy) {
			maxy = y;
		}
	}
	
	public void addBoundingBox(BoundingBox box2) {
		if(box2.minx<minx) {
			minx = box2.miny;
		}
		if(box2.maxx>maxx) {
			maxx = box2.maxx;
		}
		if(box2.miny<miny) {
			miny = box2.miny;
		}
		if(box2.maxy>maxy) {
			maxy = box2.maxy;
		}
	}
	
	
	
}
