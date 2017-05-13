
/*
 *
 * @author Wes Garner
 * @date 12APR2017
 * @filename Graph.java
 * @version 1
 * Lab Report 12: Implementation of Weighted Graphs
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;

public class Graph {
	public int V;
	public int E;
	public int idcount = 0;
	private static int nil = -1; // NIL

	public LinkedList<Edge> adj;
	public LinkedList<Vertex> vertices;

	public Graph()
	{
		V = 0;
		E = 0;
	}

	public Graph(BufferedReader reader) throws IOException
	{
		String line;
		line = reader.readLine();
		V = Integer.parseInt(line);
		line = reader.readLine();
		E = Integer.parseInt(line);

		// Initialize adj and vertices
		// addEdge(new Vertex(nil), new Vertex(nil));
		adj = new LinkedList<Edge>();
		vertices = new LinkedList<Vertex>();

		while ((line = reader.readLine()) != null) {
			int intV1, intV2;
			double weight;
			Vertex tempV1 = new Vertex(nil), tempV2 = new Vertex(nil); // Init blank vertices

			StringTokenizer st = new StringTokenizer(line, " ");
			intV1 = Integer.parseInt(st.nextToken());
			intV2 = Integer.parseInt(st.nextToken());
			weight = Double.parseDouble(st.nextToken());

			// Check to see if vertex already exists, then grab or create
			// Vertex 1
			Iterator<Vertex> li = vertices.iterator();
			boolean done = false;
			while (li.hasNext() && !done) {
				Vertex next = li.next();
				if (next.vertexID == intV1) {
					tempV1 = next;
					done = true;
				}
			}
			if (!done) { // Create new vertex as did not exist after iteration
				tempV1 = new Vertex(intV1);
				vertices.add(tempV1);
			}
			// Vertex 2
			li = vertices.iterator();
			done = false;
			while (li.hasNext() && !done) {
				Vertex next = li.next();
				if (next.vertexID == intV2) {
					tempV2 = next;
					done = true;
				}
			}
			if (!done) { // Create new vertex as did not exist after iteration
				tempV2 = new Vertex(intV2);
				vertices.add(tempV2);
			}

			addEdge(tempV1, tempV2, weight);
		}
	}

	public void addEdge(Vertex v, Vertex w, double weight)
	{	
		// Create edge with random weight (1-20) and counted edgeID
		Random rand = new Random();
		Edge newEdge = new Edge(v, w, idcount++, weight);
		newEdge.toString();
		adj.add(newEdge);
		
		// Tell the vertices that they are now attached to another and the weights
		v.attached.add(newEdge);
		w.attached.add(newEdge);
	}

	public String toString()
	{
		String s = new String();
		s = "There are "+V+" vertices and "+E+" edges\n";

		for (Edge edge : adj)
			if (edge.edgeID != nil)
				System.out.println(edge.toString());

		return s;
	}

}
