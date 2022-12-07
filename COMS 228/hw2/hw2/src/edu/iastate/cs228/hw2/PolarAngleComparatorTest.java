package edu.iastate.cs228.hw2;

/**
 *  
 * @author Khanh Tran
 *
 */

import org.junit.Test; 

import static org.junit.Assert.*;


public class PolarAngleComparatorTest {
	
	private Point p1;
	private Point p2;
	private Point pr;
	private PolarAngleComparator comp;
	
	//compareDistance Tests
	
	@Test
	public void testCompareDistance0() {
		p1 = new Point(2,4);
		p2 = new Point(-2,4);
		pr = new Point(0,0);
		comp = new PolarAngleComparator(pr);
		int result = comp.compareDistance(p1, p2);
		assertEquals(0, result);	
		
	}
	@Test
	public void testCompareDistanceNeg1() {
		p1 = new Point(0,2);
		p2 = new Point(-2,4);
		pr = new Point(0,0);
		comp = new PolarAngleComparator(pr);
		int result = comp.compareDistance(p1, p2);
		assertEquals(-1, result);	
	}
	
	@Test
	public void testCompareDistancePos1() {
		p1 = new Point(2,4);
		p2 = new Point(0,2);
		pr = new Point(0,0);
		comp = new PolarAngleComparator(pr);
		int result = comp.compareDistance(p1, p2);
		assertEquals(1, result);	
		
	}
	
	//comparePolarAngle Tests
	
	@Test 
	public void testComparePolarAngle0Con1() {
		p1 = new Point(2,4);
		p2 = new Point(3,6);
		pr = new Point(0,0);
		
		comp = new PolarAngleComparator(pr);
		//test zero conditions
		int result = comp.comparePolarAngle(p1, p1);
		assertEquals(0, result);
	}
		
		@Test
		public void testComparePolarAngle0Con2() {
			
			p1 = new Point(2,4);
			p2 = new Point(3,6);
			pr = new Point(0,0);
			comp = new PolarAngleComparator(pr);
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(0, result);
		}
		
		@Test
		public void testComparePolarAngleNeg1() {
		//test 1 conditions
		//p1 == refPoint Case

			p1 = new Point(1,4);
			p2 = new Point(2,6);
			pr = new Point(1,4);
			
			comp = new PolarAngleComparator(pr);
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		}
		
		@Test 
		public void testComparePolarAngleNeg1Con1A() {
		//p2 not equal and p1.y < referencePoint.y and p2.y < referencePoint.y, 
		//and the cross product of p1 - referencePoint and p2 - referencePoint is positive. 
			
		p1 = new Point(5,1);
		p2 = new Point(5,2);
		pr = new Point (4,7);
		comp = new PolarAngleComparator(pr);
		
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		}
		
		//p1.y == referencePoint.y cases
		@Test 
		public void testComparePolarAngleNeg1Con2A() {
		//p2.y < referencePoint.y case
			
		p1 = new Point(5,7);
		p2 = new Point(5,2);
		pr = new Point (4,7);
		comp = new PolarAngleComparator(pr);
		
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		
		}
		@Test 
		public void testComparePolarAngleNeg1Con2B() {
		//p2.y == referencePoint.y and p1.x > referencePoint.x and p2.x < referencePoint.x case
		p1 = new Point(9,7);
		p2 = new Point(5,7);
		pr = new Point (8,7);
		comp = new PolarAngleComparator(pr);
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		}
		
		@Test 
		public void testComparePolarAngleNeg1Con2C() {
		//p2.y > referencePoint.y and p1.x > referencePoint.x case
		p1 = new Point(9,7);
		p2 = new Point(5,8);
		pr = new Point (8,7);
		comp = new PolarAngleComparator(pr);
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		}
		
		//p1.y > referencePoint.y cases
		@Test 
		public void testComparePolarAngleNeg1Con3A() {
		//p2.y > referencePoint.y and the cross product of p1 - referencePoint and p2 - referencePoint is positive case
		p1 = new Point(9,7);
		p2 = new Point(5,6);
		pr = new Point (4,4);
		comp = new PolarAngleComparator(pr);	
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		}
		@Test 
		public void testComparePolarAngleNeg1Con3B() {
		//p2.y == referencePoint.y and p2.x < referencePoint.x. 
		p1 = new Point(2,7);
		p2 = new Point(3,4);
		pr = new Point (4,4);
		comp = new PolarAngleComparator(pr);	
		int result = comp.comparePolarAngle(p1, p2);
		assertEquals(-1, result);
		}
		
		//compare Tests
		@Test
		public void testCompare0() {
			p1 = new Point(1,2);
			p2 = p1;
			pr = new Point (4,4);
			comp = new PolarAngleComparator(pr);
			int result = comp.compare(p1, p2);
			assertEquals(0, result);
		}
		@Test
		public void testCompareNeg1ASit1() {
			p1 = new Point(1,6);
			p2 = new Point (1,8);
			pr = new Point (-1,5);
			comp = new PolarAngleComparator(pr);
			int result = comp.compare(p1, p2);
			assertEquals(-1, result);
		}
		@Test
		public void testCompareNeg1ASit2() {
			p1 = new Point(-1,8);
			p2 = new Point (-1,6);
			pr = new Point (1,5);
			comp = new PolarAngleComparator(pr);
			int result = comp.compare(p1, p2);
			assertEquals(-1, result);
		}
		@Test
		public void testCompareNeg1BSit1() {
			p1 = new Point(2,2);
			p2 = new Point (4,4);
			pr = new Point (0,0);
			comp = new PolarAngleComparator(pr);
			int result = comp.compare(p1, p2);
			assertEquals(-1, result);
		}
		@Test
		public void testCompareNeg1BSit2() {
			p1 = new Point(4,4);
			p2 = new Point (2,2);
			pr = new Point (0,0);
			comp = new PolarAngleComparator(pr);
			int result = comp.compare(p1, p2);
			assertEquals(1, result);
		}
	
}