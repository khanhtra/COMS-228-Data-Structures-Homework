package edu.iastate.cs228.hw1;

/**
 *  
 * @author Khanh Tran 
 * COM S 228
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

/**
 * 
 * The plain is represented as a square grid of size width x width.
 *
 */
public class Plain {
	private int width; // grid size: width X width

	public Living[][] grid;

	/**
	 * Default constructor reads from a file
	 */
	public Plain(String inputFileName) throws FileNotFoundException {
		// TODO
		//
		// Assumption: The input file is in correct format.
		//
		// You may create the grid plain in the following steps:
		//
		// 1) Reads the first line to determine the width of the grid.
		File f = new File(inputFileName);
		Scanner in = new Scanner(f);
		Scanner in2 = new Scanner(f);
		String firstS = in.nextLine();
		while (in.hasNext()) {
			int count = 1;
			for (int i = 0; i < firstS.length(); i++) {
				if (firstS.charAt(i) == ' ') {
					count++;
				}
			}
			width = count;
			// 2) Creates a grid object.
			grid = new Living[width][width];
			// 3) Fills in the grid according to the input file.
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) { 
					String word = in2.next();
					if (word.contains("B")) {
						int age = word.charAt(1);
						grid[i][j] = new Badger(this, i, j, age);
					} else if (word.contains("F")) {
						int age = word.charAt(1);
						grid[i][j] = new Fox(this, i, j, age);
					}

					else if (word.contains("R")) {
						int age = word.charAt(1);
						grid[i][j] = new Rabbit(this, i, j, age);
					}

					else if (word.contains("E")) {
						grid[i][j] = new Empty(this, i, j);
						
					} else if (word.contains("G")) {
						grid[i][j] = new Grass(this, i, j);
					}
					
				}
			}
		}
		in.close();
		// Be sure to close the input file when you are done.
	}

	/**
	 * Constructor that builds a w x w grid without initializing it.
	 * 
	 * @param width
	 *            the grid
	 */
	public Plain(int w) {
		width = w;
		grid = new Living[w][w];

	}

	public int getWidth() {

		return width;
	}

	/**
	 * Initialize the plain by randomly assigning to every square of the grid one of
	 * BADGER, FOX, RABBIT, GRASS, or EMPTY.
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit() {
		Random generator = new Random();

		for (int i = 0; i < this.getWidth(); i++) {

			for (int j = 0; j < this.getWidth(); j++) {
				int rand = generator.nextInt(5);
				if (rand == 0) {
					grid[i][j] = new Badger(this, i, j, 0);
				}

				else if (rand == 1) {
					grid[i][j] = new Empty(this, i, j);
				}

				else if (rand == 2) {
					grid[i][j] = new Fox(this, i, j, 0);
				}

				else if (rand == 3) {
					grid[i][j] = new Grass(this, j, j);
				}

				else if (rand == 4) {
					grid[i][j] = new Rabbit(this, i, j, 0);
				}
			}
		}

	}

	/**
	 * Output the plain grid. For each square, output the first letter of the living
	 * form occupying the square. If the living form is an animal, then output the
	 * age of the animal followed by a blank space; otherwise, output two blanks.
	 */
	public String toString() {
		String plain = "";

		for (int i = 0; i < width; i++) {
			plain += "\n";
			for (int j = 0; j < width; j++) {
				if (grid[i][j].who() == State.BADGER) {
					plain += "B" + ((Animal) grid[i][j]).myAge() + " ";
				} else if (grid[i][j].who() == State.EMPTY) {
					plain += "E  ";
				} else if (grid[i][j].who() == State.FOX) {
					plain += "F" + ((Animal) grid[i][j]).myAge() + " ";
				} else if (grid[i][j].who() == State.GRASS) {
					plain += "G  ";
				} else if (grid[i][j].who() == State.RABBIT) {
					plain += "R" + ((Animal) grid[i][j]).myAge() + " ";
				}
			}
		}
		return plain;
	}

	/**
	 * Write the plain grid to an output file. Also useful for saving a randomly
	 * generated plain for debugging purpose.
	 * 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException {
		// TODO
		//
		// 1. Open the file.
		//
		// 2. Write to the file. The five life forms are represented by characters
		// B, E, F, G, R. Leave one blank space in between. Examples are given in
		// the project description.
		//
		// 3. Close the file.
	}
}
