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

public class RBNode<T extends Comparable<T>> extends BinaryTreeNode {
	private RBNode<T> left; // the left child
	private RBNode<T> right; // the right child
	private RBNode<T> parent; // the parent
	private RBNode<T> grandparent; // the grandparent
	private boolean gprightchild; // is parent node the right child of grandparent
	private boolean color; // the color
	private String desc; // description of item
	private T data; // the data in this node
	private static RBNode nil = new RBNode(null);
	
	// Additional code created for finding parent/grandparent
	public RBNode<T> getParent() {
		return parent;
	}
	public RBNode<T> getGParent() {
		return grandparent;
	}
	public void setParent(RBNode<T> newParent) {
		parent = newParent;
		if (newParent.getParent() != null && newParent.getParent() != nil)
			grandparent = newParent.getParent();
		else
			grandparent = nil;
		checkGPRightChild();
	}
	
	// Check to see if the node's parent is the grandchild on the right (false would mean it's the left child)
	public void checkGPRightChild() {
		if (grandparent != nil && grandparent.getRight() != nil)
			gprightchild = (parent == grandparent.getRight());
	}
	public boolean PisGPRightChild() {
		return gprightchild;
	}

	// Additional code created for color
	// Black = false, Red = true
	public boolean getColor() {
		return color;
	}
	public void setColor(boolean newColor) {
		color = newColor;
	}
	
	public RBNode() {
		this(null, null, null, null, false);
	}
	public RBNode(T theData) {
		this(theData, null, null, null, false);
	}
	// Creation with the addition of description string
	public RBNode(T theData, String str) {
		this(theData, str, null, null, false);
	}
	public RBNode(T theData, String str, RBNode<T> leftChild,
			RBNode<T> rightChild, boolean nodeColor) {
		data = theData;
		desc = str;
		left = leftChild;
		right = rightChild;
		color = nodeColor;
	}
	public T getData() {
		return data;
	}
	// Description to attach to node with key
	public String getDesc() {
		return desc;
	}
	public void setDesc(String str) {
		desc = str;
	}
	public RBNode<T> getLeft() {
		return left;
	}
	public RBNode<T> getRight() {
		return right;
	}
	public void setLeft(RBNode<T> newLeft) {
		left = newLeft;
	}
	public void setRight(RBNode<T> newRight) {
		right = newRight;
	}
}