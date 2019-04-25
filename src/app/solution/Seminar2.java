package app.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Seminar2 {

	public static void main(String[] args) throws IOException {
		
		var points = new ArrayList<Point>(Files.lines(Paths.get(args[0])).skip(1).map(row -> {
			var parts = row.split(",");
			return new Point(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
		}).collect(Collectors.toList()));

		var lines = solution(points);

		lines.stream().forEach(System.out::println);
	}

	public static List<Line> solution(List<Point> points)	{
		var result = new HashSet<Line>();

		while(points.size() > 1) {
			var shell = grahmScan(points);

			var toRemove = new ArrayList<Point>();
			for(var i = 1; i < shell.size(); i++) {
				var start = shell.get(i-1);
				var end = shell.get(i);

				if(start.x < 0 && 0 < end.x) {
					result.add(new Line(start, end));
					points.remove(start);
					points.remove(end);
					break;
					// toRemove.add(start);
					// toRemove.add(end);
				} else if (start.x > 0 && 0 > end.x) {
					result.add(new Line(end, start));
					points.remove(start);
					points.remove(end);
					break;
					// toRemove.add(start);
					// toRemove.add(end);
				}
			}
			// points.removeAll(toRemove);

		}

		return result.stream().collect(Collectors.toList());
	}

	private static List<Point> grahmScan(List<Point> points) {
		var shell = new ArrayList<Point>();

		Point.p0 = points.stream()
			.collect(Collectors.minBy(Comparator.<Point, Double>comparing(p -> p.y)
				.thenComparing(p -> p.x))).get();
		points.remove(Point.p0);
		points.sort(Point::compareTo);
		points.add(0, Point.p0);

		for(var i = 0; i < points.size(); i++) {
			var point = points.get(i);
			
			var size = shell.size();
			while (size > 1 && direction(shell.get(size-2), shell.get(size-1), point) < 0) 
			{
				shell.remove(size-1);
				size--;
			}
			shell.add(point);
		}

		shell.add(shell.get(0));

		return shell;
	}

	private static double direction(Point i, Point j, Point k){
		double x1 = k.x - i.x;
		double y1 = k.y - i.y;
		double x2 = j.x - i.x;
		double y2 = j.y - i.y;
		return x1*y2-y1*x2;
	}
}

class Point implements Comparable<Point> {
	public double x;
	public double y;
	public int label;

	public static Point p0;

	Point(int label, double x, double y){
		this.label = label;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		// return String.format("p%d(%f, %f)", this.label, this.x, this.y);
		// return String.format("(%f, %f) ", this.x, this.y);
		return String.format("%d", this.label);
	}

	@Override
	public int compareTo(Point that) {
		double x1 = this.x - Point.p0.x;
		double y1 = this.y - Point.p0.y;
		double x2 = that.x - Point.p0.x;
		double y2 = that.y - Point.p0.y;

		double cross = x1*y2-y1*x2;

		return cross > 0 ? 1 : (cross < 0 ? -1 : 0);
	}
}

class Line {
	public Point start;
	public Point end;

	Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return this.start.label + " - " + this.end.label;
	}

	@Override
	public int hashCode() {
		int result = this.start.label;
		result = 31 * result + this.end.label;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Line))
			return false;

		var that = (Line)obj;
		return this.start.label == that.start.label && this.end.label == that.end.label;
	}
}