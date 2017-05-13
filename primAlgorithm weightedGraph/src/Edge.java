import java.util.LinkedList;

/*
*
* @author Wes Garner
* @date 12APR2017
* @filename Edge.java
* @version 1
* Lab Report 12: Implementation of Weighted Graphs
*
*/

public class Edge {
	public Vertex v1, v2;
	public int edgeID;
	public double edgeWeight;
	public LinkedList<Vertex> linked;
	
	public Edge(Vertex vertexA, Vertex vertexB, int id, double weight) {
		v1 = vertexA;
		v2 = vertexB;
		edgeID = id;
		edgeWeight = weight;
		
		// Attach v1/v2 to LinkedList to better work lab assignment
		linked = new LinkedList<Vertex>();
		linked.add(v1);
		linked.add(v2);
	}
	
	public String toString() {
		return ("Edge " + edgeID + " connects vertex " + v1.vertexID + " and " + v2.vertexID + " with a weight of " + edgeWeight);
	}
	
}
