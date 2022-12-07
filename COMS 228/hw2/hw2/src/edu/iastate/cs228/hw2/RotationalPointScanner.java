package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 *@author Khanh Tran
 *
 */
public class RotationalPointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected String outputFileName; // "select.txt", "insert.txt", "merge.txt", or "quick.txt"

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[]. Set
	 * outputFileName.
	 * 
	 * @param pts
	 *            input array of points
	 * @throws IllegalArgumentException
	 *             if pts == null or pts.length == 0.
	 */
	public RotationalPointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		sortingAlgorithm = algo;
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}


	}

	/**
	 * This constructor reads points from a file. Set outputFileName.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 *             if the input file contains an odd number of integers
	 */
	protected RotationalPointScanner(String inputFileName, Algorithm algo)
			throws FileNotFoundException, InputMismatchException {
		if (inputFileName.length() % 2 != 0) {
			throw new InputMismatchException();
		}
		sortingAlgorithm = algo;
		File f = new File(inputFileName);
		Scanner in = new Scanner(f);
		ArrayList<Point> p = new ArrayList<Point>();
	
		while (in.hasNextInt()) {
		p.add(new Point(in.nextInt(),in.nextInt()));
	
		}
		outputFileName = algo.toString();
		points = p.toArray(new Point[p.size()]);
		in.close();
		}

	/**
	 * Carry out three rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates. d) Sort points[] again by the polar angle with respect to
	 * medianCoordinatePoint.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting. Copy the
	 * sorting result back onto the array points[] by calling the method getPoints()
	 * in AbstractSorter.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {

		AbstractSorter aSorter = null;

		if (sortingAlgorithm == Algorithm.InsertionSort) {

			aSorter = new InsertionSorter(points);

		}
		if (sortingAlgorithm == Algorithm.MergeSort) {

			aSorter = new MergeSorter(points);

		}
		if (sortingAlgorithm == Algorithm.SelectionSort) {

			aSorter = new SelectionSorter(points);

		}
		if (sortingAlgorithm == Algorithm.QuickSort) {

			aSorter = new QuickSorter(points);

		}
		
		long startTime = System.nanoTime();
		aSorter.setComparator(0);
		aSorter.sort();
		int xValue = aSorter.getMedian().getX();
		aSorter.setComparator(1);
		aSorter.sort();
		int yValue = aSorter.getMedian().getY();
		medianCoordinatePoint = new Point(xValue, yValue);
		aSorter.setReferencePoint(medianCoordinatePoint);
		aSorter.setComparator(2);
		aSorter.sort();
		aSorter.getPoints(points);
		long endTime = System.nanoTime();
		scanTime = endTime - startTime;

		
		

		// create an object to be referenced by aSorter according to sortingAlgorithm.
		// for each of the three
		// rounds of sorting, have aSorter do the following:
		//
		// a) call setComparator() with an argument 0, 1, or 2. in case it is 2, must
		// have made
		// the call setReferencePoint(medianCoordinatePoint) already.
		//
		// b) call sort().
		//
		// sum up the times spent on the three sorting rounds and set the instance
		// variable scanTime.

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		String result = "";
		
		result+= sortingAlgorithm + "	" + points.length + " " + scanTime;
				
		return result;
	}

	/**
	 * Write points[] after a call to scan(). When printed, the points will appear
	 * in order of polar angle with respect to medianCoordinatePoint with every
	 * point occupying a separate line. The x and y coordinates of the point are
	 * displayed on the same line with exactly one blank space in between.
	 */
	@Override
	public String toString() {
		//go through points [] and call toString from Point
		String result = "";
		for (int i = 0; i < points.length; i++) {
			result += points[i].toString() + "\n";
		}
		return result;
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException {
		PrintWriter writeFile = new PrintWriter(outputFileName);
		writeFile.println(this.toString());
		writeFile.close();
	}

	/**
	 * This method is called after each scan for visually check whether the result
	 * is correct. You just need to generate a list of points and a list of
	 * segments, depending on the value of sortByAngle, as detailed in Section 4.1.
	 * Then create a Plot object to call the method myFrame().
	 */
	public void draw() {
		int numSegs = points.length - 1; // number of segments to draw

		// Based on Section 4.1, generate the line segments to draw for display of the
		// sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order.
		// a) Create a line segment to connect every two adjacent points in Point[] that
		// are distinct
		// from each other.
		Segment[] segments = new Segment[numSegs];
		ArrayList<Segment> seg = new ArrayList<Segment>();
		
		for (int i = 0; i < numSegs; i++) {
			seg.add(segments[i] = new Segment(points[i], points[i + 1]));
		}

		// c) Create a line segment to connect medianCoordinatePoint to every point in
		// the array
		
		
		 for (int i = 0; i < numSegs; i++) {
			 seg.add(new Segment(medianCoordinatePoint, points[i]));
		 }
		
		String sort = null;
	
		//index 0 and last point
		seg.add(new Segment (points[0], points[points.length - 1]));
		segments = seg.toArray(new Segment[seg.size()]);
		
		
		switch (sortingAlgorithm) {
		case SelectionSort:
			sort = "Selection Sort";
			break;
		case InsertionSort:
			sort = "Insertion Sort";
			break;
		case MergeSort:
			sort = "Mergesort";
			break;
		case QuickSort:
			sort = "Quicksort";
			break;
		default:
			break;
		}

		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, sort);

	}

}
