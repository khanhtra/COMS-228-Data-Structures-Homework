package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		//call helper methods on points
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		
		int length = pts.length;
		int mid = pts.length / 2;
		//base case
		if (length <= 1) {
			return;
		}
		
		//split into halves
		Point [] firstHalf = new Point[mid];
		Point [] secondHalf = new Point[length - mid];
		
		for (int i = 0; i < mid; i++) {
			firstHalf[i] = pts[i];
		}
		
		for (int i = mid; i < length; i++) {
			secondHalf[i - mid] = pts[i];
		}
		mergeSortRec(firstHalf);
		mergeSortRec(secondHalf);
		
		//create temp array to store value of merge
		Point[] temp = merge(firstHalf, secondHalf);
		
		for(int i = 0; i < temp.length; i++) {
			
			pts[i] = temp[i];
			
		}
	}

	private Point[] merge(Point [] first, Point [] second) {
		

		Point merged [] = new Point [first.length + second.length];
		int i = 0;
		int j = 0;
		int count = 0;
		while(i < first.length && j < second.length) {
			if (pointComparator.compare(first[i], second[j]) > 0) { //first[i] <= second[j], p1 < p2
				//append first to merged
				merged[count++] = first[i];
				i++;
			}
			else {
				//append second
				merged[count++] = second[j];
				j++;
			}
		}
		if (i >= first.length) {
			//append second[j]...second[q - 1]  to merged
			while (j < second.length) {
				merged[count++] = second[j];
				j++;
			}
		}
			else {
				while (i < first.length) {
				//append first[j]...first[j - 1] to merged
				merged[count++] = first[i];
				i++;
			}
		}
		return merged;
		
	}
	// Other private methods in case you need ...
}
