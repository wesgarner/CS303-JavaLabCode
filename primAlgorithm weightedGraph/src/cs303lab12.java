import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 *
 * @author Wes Garner
 * @date 12APR2017
 * @filename cs303lab12.java
 * @version 1
 * Lab Report 12: Implementation of Weighted Graph
 *
 */

public class cs303lab12 {
	// Initial Variables
	private static int nil = -1; // NIL
	private static int inf = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		System.out.println("Loading files and beginning graph creation...\n");

		// Input Files
		String[] graphPath = {
				"/home/wesgarner/workspace/CS303-Lab12/src/tinyDG.txt",
				"/home/wesgarner/workspace/CS303-Lab12/src/mediumDG.txt",
				"/home/wesgarner/workspace/CS303-Lab12/src/largeDG.txt",
				"/home/wesgarner/workspace/CS303-Lab12/src/XtraLargeDG.txt"
		};
		for (String path : graphPath) {
			// Load each file into a new graph
			FileInputStream fstream1 = new FileInputStream(path);
			BufferedReader buffered1 = new BufferedReader(new InputStreamReader(fstream1));
			Graph G = new Graph(buffered1);
			buffered1.close();
			fstream1.close();

			if (path == graphPath[0]) { System.out.println(G.toString()); } // Print only tinyDG to screen (causes overflow to do all 4

			long nanoStartTime = System.nanoTime();
			mstPrim(G, G.vertices.get(0	)); // Run Prim's algorithm on the paths
			long nanoEndTime = System.nanoTime();
			
			// Announce results with name of file
			int filenum = path.lastIndexOf("/");
			String file = path.substring(filenum + 1);
			System.out.println("File: " + file + " took " + (nanoEndTime-nanoStartTime) + " nanoseconds to process with MST-Prim");
		
		}
	}

	public static void mstPrim(Graph G, Vertex r) { // though pseudocode shows it, giving array of weights is unnecessary in this form
		for (Vertex u : G.vertices) {
			u.key = inf;
			u.pi = new Vertex(nil);
		}

		r.key = 0; // Starting point

		PriorityQueue<Integer> Q = new PriorityQueue<Integer>(G.V);

		while (!Q.isEmpty()) {
			Vertex u = G.vertices.get(Q.poll());

			for (Edge e : G.adj) { // For every edge in G
				for (Vertex v : e.linked) { // Check to see if that edge includes the vertex we are looking for
					if (Q.contains(v) && e.edgeWeight < v.key) { // If it is in queue and the edge weight is less than the current min, update
						v.pi = u;
						v.key = e.edgeWeight;
					}
				}
			}
		}
	}
}
