package edu.iastate.cs228.hw1;

/**
 * 
 * @author Khanh Tran
 *
 */

/*
 * This class is to be extended by the Badger, Fox, and Rabbit classes.
 */
public abstract class Animal extends Living implements MyAge {
	protected int age; // age of the animal

	Animal(Plain p, int rows, int cols, int age) {
		super(p, rows, cols);
		this.age = age;

		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * 
	 * @return age of the animal
	 */
	public int myAge() {

		return age;
	}
}
