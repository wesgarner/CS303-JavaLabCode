import java.io.BufferedReader;
import java.io.IOException;

/*
 *
 * @author Wes Garner
 * @date 29MAR2017
 * @filename UndirectedGraph.java
 * @version 1
 * Lab Report 10: Implementation of Graphs
 *
 */

public class UndirectedGraph extends Graph {
	
	public UndirectedGraph(BufferedReader reader) throws IOException
    {
		super(reader);
    }
	
	public void addEdge(int v, int w)
	{
        E++;
        adj[v].add(w);
        adj[w].add(v);
	}
}
