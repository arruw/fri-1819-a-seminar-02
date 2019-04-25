package app.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

public class Tests {

	public static void main(String[] args) throws Exception {

		// test("1",generateData(10, 10));
		// test("2",generateData(10, 20));
		// test("3",generateData(10, 30));
		// test("4",generateData(10, 40));
		// test("5",generateData(10, 50));
		//test("6", generateData(10, 60));


		test("5", getPoints("/home/matjazmav/Projects/A-S02/inputs/tests/5.txt"));
	}

	private static boolean test(String label, ArrayList<Point> points) {
		System.out.printf("=== %s ===\n", label);

		var success = true;
		var size = points.size();
		var limits = getLimits(points);
		var lines = Seminar2.solution((ArrayList<Point>) points.clone());

		lines.stream().forEach(System.out::println);

		var p = new JavaPlot();
		plotPoints(p, points);
		plotLines(p, lines, limits);
		plot(p, limits);

		// // Check length
		// if (lines.size() != size / 2) {
		// 	success = false;
		// 	System.out.printf("[ERROR] Lenght %d expected %d.\n", lines.size(), size / 2);
		// }
		// // Check number of edges
		// var edges = new int[size];
		// lines.stream().forEach(l -> {
		// 	edges[l.start.label]++;
		// 	edges[l.end.label]++;
		// });
		// for (var i = 0; i < size; i++) {
		// 	if (edges[i] != 1) {
		// 		success = false;
		// 		System.out.printf("[ERROR] Edges from %d: have %d expected %d.\n", i, edges[i], 1);
		// 	}
		// }

		System.out.println();
		return success;
	}

	private static ArrayList<Point> getPoints(String input) throws IOException {
		return new ArrayList<Point>(Files.lines(Paths.get(input)).skip(1).map(row -> {
			var parts = row.split(",");
			return new Point(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
		}).collect(Collectors.toList()));
	}

	private static int[] getLimits(ArrayList<Point> points) {
		var limits = new int[4];

		limits[0] = (int)Math.round(points.stream()
			.collect(Collectors.minBy(Comparator.<Point, Double>comparing(p -> p.x))).get().x)-10;
		limits[1] = (int)Math.round(points.stream()
			.collect(Collectors.minBy(Comparator.<Point, Double>comparing(p -> p.y))).get().y)-10;
		limits[2] = (int)Math.round(points.stream()
			.collect(Collectors.maxBy(Comparator.<Point, Double>comparing(p -> p.x))).get().x)+10;
		limits[3] = (int)Math.round(points.stream()
			.collect(Collectors.maxBy(Comparator.<Point, Double>comparing(p -> p.y))).get().y)+10;

		return limits;
	}

	private static ArrayList<Point> generateData(int tock, int seed){
		if(tock < 3) {
			System.out.println("Stevilo točk mora biti vsaj 3.");
			System.exit(0);
		}

		Point[] seznam = new Point[tock*2];
		Random generator = new Random(seed);
		for(int i = 0; i < tock; i++) {
			double x1 = generator.nextDouble()*-50;
			double y1 = generator.nextDouble()*100-50;
			seznam[i*2] = new Point(i*2, x1, y1);

			double x2 = generator.nextDouble()*50;
			double y2 = generator.nextDouble()*100-50;
			seznam[i*2+1] = new Point(i*2+1, x2, y2);
		}
		
		return new ArrayList<Point>(Arrays.asList(seznam));
	}

	private static void plotLines(JavaPlot p, List<Line> lines, int[] limits) {
		// Plot x = 0
		var split_ds = new DataSetPlot(new int[][] { {0, limits[1]}, {0, limits[3]} });
		split_ds.setTitle("");
		split_ds.setPlotStyle(new PlotStyle(Style.LINES));
		p.addPlot(split_ds);

		// Plot lines
		lines.stream().forEach(l -> {
			var ds = new DataSetPlot(new double[][] { {l.start.x, l.start.y}, {l.end.x, l.end.y} });
			ds.setTitle("");
			var style = new PlotStyle(Style.LINES);
			ds.setPlotStyle(style);
			p.addPlot(ds);
		});
	}

	private static void plotShell(JavaPlot p, List<Point> shell) {
		var shell_data = new double[shell.size()][2];
		for (var i = 0; i < shell.size(); i++) {
			shell_data[i][0] = shell.get(i).x;
			shell_data[i][1] = shell.get(i).y;
		}
		var shell_ds = new DataSetPlot(shell_data);
		shell_ds.setTitle("");
		shell_ds.setPlotStyle(new PlotStyle(Style.LINES));
		p.addPlot(shell_ds);
	}

	private static void plotPoints(JavaPlot p, List<Point> points) {
		var data = new double[points.size()][2];
		for (var i = 0; i < points.size(); i++) {
			data[i][0] = points.get(i).x;
			data[i][1] = points.get(i).y;
		}
		var ds = new DataSetPlot(data);
		ds.setTitle("");
		var style = new PlotStyle(Style.POINTS);
		ds.setPlotStyle(style);
		p.addPlot(ds);
	}

	private static void plot(JavaPlot p, int[] limits) {
		p.set("xrange", "["+Integer.toString(limits[0])+" : "+Integer.toString(limits[2])+"]");
		p.set("yrange", "["+Integer.toString(limits[1])+" : "+Integer.toString(limits[3])+"]");
		p.plot();
	}
}