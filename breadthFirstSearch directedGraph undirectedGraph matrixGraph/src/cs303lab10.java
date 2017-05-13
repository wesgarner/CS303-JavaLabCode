import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
 *
 * @author Wes Garner
 * @date 29MAR2017
 * @filename cs303lab10.java
 * @version 1.3
 * Lab Report 10: Implementation of Graphs
 *
 */

public class cs303lab10 {

	// Initial variables for BFS
	private static int[] pi;
	private static int[] d;
	private static int[] color;
	private static int nil = -1; // NIL
	private static int inf = Integer.MAX_VALUE; // Infinity
	private static int WHITE = 0;
	private static int GRAY = 1;
	private static int BLACK = 2;

	public static void main(String[] args) throws IOException {
		System.out.println("Placing files into graphs");		
		
		// Files to be used to create has table and search it
		String directedPath = "/home/wesgarner/workspace/CS303-Lab10/src/tinyDG.txt";
		String undirectedPath = "/home/wesgarner/workspace/CS303-Lab10/src/mediumG.txt";

		// Lab assignment
		// Create filestreams and buffered readers for both graphs
		FileInputStream fstreamdir = new FileInputStream(directedPath);
		BufferedReader directedBR = new BufferedReader(new InputStreamReader(fstreamdir));

		FileInputStream fstreamundir = new FileInputStream(undirectedPath);
		BufferedReader undirectedBR = new BufferedReader(new InputStreamReader(fstreamundir));

		DirectedGraph directed = new DirectedGraph(directedBR);
		UndirectedGraph undirected = new UndirectedGraph(undirectedBR);

		System.out.println("Directed Graph:\n" + directed.tostring());
		System.out.println("Undirected Graph:\n" + undirected.tostring());
		
		// Homework assignment
		// Create filestreams and buffered readers for both graphs
		FileInputStream fstream1 = new FileInputStream(undirectedPath);
		BufferedReader buffered1 = new BufferedReader(new InputStreamReader(fstream1));
		undirected = new UndirectedGraph(buffered1);
		buffered1.close();
		fstream1.close();
		
		FileInputStream fstream2 = new FileInputStream(undirectedPath);
		BufferedReader buffered2 = new BufferedReader(new InputStreamReader(fstream2));
		MatrixGraph matrix = new MatrixGraph(buffered2);
		buffered2.close();
		fstream2.close();

		// BFS for adjacency list then adjacency matrix
		int s = 0; // number to use as source
		int v = 12; // number to use as destination
		
		long listStartTime = System.nanoTime(); // adjacency list graph BFS timing
		BFS(undirected, s); // BFS(Graph G, int s)
		long listEndTime = System.nanoTime();
		
		long matrixStartTime = System.nanoTime(); // adjacency matrix graph BFS timing
		// BFS(matrix, s); // BFS(MatrixGraph G, int s)
		long matrixEndTime = System.nanoTime();
		
		// Announce BFS results for 0 ... v
		System.out.print("Path from " + s + " to " + v + ": ");
		printBFS(undirected, s, v);
		System.out.print("\n\n");
		
		// Print times for BFS versions to run
		System.out.println("Adjacency list completion time (nanoseconds): " + (listEndTime-listStartTime));
		System.out.println("Adjacency matrix completion time (nanoseconds): " + (matrixEndTime-matrixStartTime));
	}

	public static void BFS(Graph G, int s)
	{
		// Init color, distance, and pi
		int size = G.V;
		color = new int[size];
		d = new int[size];
		pi = new int[size];
		
		for (int u = 0; u<G.V; u++)
		{
			color[u] = WHITE;
			d[u] = inf;
			pi[u] = nil;
		}
		
		// Change start to GRAY and 0 distance with no connector
		color[s] = GRAY;
		d[s] = 0;
		pi[s] = nil;

		// Create queue and add start
		Queue <Integer> Q = new <Integer> LinkedList();
		Q.add(s);

		while(!Q.isEmpty()) {
			int u = Q.remove();
			for (int v : G.adj[u])
			{
				if (color[v] == WHITE)
				{
					color[v] = GRAY;
					d[v] = d[u] + 1;
					pi[v] = u;

					Q.add(v);
				}
			}
			color[u] = BLACK;
		}
	}
	
	// Adjacency Matrix Graph BFS
	public static void BFS(MatrixGraph G, int s)
	{
		int size = G.adj.length;
		boolean[] visited = new boolean[size];
		

		// Create queue and add start
		Queue <Integer> Q = new <Integer> LinkedList();
		Q.add(s);

		while(!Q.isEmpty()) {
			int u = Q.remove();
			int v = u;
			
			while (v < size)
			{
				if (G.adj[u][v] == true && !visited[v])
				{
					visited[v] = true;
					Q.add(v);
				}
				v++;
			}
		}
	}

	public static void printBFS(Graph G, int s, int v)
	{
		if (v == s)
			System.out.print(s);
		else if (pi[v] == nil)
			System.out.print("No path found");
		else {
			printBFS(G, s, pi[v]);
			System.out.print("-" + v);
		}
	}
}
