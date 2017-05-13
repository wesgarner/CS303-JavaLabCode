import java.util.LinkedList;

/*
*
* @author Wes Garner
* @date 12APR2017
* @filename Vertex.java
* @version 1
* Lab Report 12: Implementation of Weighted Graphs
*
*/

public class Vertex {
	public int vertexID;
	
	// Code for Prim's algorithm
	public Vertex pi;
	public double key;
	public LinkedList<Edge> attached = new LinkedList<Edge>();
	
	public Vertex(int id) { vertexID = id; } 
}
