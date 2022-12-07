package edu.iastate.cs228.hw2;


import org.junit.Before;
import org.junit.Test;


public class SelectionSorterTests {

	SelectionSorter ss;
	Point[] pts;
	Point[] ptsSortedX;
	
	@Before
	public void setUp() throws Exception {
		pts = new Point[3];
		pts[0] = new Point(-1,-1);
		pts[1] = new Point(-3,6);
		pts[2] = new Point(5,-9);
		
		ptsSortedX = new Point[3];
		ptsSortedX[0] = new Point(-3,6);
		ptsSortedX[1] = new Point(-1,-1);
		ptsSortedX[2] = new Point(5,-9);
		
		
		ss = new SelectionSorter(pts);
	}

	@Test
	public void testSort() {
		ss.setComparator(0);
		ss.sort();
		Point[] ptsSorted = new Point[pts.length];
		ss.getPoints(ptsSorted);
		for (int i = 0; i<ptsSorted.length; i++) {
			assert(ptsSorted[i].equals(ptsSortedX[i]));
		}
	}

}
