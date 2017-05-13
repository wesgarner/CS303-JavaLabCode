/*
 *
 * @author Wes Garner
 * @date 15FEB2017
 * @filename cs303lab6.java
 * @version 2 [Version 1: Original, Version 2: Implements Homework Assignments]
 * Lab Report 6: Implementation of novel sort algorithms
 * This package is copied from and based off of previous labs
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class cs303lab6 {
	public static void main(String[] args) {
		runIntSort();
		radixSort();
	}
	public static void runIntSort()
	{
		int[] intArray = null;

		// Grab file for integer sort and later for category sort
		String inputFile = "/home/wesgarner/workspace/CS303-Lab6/src/Sorting/input_100.txt";

		// Load file into array
		System.out.println("\nLoading... " + inputFile);
		try {
			FileInputStream fstream = new FileInputStream(inputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine = br.readLine();
			String[] strArray = strLine.split(" ");

			// Close the buffered reader and input stream
			br.close();
			fstream.close();

			// Parse string array into intArray ignoring the first position in the array as it is blank
			intArray = new int[strArray.length];
			for (int i=1; i<strArray.length; i++) {	intArray[i] = Integer.parseInt(strArray[i]); }

		} catch (Exception exc) {
			System.out.println("Error loading file... " + inputFile);
			System.out.println(exc.toString());
		}

		minMaxSort(intArray, 0, intArray.length-1);

		System.out.print("Sorted Array: ");
		for (int i=0; i<intArray.length; i++)
			System.out.print(intArray[i] + " ");
	}

	// Sort by finding the minimum and maximum
	public static void minMaxSort(int[] sortArray, int p, int r)
	{
		if (p<=r)
		{
			// Assume p can be both min and max as this will be tested during for loop
			int min = p, max = p;
			for (int i=p; i<=r; i++)
			{
				if (sortArray[i] < sortArray[min])
					min = i;
				else if (sortArray[i] > sortArray[max])
					max = i;
			}

			swapInt(sortArray, p, min, r, max); // Swap the new minimum to first spot and max to the last spot

			minMaxSort(sortArray, p+1, r-1); // Run the sort again with smaller array size
		}
	}

	public static void swapInt(int[] intArray, int a, int b, int c, int d)
	{
		// Create temporary variables x and y so that swapping issues don't occur where p could be max or q could be min
		int w = intArray[a];
		int x = intArray[b];
		int y = intArray[c];
		int z = intArray[d];

		intArray[a] = x;
		intArray[b] = w;
		intArray[c] = z;
		intArray[d] = y;
	}

	public static void radixSort()
	{
		int numLines;
		String[] lineList = null;

		// Load file into array
		String inputFile = "/home/wesgarner/workspace/CS303-Lab6/src/Sorting/NovelSortInput.txt";
		System.out.println("\n\nLoading... " + inputFile);
		try {
			// Detect number of lines so to create array
			FileInputStream fstream = new FileInputStream(inputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			numLines = (int) br.lines().count();
			br.close();
			fstream.close();

			// Re-open stream reader so that can now load the array
			fstream = new FileInputStream(inputFile);
			br = new BufferedReader(new InputStreamReader(fstream));
			lineList = new String[numLines];
			for (int i=0; i<numLines; i++)
				lineList[i] = br.readLine();

			br.close();
			fstream.close();
		} catch (Exception exc) {
			System.out.println("Error loading file... " + inputFile);
			System.out.println(exc.toString());
		}

		// Create temporary array for String MergeSort
		String[] tempList = new String[lineList.length];
		System.arraycopy(lineList, 0, tempList, 0, lineList.length);

		mergeSort(lineList, tempList, 0, lineList.length-1);

		for (int i=0; i<lineList.length; i++)
			System.out.println(lineList[i]);

	}

	// Merge Sort (modified to work with Java String compareTo)
	public static void mergeSort(String[] sortArray, String[] tempArray, int p, int r)
	{
		if (p<r) {
			int q = (p+r)/2;
			mergeSort(sortArray, tempArray, p, q);
			mergeSort(sortArray, tempArray, q+1, r);
			merge(sortArray, tempArray, p, q, r);
		}
	}
	public static void merge(String[] sortArray, String[] tempArray, int p, int q, int r)
	{
		// merge A[p..q] with A[q+1..r]
		int i = p;
		int j = q+1;

		// copy A[p..r] to temp[p..r]
		for (int k = p; k<=r; k++)
			tempArray[k] = sortArray[k];

		// merge back to A[p..r]
		for (int k = p; k<=r; k++)
		{
			if (i>q) { // left half empty, copy from the right
				sortArray[k] = tempArray[j];
				j = j+1;
			}
			else if (j>r) { // right half empty, copy from the left
				sortArray[k] = tempArray[i];
				i = i+1;
			}
			else if (tempArray[j].split(" ")[0].compareTo(tempArray[i].split(" ")[0]) < 0) { // copy from the right
				sortArray[k] = tempArray[j];
				j = j+1;
			}
			else {
				sortArray[k] = tempArray[i]; // copy from the left
				i = i+1;
			}
		}
	}
}