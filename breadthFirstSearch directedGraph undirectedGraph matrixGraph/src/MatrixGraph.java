
/*
 *
 * @author Wes Garner
 * @date 29MAR2017
 * @filename MatrixGraph.java
 * @version 1
 * Lab Report 10: Implementation of Graphs
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class MatrixGraph {
    public int V;
    public int E;
    
    public boolean[][] adj;
    
    public MatrixGraph()
    {
        V = 0;
        E = 0;
    }
    
	public MatrixGraph(BufferedReader reader) throws IOException
    {
        String line;
        line = reader.readLine();
        V = Integer.parseInt(line);
        line = reader.readLine();
        E = Integer.parseInt(line);
        adj = new boolean[V][V];
        while ((line = reader.readLine()) != null) {
            int tempV1, tempV2;
              StringTokenizer st = new StringTokenizer(line, " ");
              tempV1 = Integer.parseInt(st.nextToken());
              tempV2 = Integer.parseInt(st.nextToken());
              addEdge(tempV1, tempV2);
        }
    }
    
    
	public void addEdge(int v, int w)
	{
        E++;
        adj[v][w] = true;
        adj[w][v] = true;
	}
    
}
