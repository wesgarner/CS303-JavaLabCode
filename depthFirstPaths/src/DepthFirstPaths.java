/*
 *
 * @author Wes Garner
 * @date 4APR2017
 * @filename DepthFirstPaths.java
 * @version 1
 * Lab Report 11: Implementation of DFS
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class DepthFirstPaths {

	// Initial variables for DFS
	private static int[] pi;
	private static int[] d;
	private static int[] f;
	private static int[] color;
	private static int time;
	private static int nil = -1; // NIL
	private static int inf = Integer.MAX_VALUE; // Infinity
	private static int WHITE = 0;
	private static int GRAY = 1;
	private static int BLACK = 2;
	
	// Timing variables as run and announce are different variables
	private static long listStartTime = 0;
	private static long listEndTime = 0;
	

	public static void main(String[] args) throws IOException {
		System.out.println("Placing files into graphs");

		String directedPath = "/home/wesgarner/workspace/CS303-Lab11/src/tinyDG.txt";
		String undirectedPath = "/home/wesgarner/workspace/CS303-Lab11/src/mediumG.txt";

		// Create filestreams and buffered readers for both graphs
		FileInputStream fstream1 = new FileInputStream(undirectedPath);
		BufferedReader buffered1 = new BufferedReader(new InputStreamReader(fstream1));
		UndirectedGraph undirected = new UndirectedGraph(buffered1);
		buffered1.close();
		fstream1.close();
		
		FileInputStream fstream2 = new FileInputStream(directedPath);
		BufferedReader buffered2 = new BufferedReader(new InputStreamReader(fstream2));
		DirectedGraph directed = new DirectedGraph(buffered2);
		buffered2.close();
		fstream1.close();

		// DFS
		runDFS(undirected);
		
		
		/* Topological Sort
		long topStartTime = System.nanoTime(); // adjacency list graph DFS timing with topological sort
		LinkedList<Integer> sortedGraph = topSort(directed);
		long topEndTime = System.nanoTime();
		System.out.println("Topological sort:");
		for (int u : sortedGraph)
			System.out.println("Item " + u + ": " + d[u] + "/" + f[u]);*/
		
		System.out.println("\nDFS algorithm completion time (nanoseconds): " + (listEndTime-listStartTime));
		// System.out.println("Topological sort completion time (nanoseconds): " + (topEndTime-topStartTime));
	}

	public static void runDFS(Graph G)
	{
		int s = 0; // Number to use as source
		// int v = 225; // Number to use as destination

		listStartTime = System.nanoTime(); // adjacency list graph DFS timing
		DFS(G, s); // DFS Algorithm
		listEndTime = System.nanoTime();

		for (int v = 0; v<G.V; v++)
		{
			System.out.print(s + " to " + v + ": ");
			printDFS(G, s, v); // PRINT-PATH
			System.out.println("\n");
		}
	}

	public static void DFS(Graph G, int s)
	{
		// Initialize variables
		int size = G.V;
		color = new int[size];
		d = new int[size];
		f = new int[size];
		pi = new int[size];

		for (int u = 0; u<G.V; u++)
		{
			color[u] = WHITE;
			pi[u] = nil;
		}

		time = 0; 

		for (int u = 0; u<G.V; u++)
			if (color[u] == WHITE)
				visitDFS(G, u); // Run DFS-Visit (G, u)
	}
	
	public static void visitDFS(Graph G, int u)
	{
		time++; // white vertex u has just been discovered
		d[u] = time;
		color[u] = GRAY;
		for (int v : G.adj[u]) // explore edge (u,v)
			if (color[v] == WHITE) {
				pi[v] = u;
				visitDFS(G, v);
			}
		color[u] = BLACK; // blacken u; it is finished
		time++;
		f[u] = time;
	}

	public static void printDFS(Graph G, int s, int v)
	{
		if (v == s)
			System.out.print(s);
		else if (pi[v] == nil)
			System.out.print("No path found");
		else {
			printDFS(G, s, pi[v]);
			System.out.print("," + v);
		}
	}
	
	public static LinkedList<Integer> topSort(DirectedGraph G) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		for (int v = 0; v<G.V; v++)
		{
			DFS(G, v);
			result.addFirst(v);;
		}
		return result;
	}
}
