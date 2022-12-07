package edu.iastate.cs228.hw1;

/**
 *  
 * @author
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten. 
 *
 */
public class Grass extends Living 
{
	public Grass (Plain p, int r, int c) 
	{
		super (p, r, c); 
	}
	
	public State who()
	{
		// TODO  
		return State.GRASS; 
	}
	
	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
	 */
	public Living next(Plain pNew)
	{
		//a) Empty if at least three times as many Rabbits as Grasses in the neighborhood;
		//b) otherwise, Rabbit if there are at least three Rabbits in the neighborhood;
		//c) otherwise, Grass. 
		this.census(neighbor);
		if (neighbor [RABBIT] * 3 >= neighbor [GRASS]) {
			return new Empty (pNew, row, column);
		}
		else if (neighbor [RABBIT] >= 3) {
			return new Rabbit (pNew, row, column, 0);
		}
		return new Grass (pNew, row, column); 
	}
}
