package edu.iastate.cs228.hw5;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.iastate.cs228.hw5.SplayTree.Node;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
public class testt {

	//Splay Tests
@Test
public void testCompareString() {
	Video a = new Video ("Bc");
	String aa = "As";
	int result = aa.compareTo(a.getFilm());
	assertEquals(-1, result);	
}
@Test 
public void construct() {
	SplayTree<Integer> a = new SplayTree<Integer> ();
	a.addBST(5);
	a.addBST(2);
	a.addBST(4);
	SplayTree<Integer> b = new SplayTree<Integer> (a);
	assertEquals(true, b.contains(4));
}
@Test
public void testContains() {
SplayTree<Integer> a = new SplayTree<Integer> ();
a.addBST(21);
a.addBST(99);
a.addBST(20);
a.addBST(15);
a.addBST(100);
assertEquals(5, a.size());
assertEquals(true, a.contains(15));
a.clear();
assertEquals(0, a.size);
assertEquals(false, a.contains(15));
}

@Test
public void stringTest() {
SplayTree<Integer> a = new SplayTree<Integer> ();
a.addBST(4);
a.addBST(6);
a.addBST(2);
a.addBST(5);
//System.out.println(a.toString());
//assertEquals("4\n    2\n", a.toString());
}
	//Video Tests
@Test
public void testVideo() throws IllegalArgumentException, AllCopiesRentedOutException {
	Video a = new Video ("Titanic x" , 5);
	a.addNumCopies(2);
	a.rentCopies(9);
	assertEquals("Titanic x (7:7)", a.toString());
}

	//Parse Methods
@Test
public void testParse() {
	String a = "Titanic (2)";
	assertEquals(2, VideoStore.parseNumCopies(a));
	assertEquals("Titanic", VideoStore.parseFilmName(a));
}
	//Video Store Tests
@Test
public void pleaseWork() throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {

	VideoStore a = new VideoStore();
//	a.addVideo("Titanic");
//	a.videoRent("Titanic", 1);
//	System.out.println(a.available("Titanic"));
	a.addVideo("Us", 4);
	a.addVideo("Us", 2);
	a.addVideo("Wow!", 1);
	System.out.println(a.inventoryList());
	VideoStore b = new VideoStore("videoList1.txt");
	System.out.println(b.inventoryList());
	assertEquals("Titanic (1)\r\n" + 
			"Us (4)", b.inventoryList());
	

}




}
