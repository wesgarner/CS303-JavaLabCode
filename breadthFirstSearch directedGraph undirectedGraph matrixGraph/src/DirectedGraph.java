import java.io.BufferedReader;
import java.io.IOException;

/*
 *
 * @author Wes Garner
 * @date 29MAR2017
 * @filename DirectedGraph.java
 * @version 1
 * Lab Report 10: Implementation of Graphs
 *
 */

public class DirectedGraph extends Graph {
	
	public DirectedGraph(BufferedReader reader) throws IOException
    {
		super(reader);
    }
	
	public void addEdge(int v, int w)
	{
		E++;
        adj[v].add(w);
	}
}
