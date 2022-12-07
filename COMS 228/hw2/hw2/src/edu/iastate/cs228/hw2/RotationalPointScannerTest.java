package edu.iastate.cs228.hw2;

import org.junit.Test; 
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;


public class RotationalPointScannerTest {
@Test
public void constructorTest() throws InputMismatchException, FileNotFoundException {
	RotationalPointScanner r = new RotationalPointScanner("points.txt", Algorithm.QuickSort);
	
}
}
