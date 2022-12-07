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
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		algorithm = "quicksort";
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		//call two helper methods on "first and last"
		this.quickSortRec(0, points.length - 1);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		if(first >= last){ 
			return;
		}
		int pivotPos = partition(first, last);
		quickSortRec(first, pivotPos - 1);
		quickSortRec(pivotPos + 1,last);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		
		Point pivot = points[last];  
        int i = (first - 1); // index of smaller element 
        for (int j = first; j < last; j++) 
        { 
            // If current element is smaller than or 
            // equal to pivot 
            if (pointComparator.compare(points[j], pivot) < 1) //0 or -1 defined by comparator
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                Point temp = points[i]; 
                points[i] = points[j]; 
                points[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        Point temp = points[i + 1]; 
        points [i + 1] = points[last]; 
        points[last] = temp; 
  
        return i + 1; 
    } 
	
	// Other private methods in case you need ...
}
