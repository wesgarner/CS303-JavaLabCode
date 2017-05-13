/*
 *
 * @author Wes Garner
 * @date 2APR2017
 * @filename MatrixChainMultiplication.java
 * @version 1
 * Term Project: Matrix Chain Multiplication
 *
 */

import java.util.Random;

public class MatrixChainMultiplication {
	// Initial Variables
	private final static int inf = Integer.MAX_VALUE; // infinite
	static int m[][];
	static int s[][];
	static private int matrices[][][];
	
	public static void main(String[] args) {
		int p[] = { 30, 35, 15, 5, 10, 20, 25 };
		int size = p.length-1;
		
		// Create matrices
		matrices = new int[size][][];
		Random rand = new Random();
		for (int i=0; i<size; i++)
		{
			matrices[i] = new int[p[i]][p[i+1]]; 
			for (int j=0; j<p[i]; j++)
				for (int k=0; k<p[i+1]; k++) {
					matrices[i][j][k] = rand.nextInt(100);
				}
		}
		
		System.out.print("Input: ");
		for (int i=0; i<p.length; i++)
			System.out.print(p[i] + " ");
		System.out.print("\n\n");
		
		bottomUp(p); // bottom up algorithms
		topDown(p); // top down algorithms
	}
	
	public static void bottomUp(int[] A) {
		System.out.println("Bottom up algorithms");
		
		// Run and time Matrix Chain Order
		long nanoStartTime = System.nanoTime(); // Start timer
		int matrixResult =  matrixChainOrder(A);
		long nanoEndTime = System.nanoTime(); // End timer
		long mulitplyStartTime = System.nanoTime();
		matrixChainMultiply(matrices.clone(), s, 1, s.length-1);
		long mulitplyEndTime = System.nanoTime();
		
		
		// Print Optimal Parens results
		System.out.print("\nMatrix Chain Order\nComputations: " + matrixResult);
		System.out.print("\nChain Runtime: " + (nanoEndTime-nanoStartTime) + "\nMultiply Runtime: " + (mulitplyEndTime-mulitplyStartTime) + "\nOrder: ");
		printOptimalParens(s, 1, s.length-1);
		
	}
	
	public static void topDown(int[] A) {
		
		System.out.println("\n\nTop down algorithms");
		
		m = new int[A.length][A.length];
		s = new int[A.length][A.length];
		
		// Run and time Recursive Matrix Chain
		long nanoStartTime = System.nanoTime(); // Start timer
		int matrixResult =  recursiveMatrixChain(A, 1, A.length-1);
		long nanoEndTime = System.nanoTime(); // End timer
		long mulitplyStartTime = System.nanoTime();
		matrixChainMultiply(matrices.clone(), s, 1, s.length-1);
		long mulitplyEndTime = System.nanoTime();

		
		// Print Optimal Parens results
		System.out.print("\nRecursive Matrix Chain\nComputations: " + matrixResult);
		System.out.print("\nChain Runtime: " + (nanoEndTime-nanoStartTime) + "\nMultiply Runtime: " + (mulitplyEndTime-mulitplyStartTime) + "\nOrder: ");
		printOptimalParens(s, 1, s.length-1);
		
		
		// Run and time Memoized Matrix Chain (No need to clear m as is filled with inf)
		nanoStartTime = System.nanoTime(); // Start timer
		matrixResult =  memoizedMatrixChain(A);
		nanoEndTime = System.nanoTime(); // End timer
		mulitplyStartTime = System.nanoTime();
		matrixChainMultiply(matrices.clone(), s, 1, s.length-1);
		mulitplyEndTime = System.nanoTime();
		
		// Print Optimal Parens and results
		System.out.print("\n\nMemoized Matrix Chain\nComputations: " + matrixResult);
		System.out.print("\nChain Runtime: " + (nanoEndTime-nanoStartTime) + "\nMultiply Runtime: " + (mulitplyEndTime-mulitplyStartTime) + "\nOrder: ");
		printOptimalParens(s, 1, s.length-1);
	}
	
	// MATRIX-CHAIN-MULTIPLY(A, s, i, j)
	public static int[][] matrixChainMultiply(int A[][][], int[][] s, int i, int j)
	{
		if (i == j) {
			return A[i-1];
		}
		else {
			int[][] y = matrixChainMultiply(A, s, i, s[i][j]);
			int[][] z = matrixChainMultiply(A, s, s[i][j]+1, j);
			return matrixMultiply(y, z);
		}
	}
	
	// MATRIX-MULTIPLY(a, b)	
	public static int[][] matrixMultiply(int[][] a, int[][] b) {
	       int rowsA = a.length;
	       int colsA = a[0].length;
	       int colsB = b[0].length;
	       
	       int[][] c = new int[rowsA][colsB];
	       for (int i = 0; i<rowsA; i++) {
	           for (int j = 0; j<colsB; j++) {
	               for (int k = 0; k<colsA; k++) {
	                   c[i][j] = c[i][j] + a[i][k] * b[k][j];
	               }
	           }
	       }
	       return c;
	   }

	// Bottom-up Approaches	
	// MATRIX-CHAIN-ORDER(p)
	public static int matrixChainOrder(int p[])
	{
		int n = p.length;
		m = new int[n][n];
		s = new int[n][n]; // s is cost

		for (int i = 0; i<n; i++)
			m[i][i] = 0;

			
		for (int l = 2; l<n; l++) // l is chain length
		{
			for (int i = 1; i<n-l+1; i++)
			{
				int j = i + l-1;
				m[i][j] = inf;
				for (int k = i; k<=j-1; k++)
				{
					int q = m[i][k] + m[k+1][j] + p[i-1] * p[k] * p[j];
					if (q < m[i][j])
					{
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
		return m[1][n-1];
	}

	// PRINT-OPTIMAL-PARENS(s, i j)
	public static void printOptimalParens(int[][] s, int i, int j)
	{
		if (i == j)
			System.out.print("A" + j);
		else {
			System.out.print("(");
			printOptimalParens(s, i, s[i][j]);
			printOptimalParens(s, s[i][j] + 1, j);
			System.out.print(")");
		}
	}

	// Top-Down Approaches
	// RECURSIVE-MATRIX-CHAIN(p, i, j)
	public static int recursiveMatrixChain(int p[], int i, int j)
	{
		if (i == j)
			return 0;
		
		m[i][j] = inf;
		
		for (int k = i; k<=j-1; k++)
		{
			int q = recursiveMatrixChain(p, i, k) + recursiveMatrixChain(p, k+1, j) + p[i-1] * p[k] * p[j];
			if (q < m[i][j]) {
				m[i][j] = q;
				s[i][j] = k;
			}
		}
		
		return m[i][j];
	}
	
	// MEMOIZED-MATRIX-CHAIN(p)
	public static int memoizedMatrixChain(int p[]) {
		int n = p.length-1;
		
		// Store infinite in all values in matrix m (aka uncompleted)
		for (int i = 0; i <= n; i++)
			for (int j = 0; j <= n; j++)
				m[i][j] = inf;

		return lookupChain(p, 1, n);
		
	}
	
	public static int lookupChain(int p[], int i, int j)
	{
		if (m[i][j] < inf)
			return m[i][j];
		
		if (i == j)
			m[i][j] = 0;
		
		else {
			for (int k = i; k < j; k++)
			{
				int q = lookupChain(p, i, k) + lookupChain(p, k+1, j) + p[i-1] * p[k] * p[j];
				if (q < m[i][j]) {
					m[i][j] = q;
					s[i][j] = k;
				}
			}
		}
		
		return m[i][j];
	}

}
