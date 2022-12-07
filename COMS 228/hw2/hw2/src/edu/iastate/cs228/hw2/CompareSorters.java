package edu.iastate.cs228.hw2;

/**
 *  
 * @author Khanh Tran 
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	
	 
	
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		
				
		// Initial lines
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning" + "\n");
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		//Trials should update
		int trials = 1;
		System.out.print("Trial 1: ");
		RotationalPointScanner[] scanners = new RotationalPointScanner[4];
		//Scanner for user input
		Scanner in = new Scanner (System.in);
		//Amount of generated points for random
		
		Random rand = new Random(); //rand object to be called in generate in case 1
		int input = 0; //initial cond. for switch case
		
		
		//Loop for inputs
		loop : while (in.hasNextInt()) {
			input = in.nextInt();
		
		switch(input) {
		
		//random integers
		case 1: 
			System.out.print("Enter number of random points: ");
			int randomAmount = in.nextInt();
			
			//random point array to be shared by other scanner arrays
			Point [] rando = generateRandomPoints(randomAmount, rand);
			
			
			//new rotat. objects assigned to scanners
			scanners[0] = new RotationalPointScanner(rando, Algorithm.InsertionSort);
		
			scanners[0].scan();
			scanners[0].draw();
			
			scanners[1] = new RotationalPointScanner(rando, Algorithm.MergeSort);
			scanners[1].scan();
			scanners[1].draw();
			
			scanners[2] = new RotationalPointScanner(rando, Algorithm.SelectionSort);
			scanners[2].scan();
			scanners[2].draw();
			
			scanners[3] = new RotationalPointScanner(rando, Algorithm.QuickSort);
			scanners[3].scan();
			scanners[3].draw();
			trials++;
			
			//stat line
			System.out.println("algorithm size time (ns)\r\n" + 
					"-----------------------------");
			//output stats for each scanners
			for(int i = 0; i <= 3; i++) {
				String result = scanners[i].stats();
				System.out.println(result);
				
			}
			System.out.println("-----------------------------" + "\n");
			System.out.print("Trial " + trials + ": ");
			
			break;
		
		//file input
		case 2:
			System.out.println("Points from a file");
			System.out.print("File name: ");
			String inputFile = in.next();
				
			scanners[0] = new RotationalPointScanner(inputFile, Algorithm.InsertionSort);
			scanners[0].scan();
			scanners[0].draw();
			
			scanners[1] = new RotationalPointScanner(inputFile, Algorithm.MergeSort);
			scanners[1].scan();
			scanners[1].draw();
			
			scanners[2] = new RotationalPointScanner(inputFile, Algorithm.SelectionSort);
			scanners[2].scan();
			scanners[2].draw();
			
			scanners[3] = new RotationalPointScanner(inputFile, Algorithm.QuickSort);
			scanners[3].scan();
			scanners[3].draw();
			trials++;
			
			//stat line
			System.out.println("algorithm size time (ns)\r\n" + 
					"-----------------------------");
			//output stats for each scanners
			for(int i = 0; i <= 3; i++) {
				String result = scanners[i].stats();
				System.out.println(result);
			}
			System.out.println("-----------------------------" + "\n");
			System.out.print("Trial " + trials + ": ");	
			
			break;
	
		
		//exit program
		case 3:
			System.out.println("Program has ended");
			break loop;
		//if not one of the options
		default:
			System.out.print("Please enter a valid choice (1, 2, or 3): ");
			break;
			
	}
		}
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 	
		
		//rand.setSeed(20);		//for testing purposes
		Point [] a = new Point[numPts];
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < numPts; i++) {
		
		int randomX = rand.nextInt(101) - 50;
		int randomY = rand.nextInt(101) - 50;
		a [i] = new Point(randomX, randomY);
		}
		return a; 
		
	}
	
}
