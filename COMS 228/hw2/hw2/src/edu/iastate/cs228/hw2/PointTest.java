package edu.iastate.cs228.hw2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PointTest {

	@Test
	public void equalsTest() {
		Point a = new Point(1, 4);
		a.getX();
		a.getY();
		int result = 5;
		Point b = new Point(1,4);
		boolean test = a.equals(b);
		assertEquals(true, test);
		assertEquals(5, result);
	}
	@Test
	public void stringTest() {
		Point a = new Point(1, 4);
		String result = a.toString();
		assertEquals("(1, 4)", result);
	 
	}
	@Test
	public void compareTest() {
		Point p1 = new Point(1, 2);
		Point p2 = new Point(5, 4);
		int result = p1.compareTo(p2);
		assertEquals(-1, result);
	}

}
