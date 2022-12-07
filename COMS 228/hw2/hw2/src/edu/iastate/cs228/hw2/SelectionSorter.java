package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Khanh Tran 
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		algorithm = "selection sort";
		
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		
		 for (int i = 0; i < points.length - 1; i++) {
	            int index = i; // "jmin"
	            Point minPoint = points[i]; //minimum point
	            for (int j = i + 1; j < points.length; j++) {
	            	
	                if (pointComparator.compare(minPoint, points[j]) == 1) {
	                    index = j;
	                    minPoint = points[j];
	                }
	            }
	            swap(i, index);
	        }
		
	}	
}
