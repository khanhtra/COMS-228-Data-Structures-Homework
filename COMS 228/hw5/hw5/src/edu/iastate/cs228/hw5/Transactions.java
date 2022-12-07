package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Khanh Tran
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store.
 *
 */
public class Transactions {

	/**
	 * The main method generates a simulation of rental and return activities.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws AllCopiesRentedOutException
	 * @throws FilmNotInInventoryException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException,
			FilmNotInInventoryException, AllCopiesRentedOutException {

		//VideoStore vs = new VideoStore();
		//vs.addVideo("Titanic");
		// Scanner for user input
		Scanner in = new Scanner(System.in);
		VideoStore vs = new VideoStore("videoList1.txt");
		System.out.println("Transactions at a Video Store");
		System.out.println("keys:    1 (rent)      2 (bulk rent) \n" + "\t 3 (return)    4 (bulk return) \n"
				+ "\t 5 (summary)   6 (exit)");
		System.out.println();
		System.out.print("Transaction: ");
		int input = in.nextInt();
		in.nextLine();
		while (input != 6) {
			// Rent 1 copy
			if (input == 1) {
				System.out.print("Film to rent: ");
				String inputFilm = in.nextLine();
				try {
					vs.videoRent(inputFilm, 1);
				} catch (FilmNotInInventoryException e) {
					System.out.println("Film " + inputFilm + " is not in inventory");

				} catch (AllCopiesRentedOutException e) {
					System.out.println("Film " + inputFilm + " has been rented out");
				}
			}

			// Bulk Rent
			else if (input == 2) {
				System.out.print("Video file (rent): ");
				String inputFilm = in.next();

				try {
					vs.bulkRent(inputFilm);
				} catch (FilmNotInInventoryException e) {
					System.out.print("Film " + VideoStore.parseFilmName(inputFilm) + " is not in inventory");
					break;
				} catch (AllCopiesRentedOutException e) {
					System.out.println("Film " + VideoStore.parseFilmName(inputFilm) + " has been rented out");
					break;
				}

			}
			// Return
			else if (input == 3) {
				System.out.print("Film to return: ");
				String returnFilm = in.nextLine();
				try {
				vs.videoReturn(returnFilm, VideoStore.parseNumCopies(returnFilm));
			}
				catch (FilmNotInInventoryException e){
					System.out.println("Film: " + returnFilm + " is not in inventory, cannot return");
				}
			}
			// Bulk Return
			else if (input == 4) {
				System.out.print("Video file (return): ");
				String inputFilm = in.next();

				try {
					vs.bulkReturn(inputFilm);
				} catch (FilmNotInInventoryException e) {
					System.out.print("Film " + VideoStore.parseFilmName(inputFilm) + " is not in inventory");
					break;
				}

			}
			// Summary
			else if (input == 5) {
				System.out.println(vs.transactionsSummary());

			}
			// Exit
			else if (input == 6) {
				System.out.print("Program has ended!");
				break;
			}
			System.out.print("Transaction: ");
			input = in.nextInt();
			in.nextLine();
		}
	}

}
