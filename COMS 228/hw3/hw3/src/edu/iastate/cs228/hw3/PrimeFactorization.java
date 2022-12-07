package edu.iastate.cs228.hw3;

/**
 *  
 * @author Khanh Tran  
 *
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class PrimeFactorization implements Iterable<PrimeFactor> {
	private static final long OVERFLOW = -1;
	private long value; // the factored integer
						// it is set to OVERFLOW when the number is greater than 2^63-1, the
						// largest number representable by the type long.

	/**
	 * Reference to dummy node at the head.
	 */
	private Node head;

	/**
	 * Reference to dummy node at the tail.
	 */
	private Node tail;

	private int size; // number of distinct prime factors

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor constructs an empty list to represent the number 1.
	 * 
	 * Combined with the add() method, it can be used to create a prime
	 * factorization.
	 */
	public PrimeFactorization() {
		// empty head and tail nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		size = 0;
		value = 1;

	}

	/**
	 * Obtains the prime factorization of n and creates a doubly linked list to
	 * store the result. Follows the direct search factorization algorithm in
	 * Section 1.2 of the project description.
	 * 
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public PrimeFactorization(long n) throws IllegalArgumentException {
		int multi = 0;
		long temp = n;

		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		value = n;
		size = 0;

		if (n < 1) {
			throw new IllegalArgumentException();
		}
		// Go through prime numbers
		for (int i = 2; i <= temp; i++) {
			multi = 0; // Reset multiplicity
			while (temp % i == 0) {
				multi++;
				temp = temp / i;
			}
			// Add multiplicity to object list with the condition that multi. is not 0
			if (multi != 0) {
				this.add(i, multi);
			}
			if (temp == 1) {
				break;
			}
		}
	}

	/**
	 * Copy constructor. It is unnecessary to verify the primality of the numbers in
	 * the list.
	 * 
	 * @param pf
	 */
	public PrimeFactorization(PrimeFactorization pf) {
		size = pf.size;
		head = pf.head;
		tail = pf.tail;
		value = pf.value;
		head.next = tail;
		tail.previous = head;

		PrimeFactorizationIterator pfIter = pf.iterator();
		while (pfIter.hasNext()) {
			PrimeFactor pfCopy = pfIter.next();
			this.add(pfCopy.prime, pfCopy.multiplicity);
		}
	}

	/**
	 * Constructs a factorization from an array of prime factors. Useful when the
	 * number is too large to be represented even as a long integer.
	 * 
	 * @param pflist
	 */
	public PrimeFactorization(PrimeFactor[] pfList) {
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		for (PrimeFactor a : pfList) {
			this.add(a.prime, a.multiplicity);
		}
	}

	// --------------
	// Primality Test
	// --------------

	/**
	 * Test if a number is a prime or not. Check iteratively from 2 to the largest
	 * integer not exceeding the square root of n to see if it divides n.
	 * 
	 * @param n
	 * @return true if n is a prime false otherwise
	 */
	public static boolean isPrime(long n) {
		// alg. for prime, return false if composite number
		if (n == 2) {
			return true;
		}
		if (n == 1) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	// ---------------------------
	// Multiplication and Division
	// ---------------------------

	/**
	 * Multiplies the integer v represented by this object with another number n.
	 * Note that v may be too large (in which case this.value == OVERFLOW). You can
	 * do this in one loop: Factor n and traverse the doubly linked list
	 * simultaneously. For details refer to Section 3.1 in the project description.
	 * Store the prime factorization of the product. Update value and size.
	 * 
	 * @param n
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public void multiply(long n) throws IllegalArgumentException {
		// In the first case, increment the multiplicity field of that node. In the
		// second case, create a new node
		// for this factor and add it to the linked list before the larger prime. If the
		// cursor reaches the end of the
		// list, simply add the new node as the last node.
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		long result = 0;
		result = n * value;
		PrimeFactorization pf = new PrimeFactorization(result);
		this.clearList();
		PrimeFactorizationIterator iter = pf.iterator();
		while (iter.hasNext()) {
			PrimeFactor pff = iter.next();
			this.add(pff.prime, pff.multiplicity);
		}
		updateValue();

	}

	/**
	 * Multiplies the represented integer v with another number in the factorization
	 * form. Traverse both linked lists and store the result in this list object.
	 * See Section 3.1 in the project description for details of algorithm.
	 * 
	 * @param pf
	 */
	public void multiply(PrimeFactorization pf) {
		long result = pf.value * this.value;
		PrimeFactorization r = new PrimeFactorization(result);
		PrimeFactorizationIterator iter = r.iterator();
		while (iter.hasNext()) {
			PrimeFactor pff = iter.next();
			this.add(pff.prime, pff.multiplicity);
		}
		updateValue();

	}

	/**
	 * Multiplies the integers represented by two PrimeFactorization objects.
	 * 
	 * @param pf1
	 * @param pf2
	 * @return object of PrimeFactorization to represent the product
	 */
	public static PrimeFactorization multiply(PrimeFactorization pf1, PrimeFactorization pf2) {
		// And returns a new object storing the product of the two numbers represented
		// by the first two objects.
		return new PrimeFactorization(Math.multiplyExact(pf1.value(), pf2.value()));
	}

	/**
	 * Divides the represented integer v by n. Make updates to the list, value, size
	 * if divisible. No update otherwise. Refer to Section 3.2 in the project
	 * description for details.
	 * 
	 * @param n
	 * @return true if divisible false if not divisible
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 */
	public boolean dividedBy(long n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		if (value != -1 && value < n) {
			return false;
		}
		// Constructs a PrimeFactorization object for n, and then calls the second
		// dividedBy() method
		// with the constructed object as the argument.
		PrimeFactorization p = new PrimeFactorization(n);
		this.dividedBy(p);
		return true;
	}

	/**
	 * Division where the divisor is represented in the factorization form. Update
	 * the linked list of this object accordingly by removing those nodes housing
	 * prime factors that disappear after the division. No update if this number is
	 * not divisible by pf. Algorithm details are given in Section 3.2.
	 * 
	 * @param pf
	 * @return true if divisible by pf false otherwise
	 */
	public boolean dividedBy(PrimeFactorization pf) {
		// make a copy of the linked list of this object. Traverse the list copy and the
		// list of the object
		// pf using two separate iterators, say, iterCopy and iterPf, respectively
		PrimeFactorizationIterator iterCopy = this.iterator();
		PrimeFactorizationIterator iterPf = pf.iterator();
		while (iterPf.hasNext()) {
			PrimeFactor copy = iterCopy.next();
			PrimeFactor Pf = iterPf.next();
			if (!iterCopy.hasNext() && iterPf.hasNext()) {
				return false;
			}

		}

		return true;
	}

	/**
	 * Divide the integer represented by the object pf1 by that represented by the
	 * object pf2. Return a new object representing the quotient if divisible. Do
	 * not make changes to pf1 and pf2. No update if the first number is not
	 * divisible by the second one.
	 * 
	 * @param pf1
	 * @param pf2
	 * @return quotient as a new PrimeFactorization object if divisible null
	 *         otherwise
	 */
	public static PrimeFactorization dividedBy(PrimeFactorization pf1, PrimeFactorization pf2) {
		// TODO
		return null;
	}

	// -------------------------------------------------
	// Greatest Common Divisor and Least Common Multiple
	// -------------------------------------------------

	/**
	 * Computes the greatest common divisor (gcd) of the represented integer v and
	 * an input integer n. Returns the result as a PrimeFactor object. Calls the
	 * method Euclidean() if this.value != OVERFLOW.
	 * 
	 * It is more efficient to factorize the gcd than n, which can be much greater.
	 * 
	 * @param n
	 * @return prime factorization of gcd
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public PrimeFactorization gcd(long n) throws IllegalArgumentException {
		long gcd = 0;
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		if (value != OVERFLOW) {
			gcd = Euclidean(value, n);
		}

		return new PrimeFactorization(gcd);
	}

	/**
	 * Implements the Euclidean algorithm to compute the gcd of two natural numbers
	 * m and n. The algorithm is described in Section 4.1 of the project
	 * description.
	 * 
	 * @param m
	 * @param n
	 * @return gcd of m and n.
	 * @throws IllegalArgumentException
	 *             if m < 1 or n < 1
	 */
	public static long Euclidean(long m, long n) throws IllegalArgumentException {
		if (m < 1 || n < 1) {
			throw new IllegalArgumentException();
		}
		// Base
		if (n == 0) {
			return m;
		}
		return Euclidean(n, m % n);
	}

	/**
	 * Computes the gcd of the values represented by this object and pf by
	 * traversing the two lists. No direct computation involving value and pf.value.
	 * Refer to Section 4.2 in the project description on how to proceed.
	 * 
	 * @param pf
	 * @return prime factorization of the gcd
	 */
	public PrimeFactorization gcd(PrimeFactorization pf) {
		// Traverse the linked lists of the two objects to find the common prime factors
		// of the two numbers.

		PrimeFactorization result = new PrimeFactorization();
		PrimeFactorizationIterator pfIter = pf.iterator();
		PrimeFactorizationIterator thisObject = this.iterator();
		// Go through list to check
		while (pfIter.hasNext()) {
			// Store values using pfIter
			PrimeFactor current = pfIter.next();
			PrimeFactor current2 = thisObject.next();

			// For every common prime factor 𝑝, create a new node to store 𝑝 and
			// min(𝛼1,𝛼2),
			// the minimum of the multiplicities 𝛼1 and 𝛼2 of 𝑝 stored in the two lists.

			if (current.prime == current2.prime) {
				int storedPrime = current.prime;
				int storedMulti = Math.min(current.multiplicity, current2.multiplicity);
				result.add(storedPrime, storedMulti);
			}

		}
		// Link all the new nodes together to construct a PrimeFactorization object to
		// represent the GCD.

		return result;
	}

	/**
	 * 
	 * @param pf1
	 * @param pf2
	 * @return prime factorization of the gcd of two numbers represented by pf1 and
	 *         pf2
	 */
	public static PrimeFactorization gcd(PrimeFactorization pf1, PrimeFactorization pf2) {
		// Go through pf1 and pf2 to get values
		PrimeFactorizationIterator pf1Iter = pf1.iterator();
		PrimeFactorizationIterator pf2Iter = pf2.iterator();
		long pf1Result = 0;
		long pf2Result = 0;
		while (pf1Iter.hasNext()) {
			PrimeFactor a = pf1Iter.next();
			pf1Result += a.prime ^ a.multiplicity;
		}
		while (pf2Iter.hasNext()) {
			PrimeFactor b = pf1Iter.next();
			pf2Result += b.prime ^ b.multiplicity;
		}
		long result = Euclidean(pf1Result, pf2Result);

		return new PrimeFactorization(result);
	}

	/**
	 * Computes the least common multiple (lcm) of the two integers represented by
	 * this object and pf. The list-based algorithm is given in Section 4.3 in the
	 * project description.
	 * 
	 * @param pf
	 * @return factored least common multiple
	 */
	public PrimeFactorization lcm(PrimeFactorization pf) {
		// TODO
		return null;
	}

	/**
	 * Computes the least common multiple of the represented integer v and an
	 * integer n. Construct a PrimeFactors object using n and then call the lcm()
	 * method above. Calls the first lcm() method.
	 * 
	 * @param n
	 * @return factored least common multiple
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public PrimeFactorization lcm(long n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		return null;
	}

	/**
	 * Computes the least common multiple of the integers represented by pf1 and
	 * pf2.
	 * 
	 * @param pf1
	 * @param pf2
	 * @return prime factorization of the lcm of two numbers represented by pf1 and
	 *         pf2
	 */
	public static PrimeFactorization lcm(PrimeFactorization pf1, PrimeFactorization pf2) {
		// TODO
		return null;
	}

	// ------------
	// List Methods
	// ------------

	/**
	 * Traverses the list to determine if p is a prime factor.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 * @return true if p is a prime factor of the number v represented by this
	 *         linked list false otherwise
	 * @throws IllegalArgumentException
	 *             if p is not a prime
	 */
	public boolean containsPrimeFactor(int p) {
		if (!PrimeFactorization.isPrime(p)) {
			throw new IllegalArgumentException();
		}
		PrimeFactorizationIterator iter = this.iterator();
		while (iter.hasNext()) {
			PrimeFactor pf = iter.next();
			if (pf.prime == p) {
				return true;
			}
		}
		return false;

	}

	// The next two methods ought to be private but are made public for testing
	// purpose. Keep
	// them public

	/**
	 * Adds a prime factor p of multiplicity m. Search for p in the linked list. If
	 * p is found at a node N, add m to N.multiplicity. Otherwise, create a new node
	 * to store p and m.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 *            prime
	 * @param m
	 *            multiplicity
	 * @return true if m >= 1 false if m < 1
	 */
	public boolean add(int p, int m) {
		if (m < 1) {
			return false;
		}

		PrimeFactorizationIterator iter = this.iterator();
		while (iter.hasNext()) {
			PrimeFactor N = iter.next();
			if (N.prime == p) {
				N.multiplicity += m;
			}
		}

		PrimeFactor newNode = new PrimeFactor(p, m);
		iter.add(newNode);
		return true;

	}

	/**
	 * Removes m from the multiplicity of a prime p on the linked list. It starts by
	 * searching for p. Returns false if p is not found, and true if p is found. In
	 * the latter case, let N be the node that stores p. If N.multiplicity > m,
	 * subtracts m from N.multiplicity. If N.multiplicity <= m, removes the node N.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 * @param m
	 * @return true when p is found. false when p is not found.
	 * @throws IllegalArgumentException
	 *             if m < 1
	 */
	public boolean remove(int p, int m) throws IllegalArgumentException {
		if (m < 1) {
			throw new IllegalArgumentException();
		}
		PrimeFactorizationIterator iter = this.iterator();

		if (!containsPrimeFactor(p)) {
			return false;
		}
		while (iter.hasNext()) {
			PrimeFactor N = iter.next();
			if (N.multiplicity > m) {
				N.multiplicity -= m;
			} 
			else if (N.multiplicity <= m) {
				iter.remove();

			}
		}
		return true;
	}

	/**
	 * 
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Writes out the list as a factorization in the form of a product. Represents
	 * exponentiation by a caret. For example, if the number is 5814, the returned
	 * string would be printed out as "2 * 3^2 * 17 * 19".
	 */
	@Override
	public String toString() {
		PrimeFactorizationIterator iter = this.iterator();
		String result = "";
		while (iter.hasNext()) {
			PrimeFactor pf = iter.next();
			result += pf.toString() + " * ";
		}
		return result;
	}

	// The next three methods are for testing, but you may use them as you like.

	/**
	 * @return true if this PrimeFactorization is representing a value that is too
	 *         large to be within long's range. e.g. 999^999. false otherwise.
	 */
	public boolean valueOverflow() {
		return value == OVERFLOW;
	}

	/**
	 * @return value represented by this PrimeFactorization, or -1 if
	 *         valueOverflow()
	 */
	public long value() {
		return value;
	}

	public PrimeFactor[] toArray() {
		PrimeFactor[] arr = new PrimeFactor[size];
		int i = 0;
		for (PrimeFactor pf : this)
			arr[i++] = pf;
		return arr;
	}

	@Override
	public PrimeFactorizationIterator iterator() {
		return new PrimeFactorizationIterator();
	}

	/**
	 * Doubly-linked node type for this class.
	 */
	private class Node {
		public PrimeFactor pFactor; // prime factor
		public Node next;
		public Node previous;

		/**
		 * Default constructor for creating a dummy node.
		 */
		public Node() {
			next = null;
			previous = null;

		}

		/**
		 * Precondition: p is a prime
		 * 
		 * @param p
		 *            prime number
		 * @param m
		 *            multiplicity
		 * @throws IllegalArgumentException
		 *             if m < 1
		 */
		public Node(int p, int m) throws IllegalArgumentException {
			if (m < 1) {
				throw new IllegalArgumentException();
			}
			pFactor = new PrimeFactor(p, m);
		}

		/**
		 * Constructs a node over a provided PrimeFactor object.
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 */
		public Node(PrimeFactor pf) {
			pFactor = pf;
		}

		/**
		 * Printed out in the form: prime + "^" + multiplicity. For instance "2^3".
		 * Also, deal with the case pFactor == null in which a string "dummy" is
		 * returned instead.
		 */
		@Override
		public String toString() {
			if (pFactor == null) {
				return "dummy";
			}

			return pFactor.toString();
		}
	}

	private class PrimeFactorizationIterator implements ListIterator<PrimeFactor> {
		// Class invariants:
		// 1) logical cursor position is always between cursor.previous and cursor
		// 2) after a call to next(), cursor.previous refers to the node just returned
		// 3) after a call to previous() cursor refers to the node just returned
		// 4) index is always the logical index of node pointed to by cursor

		private Node cursor = head.next;
		private Node pending = null; // node pending for removal
		private int index = 0;

		// other instance variables ...

		/**
		 * Default constructor positions the cursor before the smallest prime factor.
		 */
		public PrimeFactorizationIterator() {

		}

		@Override
		public boolean hasNext() {
			return nextIndex() < size;
		}

		@Override
		public boolean hasPrevious() {

			return previousIndex() > 0;
		}

		@Override
		public PrimeFactor next() throws NoSuchElementException {
			// returns element after the cursor and moves cursor forward
			pending = cursor;
			cursor = cursor.next;
			index++;
			return pending.pFactor;
		}

		@Override
		public PrimeFactor previous() {
			// returns element before the cursor,and moves cursor backward
			cursor = cursor.previous;
			pending = cursor;
			index--;
			return pending.pFactor;
		}

		/**
		 * Removes the prime factor returned by next() or previous()
		 * 
		 * @throws IllegalStateException
		 *             if pending == null
		 */
		@Override
		public void remove() throws IllegalStateException {
			if (pending == null) {
				throw new IllegalStateException();
			}
			unlink(pending);
			pending = null;
			size--;
		}

		/**
		 * Adds a prime factor at the cursor position. The cursor is at a wrong position
		 * in either of the two situations below:
		 * 
		 * a) pf.prime < cursor.previous.pFactor.prime if cursor.previous != head. b)
		 * pf.prime > cursor.pFactor.prime if cursor != tail.
		 * 
		 * Take into account the possibility that pf.prime == cursor.pFactor.prime.
		 * 
		 * Precondition: pf.prime is a prime.
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 *             if the cursor is at a wrong position.
		 */
		@Override
		public void add(PrimeFactor pf) throws IllegalArgumentException {
			if (cursor.previous != head) {
				if (pf.prime < cursor.previous.pFactor.prime) {
					throw new IllegalArgumentException();
				}
			} else if (cursor != tail) {
				if (pf.prime > cursor.pFactor.prime) {
					throw new IllegalArgumentException();
				}
			}
			// Add new nodes by linking nodes at previous cursor and desired node, update
			// size and increase index
			Node toBeAdded = new Node(pf);
			link(cursor.previous, toBeAdded);
			size++;
			index++;
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Deprecated
		@Override
		public void set(PrimeFactor pf) {
			throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support set method");
		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
	}

	// --------------
	// Helper methods
	// --------------

	/**
	 * Inserts toAdd into the list after current without updating size.
	 * 
	 * Precondition: current != null, toAdd != null
	 */
	private void link(Node current, Node toAdd) {
		toAdd.previous = current;
		toAdd.next = current.next;
		current.next.previous = toAdd;
		current.next = toAdd;

	}

	/**
	 * Removes toRemove from the list without updating size.
	 */
	private void unlink(Node toRemove) {
		toRemove.previous.next = toRemove.next;
		toRemove.next.previous = toRemove.previous;
	}

	/**
	 * Remove all the nodes in the linked list except the two dummy nodes.
	 * 
	 * Made public for testing purpose. Ought to be private otherwise.
	 */
	public void clearList() {
		head.next = tail;
		tail.previous = head;
		size = 0;
		value = 0;
	}

	/**
	 * Multiply the prime factors (with multiplicities) out to obtain the
	 * represented integer. Use Math.multiply(). If an exception is throw, assign
	 * OVERFLOW to the instance variable value. Otherwise, assign the multiplication
	 * result to the variable.
	 * 
	 */
	private void updateValue() {
		PrimeFactorizationIterator iter = this.iterator();
		try {
			while (iter.hasNext()) {
				PrimeFactor pf = iter.next();
				value += Math.multiplyExact(pf.prime, pf.multiplicity);
			}
		}

		catch (ArithmeticException e) {
			value = OVERFLOW;

		}

	}
}
