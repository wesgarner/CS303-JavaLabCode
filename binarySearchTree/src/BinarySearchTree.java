/*
 *
 * @author Wes Garner
 * @date 22FEB2017
 * @filename BinarySearchTree.java
 * @version 2 [Implemented Homework]
 * Lab Report 7: Implementation BinaryTree methods
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BinarySearchTree<T extends Comparable<T>> {
	// Driver program
	public static void main(String[] args) {
		String inputFile = "/home/wesgarner/workspace/CS303-Lab7/src/Sorting/input_100.txt";

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

		// Set amount of numbers to be adding / removing from the binary tree
		int maxnum = 20;

		// Parse string into integers (ignore first position as is blank)
		int[] intArray = new int[strArray.length];
		for (int i=1; i<strArray.length; i++)
			intArray[i-1] = Integer.parseInt(strArray[i]);

		// Parse string array into binary tree
		BinaryTreeNode node = new BinaryTreeNode(intArray[0]);
		BinaryTree tree = new BinaryTree(node);
		for (int i=1; i<=maxnum; i++)
			treeInsert(tree, new BinaryTreeNode(intArray[i]));

		// Send original binary tree to screen by walking thru it
		inOrderTreeWalk(node);

		// Remove and add back nodes
		System.out.println("\nRemoving nodes...");
		// Remove 5 nodes
		for (int i=0; i<5; i++)
		{
			treeDelete(tree, tree.getRoot());
			inOrderTreeWalk(tree.getRoot());
			System.out.print("\n");
		}
		
		System.out.println("\nAdding nodes...");
		// Add 5 nodes
		for (int i=maxnum+1; i<=maxnum+5; i++)
		{
			treeInsert(tree, new BinaryTreeNode(intArray[i]));
			inOrderTreeWalk(tree.getRoot());
			System.out.print("\n");
			
		}
	}
	
	// Insert (T, data) Method
	public static void treeInsert(BinaryTree T, BinaryTreeNode z) {
		BinaryTreeNode y = null;
		BinaryTreeNode x = T.getRoot();

		while (x != null)
		{
			y = x;
			if (z.getData().compareTo(x.getData()) < 0)
				x = x.getLeft();
			else
				x = x.getRight();
		}
		z.setParent(y);
		if (y == null)
			T.setRoot(z); // tree T was empty
		else if (z.getData().compareTo(y.getData()) < 0)
			y.setLeft(z);
		else
			y.setRight(z);
	}

	// In-Order-Traversal (node) Method
	public static void inOrderTreeWalk(BinaryTreeNode x)
	{
		if (x != null)
		{
			inOrderTreeWalk(x.getLeft());
			System.out.print(x.getData() + " ");
			inOrderTreeWalk(x.getRight());
		}
	}

	// Tree-Search (node, data) Method
	public static BinaryTreeNode treeSearch(BinaryTreeNode x, Comparable k)
	{
		if (x == null || k == x.getData())
			return x;
		if (k.compareTo(x.getData()) < 0)
			return treeSearch(x.getLeft(), k);
		else
			return treeSearch(x.getRight(), k);
	}

	// Tree-Delete (T, data)
	public static void treeDelete(BinaryTree T, BinaryTreeNode z)
	{
		if (z.getLeft() == null)
		{
			transplant(T, z, z.getRight());
		}
		else if (z.getRight() == null)
			transplant(T, z, z.getLeft());
		else {
			BinaryTreeNode y = treeMinimum(z.getRight());
			if (y.getParent() != z)
			{
				transplant(T, y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			transplant(T, z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
		}
	}

	public static void transplant(BinaryTree T, BinaryTreeNode u, BinaryTreeNode v)
	{
		if (u.getParent() == null)
			T.setRoot(v);
		else if (u == u.getParent().getLeft())
			u.getParent().setLeft(v);
		else
			u.getParent().setRight(v);
		if (v != null)
			v.setParent(u.getParent());
	}
	
	public static BinaryTreeNode treeMinimum(BinaryTreeNode x) {
		while (x.getRight() != null)
			x = x.getRight();
		return x;
	}
}
