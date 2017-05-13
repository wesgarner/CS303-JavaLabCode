import java.io.BufferedReader;
import java.io.IOException;

/*
*
* @author Wes Garner
* @date 3APR2017
* @filename UndirectedGraph.java
* @version 1
* Lab Report 11: Implementation of DFS algorithm
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
