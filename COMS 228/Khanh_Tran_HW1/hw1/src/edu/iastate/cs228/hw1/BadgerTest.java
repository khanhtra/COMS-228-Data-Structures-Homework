package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BadgerTest {
@Test
public void whoTest () {
	Badger b = new Badger (null,1, 2, 3);
	assertEquals(State.BADGER, b.who());
}
@Test
public void testAge () {
	Badger b = new Badger (null,1, 2, 3);
	assertEquals(3, b.age);
}

@Test
public void aliveTestAge() {
	Plain test = new Plain(3);
	test.randomInit();
	Living tester;
	test.grid[0][0] = new Badger (test, 0, 0, 4);
	test.grid[0][1] = new Empty(test, 0, 1);
	test.grid[0][2] = new Fox(test, 0, 2, 1);
	test.grid[1][0] = new Fox(test, 1, 0, 0);
	test.grid[1][1] = new Rabbit(test, 1, 1, 2);
	test.grid[1][2] = new Grass(test, 1, 2); 
	test.grid[2][0] = new Fox(test, 2, 0, 1);
	test.grid[2][1] = new Grass(test, 2, 1);
	test.grid[2][2] = new Badger(test, 2, 2, 0);
	tester = test.grid[0][0].next(null);
	assertEquals("This cell should be empty", State.EMPTY, tester);
}
@Test 
public void nextAge () {
	Plain test = new Plain(2);
	test.grid[0][0] = new Badger(test, 0, 0, 2);
	test.grid[0][0].next(test);
	assertEquals (3, ((Animal)test.grid[0][0]).myAge());
}
}

