package edu.iastate.cs228.hw1;

/**
 *  
 * @author Khanh Tran
 * COM S 228
 */

/**
 * A fox eats rabbits and competes against a badger.
 */
public class Fox extends Animal {
	/**
	 * Constructor
	 * 
	 * @param p:
	 *            plain
	 * @param r:
	 *            row position
	 * @param c:
	 *            column position
	 * @param a:
	 *            age
	 */
	public Fox(Plain p, int r, int c, int a) {
		super(p, r, c, a);
	}

	/**
	 * A fox occupies the square.
	 */
	public State who() {

		return State.FOX;
	}

	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior
	 * badgers.
	 * 
	 * @param pNew
	 *            plain of the next cycle
	 * @return Living life form occupying the square in the next cycle.
	 */
	public Living next(Plain pNew) {
		// TODO
		//a) Empty if the Fox is currently at age 6;
		//b) otherwise, Badger, if there are more Badgers than Foxes in the neighborhood;
		//c) otherwise, Empty, if Badgers and Foxes together outnumber Rabbits in the neighborhood;
		//d) otherwise, Fox (the fox will live on).
		
		this.census(neighbor);
		if (this.myAge() == FOX_MAX_AGE) {
			return new Empty (pNew, row, column);
		}
		else if (neighbor [BADGER] > neighbor [FOX]) {
			return new Badger (pNew, row, column, age);
		}
		else if (neighbor [BADGER] + neighbor [2] > neighbor [RABBIT]) {
			return new Empty (pNew, row, column);
		}
		else {
			age++;
		}
		return new Fox(pNew, row, column, age);
	}
}
