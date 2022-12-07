package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Khanh Tran
 * COM S 228
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with squares
 * inhabited by badgers, foxes, rabbits, grass, or none.
 *
 */
public class Wildlife {
	/**
	 * Update the new plain from the old plain in one cycle.
	 * 
	 * @param pOld
	 *            old plain
	 * @param pNew
	 *            new plain
	 */
	public static void updatePlain(Plain pOld, Plain pNew) {
		// TODO
		//
		// For every life form (i.e., a Living object) in the grid pOld, generate
		// a Living object in the grid pNew at the corresponding location such that
		// the former life form changes into the latter life form.
		//
		// Employ the method next() of the Living class.
		for (int i = 0; i < pOld.getWidth(); i++) {
			for (int j = 0; j < pOld.getWidth(); j++) {
				pNew.grid[i][j] = pOld.grid[i][j].next(pNew);
			}
		}
	}

	/**
	 * Repeatedly generates plains either randomly or from reading files. Over each
	 * plain, carries out an input number of cycles of evolution.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO
		//
		// Generate wildlife simulations repeatedly like shown in the
		// sample run in the project description.
		//
		// 1. Enter 1 to generate a random plain, 2 to read a plain from an input
		// file, and 3 to end the simulation. (An input file always ends with
		// the suffix .txt.)
		//

		Scanner in = new Scanner(System.in);
		System.out.println("Simulation of Wildlife of the Plain");
		System.out.println("keys: 1 (random plain)  2 (file input)  3 (exit)");
		int i = 1;
		while (i != 0) {
			try {
				int key = in.nextInt();
				System.out.println("Trial " + i + ": " + key);
				/*
				 * Couldn't get this case working, error messages show up but aren't correct because the files 
				 * can't be read from the Plain method.
				 */
				if (key == 2) {
					try {

						System.out.print("Please enter a file name: ");
						String a = in.next();
						Plain even = new Plain(a);
						Plain odd = new Plain(a);
						System.out.print("Enter number of cycles: ");
						int userCycles = in.nextInt();
						System.out.println();
						System.out.println("Initial plain: ");
						System.out.println();
						System.out.println(even.toString());
						System.out.println();
						System.out.println("Final plain: ");
						System.out.println();

						for (int j = 0; j < userCycles; j++) {
							if (j % 2 == 0) {
								updatePlain(even, odd);
							} else {
								updatePlain(odd, even);
							}
						}

						if (userCycles % 2 == 0) {
							System.out.println(even.toString());
						} else {
							System.out.println(odd.toString());
						}
						i++;
					} catch (Exception e) {
						System.out.println("No file name exists, please select a re-enter a key: ");
						key = 2;
					
					}
				} else if (key == 3) {
					System.out.println("Program has ended");
					break;
				} else if (key == 1) {
					System.out.println("Random plain");
					System.out.print("Enter grid width: ");
					int userWidth = in.nextInt();
					System.out.print("Enter number of cycles: ");
					int userCycle = in.nextInt();
					System.out.println("Initial plain: ");
					Plain even = new Plain(userWidth);
					even.randomInit();
					Plain odd = new Plain(userWidth);
					odd.randomInit();
					System.out.println(even.toString());
					System.out.println();
					System.out.println("Final Plain: ");
					System.out.println();

					for (int j = 0; j < userCycle; j++) {
						if (j % 2 == 0) {
							updatePlain(even, odd);
						} else {
							updatePlain(odd, even);
						}
					}

					if (userCycle % 2 == 0) {
						System.out.println(even.toString());
					} else {
						System.out.println(odd.toString());
					}
					i++;
				}
				else {
					System.out.println("Error: incorrect key, please restart program!");
					break;
				}
			} catch (Exception e) {
				System.out.println("Error: incorrect key, please restart program!");
				break;
			}
		}
		// 2. Print out standard messages as given in the project description.
		//
		// 3. For convenience, you may define two plains even and odd as below.
		// In an even numbered cycle (starting at zero), generate the plain
		// odd from the plain even; in an odd numbered cycle, generate even
		// from odd.

		Plain even; // the plain after an even number of cycles
		Plain odd; // the plain after an odd number of cycles

		// 4. Print out initial and final plains only. No intermediate plains should
		// appear in the standard output. (When debugging your program, you can
		// print intermediate plains.)
		//
		// 5. You may save some randomly generated plains as your own test cases.
		//
		// 6. It is not necessary to handle file input & output exceptions for this
		// project. Assume data in an input file to be correctly formated.
	}
}
