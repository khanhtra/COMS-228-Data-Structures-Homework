package edu.iastate.cs228.hw1;

/**
 * 
 * @author Khanh Tran
 * COM S 228
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal {
	/**
	 * Creates a Rabbit object.
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
	public Rabbit(Plain p, int r, int c, int a) {
		super(p, r, c, a);
	}

	// Rabbit occupies the square.
	public State who() {
		return State.RABBIT;
	}

	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a
	 * fox.
	 * 
	 * @param pNew
	 *            plain of the next cycle
	 * @return Living new life form occupying the same square
	 */
	public Living next(Plain pNew) {
		// TODO
		//
	//a) Empty if the Rabbit's current age is 3;
	//b) otherwise, Empty if there is no Grass in the neighborhood (the rabbit needs food);
	//c) otherwise, Fox if in the neighborhood there are at least as many Foxes and Badgers
	//combined as Rabbits, and furthermore, if there are more Foxes than Badgers;
	//d) otherwise, Badger if there are more Badgers than Rabbits in the neighborhood;
	//e) otherwise, Rabbit (the rabbit will live on). 
		this.census(neighbor);
		if (this.myAge() == RABBIT_MAX_AGE) {
			return new Empty (pNew, row, column);
		}
		else if (neighbor [GRASS] == 0) {
			return new Empty (pNew, row, column);
		}
		else if (neighbor [RABBIT] == neighbor[BADGER] + neighbor [FOX] || neighbor [FOX] > neighbor [BADGER]) {
			return new Fox (pNew, row, column, age);
		}
		else if (neighbor [RABBIT] < neighbor [BADGER]) {
			return new Badger (pNew, row, column, age);
		}
		else {
			age++;
		}
		return new Rabbit(pNew, row, column, age);
	}
}

