/*
*
* @author Wes Garner
* @date 1MAR2017
* @filename RBNode.java
* @version 1
* Lab Report 8: Implementation Red Black Tree
* Note: Uses and extends Lab 7 code
*
*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class RBTree<T extends Comparable<T>> extends BinaryTree {
	private RBNode<T> root; // the root of the tree
	private RBNode<T> cursor; // the current node
	private static RBNode nil = new RBNode(null);
	
	public static void main(String[] args) {
		int TABLE_SIZE = 177650;
		int SEARCH_SIZE = 17;
		int KEY_SIZE = 0;
		String[] keyArray = new String[TABLE_SIZE]; // Size of key file
		String[] searchArray = new String[SEARCH_SIZE];

		try {
			System.out.println("Placing files into arrays...");
			// Files to be used to create has table and search it
			String searchPath = "/home/wesgarner/workspace/CS303-Lab8/src/input.txt";
			String keyPath = "/home/wesgarner/workspace/CS303-Lab8/src/UPC.csv";
			
			// Search file array creation
			FileInputStream fstream = new FileInputStream(searchPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					
			String strLine;
			for (int i=0; (strLine = br.readLine()) != null; i++)
				searchArray[i] = strLine;
			
			// Key file array creation
			
			fstream = new FileInputStream(keyPath);
			br = new BufferedReader(new InputStreamReader(fstream));

			for (KEY_SIZE=0; (strLine = br.readLine()) != null; KEY_SIZE++)
				keyArray[KEY_SIZE] = strLine;

			// Close the buffered reader and input stream
			br.close();
			fstream.close();

		} catch (Exception exc) {
			System.out.print("Error loading files... ");
			System.out.println(exc.toString());
		}
		
		// Create Red/Black BST
		System.out.println("Creating Red/Black Tree...");
		RBTree tree = new RBTree(nil);
		
		long rbStartTime = System.currentTimeMillis();
		for (int i=0; i<TABLE_SIZE; i++) {
			String[] str = keyArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String desc = str[2] + ", " + str[1];
			RBTree.rbInsert(tree, new RBNode(key, desc));
		}
		long rbEndTime = System.currentTimeMillis();
		
		long rbSStartTime = System.currentTimeMillis();
		for (int i=0; i < searchArray.length; i++) {
			// Parse search line
			String[] str = searchArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			
			RBNode searchNode = RBTree.rbSearch(tree.root, key);
			System.out.println("Found key " + key + ": " + searchNode.getDesc());
			if (searchNode.PisGPRightChild())
				System.out.println(" -- Parent is the right child of the grandparent");
			else // Parent is left child of grandparent
				System.out.println(" -- Parent is the left child of the grandparent");
		}
		long rbSEndTime = System.currentTimeMillis();
		
		System.out.println("Creation time (ms): " + (rbEndTime-rbStartTime));
		System.out.println("Search time (ms): " + (rbSEndTime-rbSStartTime));
	}

	
	
	// Driver program used for lab purposes and thus removed
	/*public static void main(String[] args) {
		String inputFile = "/home/wesgarner/workspace/CS303-Lab8/src/UPC.csv";

		// Load file into string array
		System.out.println("\nLoading... " + inputFile);
		String[] strArray = null;
		try {
			FileInputStream fstream = new FileInputStream(inputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine = br.readLine();
			strArray = strLine.split(" ");

			// Close the buffered reader and input stream
			br.close();
			fstream.close();
		} catch (Exception exc) {
			System.out.println("Error loading file... " + inputFile);
			System.out.println(exc.toString());
		}

		int maxnum = 60;

		// Parse string into integers (ignore first position as is blank)
		int[] intArray = new int[strArray.length];
		for (int i=0; i<strArray.length-1; i++)
			intArray[i] = Integer.parseInt(strArray[i]);

		// Parse string array into binary tree
		RBTree tree = new RBTree(nil);		
		for (int i=0; i<maxnum; i++)
			rbInsert(tree, new RBNode(intArray[i]));

		// Send original binary tree to screen by walking thru it
		inOrderTreeWalk(tree.getRoot());
		
	}*/

	// Implement Left-Rotate
	public static void leftRotate(RBTree T, RBNode x)
	{
		RBNode y = x.getRight();			// set y
		x.setRight(y.getLeft());			// turn y's left subtree into x's right subtree
		// y.getLeft().setParent(x);
		if (y.getLeft() != nil)
			y.getLeft().setParent(x);
		y.setParent(x.getParent()); 				// link x's parent to y
		if (x.getParent() == nil)
			T.setRoot(y);
		else if (x == x.getParent().getLeft())
			x.getParent().setLeft(y);
		else
			x.getParent().setRight(y);

		y.setLeft(x);						// put x on y's left
		x.setParent(y);
	}

	// Implement Right-Rotate
	public static void rightRotate(RBTree T, RBNode x)
	{
		RBNode y = x.getLeft();				// set y
		x.setLeft(y.getRight());			// turn y's right subtree into x's left subtree
		// y.getRight().setParent(x);
		if (y.getRight() != nil)
			y.getRight().setParent(x);
		y.setParent(x.getParent()); 				// link x's parent to y
		if (x.getParent() == nil)
			T.setRoot(y);
		else if (x == x.getParent().getLeft())
			x.getParent().setLeft(y);
		else
			x.getParent().setRight(y);

		y.setRight(x);						// put x on y's right
		x.setParent(y);
	}

	// Implement RB-Insert
	public static void rbInsert(RBTree T, RBNode z)
	{
		RBNode y = nil;
		RBNode x = T.getRoot();

		while (x != nil)
		{
			y = x;
			if (z.getData().compareTo(x.getData()) < 0)
				x = x.getLeft();
			else
				x = x.getRight();
		}
		z.setParent(y);
		if (y == nil)
			T.setRoot(z);
		else if (z.getData().compareTo(y.getData()) < 0)
			y.setLeft(z);
		else
			y.setRight(z);

		z.setLeft(nil);
		z.setRight(nil);
		z.setColor(true); // red = true

		rbInsertFixup(T, z);
	}

	// Implement RB-Insert-Fixup
	// Red = true, Black = false for getColor/setColor
	public static void rbInsertFixup(RBTree T, RBNode z)
	{
		while (z.getParent().getColor() == true) // if red
		{
			if (z.getParent() == z.getParent().getParent().getLeft()) // if z's parent is a left child
			{
				RBNode y = z.getParent().getParent().getRight();
				if (y.getColor()) // if red
				{
					z.getParent().setColor(false); // case 1
					y.setColor(false);
					z.getParent().getParent().setColor(true);
					z = z.getParent().getParent();
				}
				else
				{
					if (z == z.getParent().getRight()) // if z is a right child
					{
						z = z.getParent();
						leftRotate(T, z); // case 2
					}
					z.getParent().setColor(false); // case 3
					z.getParent().getParent().setColor(true);
					rightRotate(T, z.getParent().getParent());
				}
			}
			else {
				RBNode y = z.getParent().getParent().getLeft();
				if (y.getColor()) // if red
				{
					z.getParent().setColor(false); // case 1
					y.setColor(false);
					z.getParent().getParent().setColor(true);
					z = z.getParent().getParent();
				}
				else
				{
					if (z == z.getParent().getLeft()) // if z is a right child
					{
						z = z.getParent();
						rightRotate(T, z); // case 2
					}
					z.getParent().setColor(false); // case 3
					z.getParent().getParent().setColor(true);
					leftRotate(T, z.getParent().getParent());
				}
			}
		}
		T.getRoot().setColor(false);
	}

	// In-Order-Traversal (node) Method
	public static void inOrderTreeWalk(RBNode x)
	{
		if (x != nil)
		{
			inOrderTreeWalk(x.getLeft());
			System.out.println(x.getData() + ", Color: " + x.getColor());
			inOrderTreeWalk(x.getRight());
		}
	}
	
	public static RBNode rbSearch(RBNode x, double key)
	{
		if (x == nil || x.getData().compareTo(key) == 0)
			return x;
		if (x.getData().compareTo(key) > 0)
			return rbSearch(x.getLeft(), key);
		else
			return rbSearch(x.getRight(),key);
	}
	

	/**
	 * Constructor for initializing a tree with node
	 * being set as the root of the tree.
	 * @param node
	 */
	public RBTree(RBNode<T> node) {
		root = node;
	}
	public RBTree() {
	}
	/**
	 * Returns the cursor node.
	 * @return cursor
	 */
	public RBNode<T> getCursor() {
		return cursor;
	}
	/**
	 * Sets the root to the provided node.
	 * ONLY USE IN THE DELETE METHOD
	 * @param node
	 */
	public void setRoot(RBNode<T> node) {
		root = node;
	}
	// Implements getRoot
	public RBNode<T> getRoot() {
		return root;
	}
}
