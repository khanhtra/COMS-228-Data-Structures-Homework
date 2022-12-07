package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author  Khanh Tran
 *
 */

/**
 * 
 * This class implements a splay tree. Add any helper methods or implementation
 * details you'd like to include.
 *
 */

public class SplayTree<E extends Comparable<? super E>> extends AbstractSet<E> {
	protected Node root;
	protected int size;

	public class Node // made public for grading purpose
	{
		public E data;
		public Node left;
		public Node parent;
		public Node right;

		public Node(E data) {
			this.data = data;
		}

		@Override
		public Node clone() {
			return new Node(data);
		}
	}

	/**
	 * Default constructor constructs an empty tree.
	 */
	public SplayTree() {
		size = 0;
	}

	/**
	 * Needs to call addBST() later on to complete tree construction.
	 */
	public SplayTree(E data) {
		root = new Node (data);
		size = 0;
		addBST(data);
	}

	/**
	 * Copies over an existing splay tree. The entire tree structure must be copied.
	 * No splaying. Calls cloneTreeRec().
	 * 
	 * @param tree
	 */
	public SplayTree(SplayTree<E> tree) {
		Node a = cloneTreeRec(tree.root);
		addBST(a.data);
		size = tree.size;
	}

	/**
	 * Recursive method called by the constructor above.
	 * 
	 * @param subTree
	 * @return
	 */
	private Node cloneTreeRec(Node subTree) {
		if (subTree == null) {
			return null;
		}

	    Node newNode = new Node(subTree.data);
        newNode.left = cloneTreeRec(subTree.left);
        newNode.right = cloneTreeRec(subTree.right);
        return newNode;
    }

	/**
	 * This function is here for grading purpose. It is not a good programming
	 * practice.
	 * 
	 * @return element stored at the tree root
	 */
	public E getRoot() {
		return root.data;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Clear the splay tree.
	 */
	@Override
	public void clear() {
		
		root = null;
		size = 0;
		
	}

	// ----------
	// BST method
	// ----------

	/**
	 * Adds an element to the tree without splaying. The method carries out a binary
	 * search tree addition. It is used for initializing a splay tree.
	 * 
	 * Calls link().
	 * 
	 * @param data
	 * @return true if addition takes place false otherwise (i.e., data is in the
	 *         tree already)
	 */
	public boolean addBST(E data) {
		if (root == null) {
			root = new Node(data);
			size++;
			return true;
		}
		Node current = root;
		Node n = new Node(data);
		while (true) {
			int comp = current.data.compareTo(data);
			if (comp == 0) {
				return false;
			} 
			else if (comp > 0) {
				//update current
				if (current.left != null) {
					current = current.left;
				} else {
					//add if no node present
					current.left = n;
					link(current, current.left);
					size++;
					return true;
				} 
			}
			else if (comp < 0) 
				if (current.right != null) {
					current = current.right;
				} else {
					current.right = n;
					link(current, current.right);
					size++;
					return true;
				}
			}
		}
	
	

	// ------------------
	// Splay tree methods
	// ------------------

	/**
	 * Inserts an element into the splay tree. In case the element was not
	 * contained, this creates a new node and splays the tree at the new node. If
	 * the element exists in the tree already, it splays at the node containing the
	 * element.
	 * 
	 * Calls link().
	 * 
	 * @param data
	 *            element to be inserted
	 * @return true if addition takes place false otherwise (i.e., data is in the
	 *         tree already)
	 */
	@Override
	public boolean add(E data) {
		Node a = findEntry(data);
		if (a != null) {
			splay(a);
			return false;
		}
		else {
			addBST(data);
			Node b = findEntry(data);
			splay(b);
			return true;
		}
	}

	/**
	 * Determines whether the tree contains an element. Splays at the node that
	 * stores the element. If the element is not found, splays at the last node on
	 * the search path.
	 * 
	 * @param data
	 *            element to be determined whether to exist in the tree
	 * @return true if the element is contained in the tree false otherwise
	 */
	public boolean contains(E data) {
		Node a = findEntry(data);
		splay(a);
		if (a == null) {
			return false;
		}
		return findEntry(data) != null; 
	}

	/**
	 * Finds the node that stores the data and splays at it.
	 *
	 * @param data
	 */
	public void splay(E data) {
		contains(data);
	}

	/**
	 * Removes the node that stores an element. Splays at its parent node after
	 * removal (No splay if the removed node was the root.) If the node was not
	 * found, the last node encountered on the search path is splayed to the root.
	 * 
	 * Calls unlink().
	 * 
	 * @param data
	 *            element to be removed from the tree
	 * @return true if the object is removed false if it was not contained in the
	 *         tree
	 */
	public boolean remove(E data) {
		return false;
	}

	/**
	 * This method finds an element stored in the splay tree that is equal to data
	 * as decided by the compareTo() method of the class E. This is useful for
	 * retrieving the value of a pair <key, value> stored at some node knowing the
	 * key, via a call with a pair <key, ?> where ? can be any object of E.
	 * 
	 * Calls findEntry(). Splays at the node containing the element or the last node
	 * on the search path.
	 * 
	 * @param data
	 * @return element such that element.compareTo(data) == 0
	 */
	public E findElement(E data) {
		if (findEntry(data) != null) {
		Node found = findEntry(data);
		splay(found);
		return found.data;
		}
		return null;

	}

	/**
	 * Finds the node that stores an element. It is called by methods such as
	 * contains(), add(), remove(), and findElement().
	 * 
	 * No splay at the found node.
	 *
	 * @param data
	 *            element to be searched for
	 * @return node if found or the last node on the search path otherwise null if
	 *         size == 0.
	 */
	protected Node findEntry(E data) {
		if (size == 0) {
			return null;
		}
		Node current = root;
		while (current != null) {
			int comp = current.data.compareTo(data);
			if (comp == 0) {
				return current;
			} else if (comp > 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return null;

	}

	/**
	 * Join the two subtrees T1 and T2 rooted at root1 and root2 into one. It is
	 * called by remove().
	 * 
	 * Precondition: All elements in T1 are less than those in T2.
	 * 
	 * Access the largest element in T1, and splay at the node to make it the root
	 * of T1. Make T2 the right subtree of T1. The method is called by remove().
	 * 
	 * @param root1
	 *            root of the subtree T1
	 * @param root2
	 *            root of the subtree T2
	 * @return the root of the joined subtree
	 */
	protected Node join(Node root1, Node root2) {
		if (root1 == null) {
			return root2;
		}
		else if (root2 == null) {
			return root1;
		}
		else {

		while (root1.right != null) {
			root1 = root1.right;
		}
		//splay(root1);
		root1.right = root2;
		return root1;
		}
	}

	/**
	 * Splay at the current node. This consists of a sequence of zig, zigZig, or
	 * zigZag operations until the current node is moved to the root of the tree.
	 * 
	 * @param current
	 *            node to splay
	 */
	protected void splay(Node current) {
//		//as long as it has a parent
//		while (current.parent != null) {
//		//zig case
//			if (current.parent.parent == null) {
//				zig(current);
//			}
//			//zigzig case
//			else if (current.parent.parent != null) {
//				zigZig(current);
//			}
//			//zigzag case
//			
//		}
		
		
	
	}

	/**
	 * This method performs the zig operation on a node. Calls leftRotate() or
	 * rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig operation on
	 */
	protected void zig(Node current) {
		if (current != null) {
			if (current == current.parent.left) {
				rightRotate(current);
			}
			else if (current == current.parent.right) {
				leftRotate(current);
			}
		}
	}

	/**
	 * This method performs the zig-zig operation on a node. Calls leftRotate() or
	 * rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig-zig operation on
	 */
	protected void zigZig(Node current) {
		if (current != null) {
			if (current == current.parent.left && current.parent == current.parent.parent.left) {
				rightRotate(current);
				rightRotate(current);
			}
			else if (current == current.parent.right && current.parent == current.parent.parent.right) {
				leftRotate(current);
				leftRotate(current);
			}
		}
	}
	

	/**
	 * This method performs the zig-zag operation on a node. Calls leftRotate() and
	 * rightRotate().
	 * 
	 * @param current
	 *            node to perform the zig-zag operation on
	 */
	protected void zigZag(Node current) {
		// TODO
	}

	/**
	 * Carries out a left rotation at a node such that after the rotation its former
	 * parent becomes its left child.
	 * 
	 * Calls link().
	 * 
	 * @param current
	 */
	private void leftRotate(Node current) {
		Node a = current.right;
		current.right = current.left;
		a.left = current;
		link(a, current);

	}

	/**
	 * Carries out a right rotation at a node such that after the rotation its
	 * former parent becomes its right child.
	 * 
	 * Calls link().
	 * 
	 * @param current
	 */
	private void rightRotate(Node current) {
		Node a = current.left;
		current.left = current.right;
		a.right = current;
		link(a, current);
	}

	/**
	 * Establish the parent-child relationship between two nodes.
	 * 
	 * Called by addBST(), add(), leftRotate(), and rightRotate().
	 * 
	 * @param parent
	 * @param child
	 */
	private void link(Node parent, Node child) {
		child.parent = parent;
	}

	/**
	 * Removes a node n by replacing the subtree rooted at n with the join of the
	 * node's two subtrees.
	 * 
	 * Called by remove().
	 * 
	 * @param n
	 */
	private void unlink(Node n) {
		Node a = successor (n);
		n.data = a.data;
		n = a;
		size--;
	}

	/**
	 * Perform BST removal of a node.
	 * 
	 * Called by the iterator method remove().
	 * 
	 * @param n
	 */
	private void unlinkBST(Node n) {
		// TODO
	}

	/**
	 * Called by unlink() and the iterator method next().
	 * 
	 * @param n
	 * @return successor of n
	 */
	private Node successor(Node n) {
		//no node case
		if (n == null) {
			return null;
		}
		//right child successor case with sub tree
		else if (n.right != null) {
			Node current = n.right;
			while (current.left != null) {
				current = current.left;
			}
			return current;
		}
		//right child successor case with no child sub tree
		else {
			  Node current = n.parent;
		      Node child = n;
		      while (current != null && current.right == child)
		      {
		        child = current;
		        current = current.parent;
		      }
		      return current;
		    }
		  }

	@Override
	public Iterator<E> iterator() {
		return new SplayTreeIterator();
	}

	/**
	 * Write the splay tree according to the format specified in Section 2.2 of the
	 * project description.
	 * 
	 * Calls toStringRec().
	 *
	 */
	@Override
	public String toString() {
		  
		    return toStringRec(root, 0);
	}

	private String toStringRec(Node n, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++)
	    {
	      sb.append("  ");
	    }
	    
	    if (n == null)
	    {
	      sb.append("null\n");
	      return sb.toString();
	    }
	    sb.append(n.data.toString());
	    sb.append("\n");
	    if (n.left != null || n.right != null)
	    {
	      toStringRec(n.left, depth + 1);   
	      toStringRec(n.right, depth + 1);
	    }
		return sb.toString();
	  }
	/**
	 *
	 * Iterator implementation for this splay tree. The elements are returned in
	 * ascending order according to their natural ordering. The methods hasNext()
	 * and next() are exactly the same as those for a binary search tree --- no
	 * splaying at any node as the cursor moves. The method remove() behaves like
	 * the class method remove(E data) --- after the node storing data is found.
	 * 
	 */
	private class SplayTreeIterator implements Iterator<E> {
		Node cursor;
		Node pending;

		public SplayTreeIterator() {
		      cursor = root;
		      if (cursor != null)
		      {
		        while (cursor.left != null)
		        {
		        	cursor = cursor.left;
		        }
		      }
		}

		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public E next() {
			 if (!hasNext()) {
				 throw new NoSuchElementException();
			 }
		      pending = cursor;
		      cursor = successor(cursor);
		      return pending.data;
		    }
		

		/**
		 * This method will join the left and right subtrees of the node being removed,
		 * and then splay at its parent node. It behaves like the class method remove(E
		 * data) after the node storing data is found. Place the cursor at the parent
		 * (or the new root if removed node was the root).
		 * 
		 * Calls unlinkBST().
		 * 
		 */
		@Override
		public void remove() {
			if (pending.left != null && pending.right != null)
		      {
		        cursor = pending;
		      }
		      unlink(pending);
		      pending = null;
		    }
		  }
	}

