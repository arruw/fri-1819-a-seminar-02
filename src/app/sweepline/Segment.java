package app.sweepline;

public class Segment implements Comparable<Segment>{
	public Point start = null;
	public Point end = null;
	
	Segment(Point s, Point e){
		start = s;
		end = e;
	}
	
	@Override
	public int compareTo(Segment that){

        // https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // CW   -> below
        // CCW  -> above

        var o = orientation(that.start, that.end, this.start);
        
        return o;// == 0 ? (that.end.y < this.start.y ? 1 : 2) : o;
    }
    
    private int orientation(Point p1, Point p2, Point p3) 
    { 
        if (p1.x == p2.x) {
            p2.x += 0.0001;
        }

        double val = (p2.y - p1.y) * (p3.x - p2.x) - 
                (p2.x - p1.x) * (p3.y - p2.y); 
    
        if (val == 0) return 0;  // colinear 
    
        return (val > 0)? 1: 2; // clock or counterclock wise 
    } 
}