package app.sweepline;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		SweepLine sl = new SweepLine();
		List<Segment> input = new ArrayList<Segment>();
		//input.add(new Segment(new Point(,), new Point(,)));
		//Vhod za preverjanje pravilnega sortiranja kon�nih to�k
		//(�e izpisujete to�ke v for zanki znotraj funkcije AnySegmentsIntersect(input)
//		input.add(new Segment(new Point(2,3), new Point(10,5)));
//		input.add(new Segment(new Point(2,2), new Point(7,4)));
//		input.add(new Segment(new Point(2,4), new Point(3,2)));
//		input.add(new Segment(new Point(3,2), new Point(10,4)));
//		input.add(new Segment(new Point(7,5), new Point(11,11)));
//		input.add(new Segment(new Point(1,1), new Point(10,10)));
		
		
		//Testi
		System.out.println("Expected false, returned " + sl.AnySegmentsIntersect(input)); //false

		input.add(new Segment(new Point(1,1), new Point(2,2)));
		System.out.println("Expected false, returned " + sl.AnySegmentsIntersect(input)); //false
		
		input.add(new Segment(new Point(1,0), new Point(2,1)));
		System.out.println("Expected false, returned " + sl.AnySegmentsIntersect(input)); //false
		
		input.add(new Segment(new Point(1.5, 1), new Point(3,2)));
		System.out.println("Expected false, returned " + sl.AnySegmentsIntersect(input)); //false
		
		input.add(new Segment(new Point(0,0), new Point(1,0.1)));
		System.out.println("Expected false, returned " + sl.AnySegmentsIntersect(input)); //false
		
		input.add(new Segment(new Point(2,3), new Point(3,1)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
		input.remove(input.size()-1);
		input.add(new Segment(new Point(1.5,0), new Point(3,3)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
	
		input.remove(input.size()-1);
		input.add(new Segment(new Point(1.5,0), new Point(3,3)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
		input.remove(input.size()-1);
		input.add(new Segment(new Point(0,0), new Point(1,0.2)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
		input.remove(input.size()-1);
		input.add(new Segment(new Point(0,1), new Point(1,0)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
		input.remove(input.size()-1);
		input.add(new Segment(new Point(0,1), new Point(1,0.1)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
		input.remove(input.size()-1);
		input.add(new Segment(new Point(1,2), new Point(1.5,1.5)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
		input.remove(input.size()-1);
		input.add(new Segment(new Point(1.5,0.5), new Point(2,0)));
		System.out.println("Expected true, returned " + sl.AnySegmentsIntersect(input)); //true
		
	}
	
}
