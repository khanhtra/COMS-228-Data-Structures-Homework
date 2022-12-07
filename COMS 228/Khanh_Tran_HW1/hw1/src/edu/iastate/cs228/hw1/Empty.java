package edu.iastate.cs228.hw1;

/**
 *  
 * @author
 *
 */

/**
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living {
	public Empty(Plain p, int r, int c) {
		super(p, r, c);
	}

	public State who() {
		// TODO
		return State.EMPTY;
	}

	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or
	 * Grass, or remain empty.
	 * 
	 * @param pNew
	 *            plain of the next life cycle.
	 * @return Living life form in the next cycle.
	 */
	public Living next(Plain pNew) {
		// a) Rabbit, if more than one neighboring Rabbit;
		// b) otherwise, Fox, if more than one neighboring Fox;
		// c) otherwise, Badger, if more than one neighboring Badger;
		// d) otherwise, Grass, if at least one neighboring Grass;
		// e) otherwise, Empty.
		this.census(neighbor);
		if (neighbor[RABBIT] > 1) {
			return new Rabbit(pNew, row, column, 0);
		} else if (neighbor[FOX] > 1) {
			return new Fox(pNew, row, column, 0);
		} else if (neighbor[BADGER] > 1) {
			return new Badger(pNew, row, column, 0);
		} else if (neighbor[GRASS] >= 1) {
			return new Grass(pNew, row, column);
		}

		return new Empty(pNew, row, column);
	}
}
