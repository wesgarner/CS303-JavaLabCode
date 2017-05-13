/*
 *
 * @author Wes Garner
 * @date 22FEB2017
 * @filename BinaryTree.java
 * @version 1
 * Lab Report 7: Implementation BinaryTree methods
 * This class is derived from assignment PDF
 *
 */


public class BinaryTree<T extends Comparable<T>> {
	private BinaryTreeNode<T> root; // the root of the tree
	private BinaryTreeNode<T> cursor; // the current node
	
	// Blank constructor to allow extends to work in Lab 8
	public BinaryTree() {	}
	/**
	 * Constructor for initializing a tree with node
	 * being set as the root of the tree.
	 * @param node
	 */
	public BinaryTree(BinaryTreeNode<T> node) {
		root = node;
	}
	/**
	 * Moves the cursor to the root.
	 */
	public void toRoot() {
		cursor = root;
	}
	// Implements getRoot
	public BinaryTreeNode<T> getRoot() {
		return root;
	}
	/**
	 * Returns the cursor node.
	 * @return cursor
	 */
	public BinaryTreeNode<T> getCursor() {
		return cursor;
	}
	/**
	 * Sets the root to the provided node.
	 * ONLY USE IN THE DELETE METHOD
	 * @param node
	 */
	public void setRoot(BinaryTreeNode<T> node) {
		root = node;
	}
	/**
	 * Checks if the tree node has a left child node
	 * @return true if left child exists else false
	 */
	public boolean hasLeftChild() {
		return cursor.getLeft() != null;
	}
	/**
	 * Checks if the tree node has a right child node
	 * @return true if right child exists else false
	 */
	public boolean hasRightChild() {
		return cursor.getRight() != null;
	}
	/**
	 * Move the cursor to the left child
	 */
	public void toLeftChild() {
		cursor = cursor.getLeft();
	}
	/**
	 * Move the cursor to the right child
	 */
	public void toRightChild() {
		cursor = cursor.getRight();
	}
	/**
	 * @return height of the tree
	 */
	public int height() {
		if (root != null) {
			return root.height();
		} else {
			return 0;
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (root != null) {
			return root.toStringPreOrder(".");
		} else {
			return "";
		}
	}
}