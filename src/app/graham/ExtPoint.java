package app.graham;

public class ExtPoint extends Point implements Comparable<ExtPoint>{
	
	static private Point p0 = null;
	
	ExtPoint(double x_value, double y_value){
		super(x_value, y_value);
	}
	
	ExtPoint(Point p){
		super(p.x, p.y);
	}
	
	static public void setP0(Point p) {
		p0 = p;
	}
	
	@Override
	public int compareTo(ExtPoint that){
		//TODO: 4. Primerjanje kota tock this and that glede na tocko p0

		var pjx = (that.x - this.p0.x);
		var pjy = (that.y - this.p0.y);

		var pix = (this.x - this.p0.x);
		var piy = (this.y - this.p0.y);

		var x = pix*pjy - piy*pjx;

		return (x > 0) ?
			1 :
			((x < 0) ? -1 : 0);
		
	}
	
	public String toString() {
		return this.x + ", " + this.y + "-" + p0.x + ", "+ p0.y;
	}
}