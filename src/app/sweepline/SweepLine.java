package app.sweepline;

import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class SweepLine {
	
	/*
	 * Predpostavka: Segmenti so vedno pravilno vneseni.
	 * Segment.start je vedno levo od Segment.end
	 * Ni vertikalnih segmentov.
	 * */
	
	public boolean AnySegmentsIntersect(List<Segment> S){
		// http://jeffe.cs.illinois.edu/teaching/373/notes/x06-sweepline.pdf
				
		var K = new PriorityQueue<ExtPoint>();
		S.forEach(x -> {
			K.add(new ExtPoint(x.start, true, x));
			K.add(new ExtPoint(x.end, false, x));
		});

		var T = new TreeSet<Segment>();

		while(!K.isEmpty()) {
			var k = K.remove();

			if(k.right) {
				var above = T.higher(k.segment);
				var below = T.lower(k.segment);

				if(above != null && below != null && SegmentsIntersect(above.start, above.end, below.start, below.end)) {
					return true;
				}

				T.remove(k.segment);

			} else {
				T.add(k.segment);

				var c = T.higher(k.segment);
				c = c == null ? T.lower(k.segment) : c;
				if(c != null && SegmentsIntersect(c.start, c.end, k.segment.start, k.segment.end)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/* 
	 * Ali se daljici p1p2 in p3p4 sekata?
	 * */
	private boolean SegmentsIntersect(Point p1, Point p2, Point p3, Point p4){
		//Relativne smeri tock daljice, glede na drugo daljico.
		double d1 = Direction(p3, p4, p1);  //Ali p1 lezi levo ali desno od p3p4
		double d2 = Direction(p3, p4, p2);  //Ali p2 lezi levo ali desno od p3p4
		double d3 = Direction(p1, p2, p3);  //Ali p3 lezi levo ali desno od p1p2 
		double d4 = Direction(p1, p2, p4);  //Ali p4 lezi levo ali desno od p1p2
		
		if (	((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
				((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))   ) return true;
		
		if (d1 == 0 && OnSegment(p3, p4, p1)) return true;
		if (d2 == 0 && OnSegment(p3, p4, p2)) return true;
	    if (d3 == 0 && OnSegment(p1, p2, p3)) return true;
	    if (d4 == 0 && OnSegment(p1, p2, p4)) return true;
		return false;
	}
	
	/*
	 * Predpostavka: Tocke i,j,k so kolinearne!
	 * Ali tocka k, lezi na premici ij. 
	 * */
	private boolean OnSegment(Point i, Point j, Point k){
		if (	(Math.min(i.x, j.x) <= k.x && k.x <= Math.max(i.x, j.x)) &&
				(Math.min(i.y, j.y) <= k.y && k.y <= Math.max(i.y, j.y)) ) return true;
		return false;
	}
	
	/*
	 * Relativna smer daljice ij glede na daljico ik
	 * return: 
	 * 0 -> Daljici sta vzporedni -> Tocke so kolinearne
	 * + -> Daljica ij lezi desno od ik
	 * - -> Daljica ij lezi levo od ik
	 */
	private double Direction(Point i, Point j, Point k){
		double x1 = k.x - i.x;
		double y1 = k.y - i.y;
		double x2 = j.x - i.x;
		double y2 = j.y - i.y;
		return x1*y2-y1*x2;
	}
	
	private class ExtPoint extends Point implements Comparable<ExtPoint>{
		
		public boolean right;
		public Segment segment;
		
		ExtPoint(double x_value, double y_value, boolean rightEndpoint, Segment s){
			super(x_value, y_value);
			right = rightEndpoint;
			segment = s;
		}
		
		ExtPoint(Point p, boolean rightEndpoint, Segment s){
			super(p.x, p.y);
			right = rightEndpoint;
			segment = s;
		}
		
		@Override
	    public int compareTo(ExtPoint that){
			//Najprej glej po x
			if(this.x < that.x) return -1;
			if(this.x > that.x) return 1;
			//Glej po levem/desnem krajiscu (leva krajisca so manjsa)
			if(!this.right && that.right) return -1;
			if(this.right && !that.right) return 1;
			//Glej po y (nizja so manjsa)
			if(this.y < that.y) return -1;
			if(this.y > that.y) return 1;
	        return 0; //Tocki lezita ena na drugi in sta obe levi/desni krajisci daljice.
	    }
	}
}


