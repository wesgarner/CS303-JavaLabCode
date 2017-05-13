/*
 *
 * @author Wes Garner
 * @date 25JAN2017
 * @filename cs303lab3.java
 * @version 2 [Version 1: Original, Version 2: Implements Homework Assignments]
 * Lab Report 3: Implementation of a merge sort algorithm while comparing to insertion
 * This package is copied from and based off of Lab 2's insert sort algorithm
 * Read up on some of the input file stream code via stackoverflow
 * Path Stream code was taken from: http://stackoverflow.com/questions/1844688/read-all-files-in-a-folder
 *
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

public class cs303lab3 {
	public static void main(String[] args) {
		String inputPath = "/home/wesgarner/workspace/CS303-Lab3/src/Sorting";

		// Open path and run lab assignment on each file
		try(Stream<Path> paths = Files.walk(Paths.get(inputPath))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					runSort(filePath.toString());
				}
			});
		} catch (Exception exc) {
			// Catch Error and output to screen
			System.out.println("Error loading files in path... " + inputPath);
			System.out.println(exc.toString());
		}

	}
	public static void runSort(String inputFile)
	{
		int[] intArray = null;

		// Load file into array
		System.out.println("Loading... " + inputFile);
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

		// Create the temporary array and  a copy for insertSort
		int[] tempArray = new int[intArray.length];
		int[] insertArray = new int[intArray.length];
		System.arraycopy(intArray, 0, tempArray, 0, intArray.length);
		System.arraycopy(intArray, 0, insertArray, 0, intArray.length);

		/* Ignore this set of code as was used to present to TA and evaluate insertion vs. merge performance
		*  // Sort the array while timing via mergeSort
		*  long mergeStartTime = System.currentTimeMillis();
		*  mergeSort(intArray, tempArray, 0, (intArray.length-1));
		*  long mergeEndTime = System.currentTimeMillis();
		*
		*
		*  // Sort the array while timing via insertSort
		*  long insertStartTime = System.currentTimeMillis();
		*  insertSort(insertArray);
		*  long insertEndTime = System.currentTimeMillis();
		*  // Send sorted array to screen with timing
		*  // Commented out because of the array length would overflow the console
		*  System.out.print("Sorted Array: ");
		*  for (int i=0; i<intArray.length; i++)
		*	 System.out.print(intArray[i] + " ");
		*  System.out.println("\nNumber of Items: " + intArray.length + "\nMerge Sort Time (ms): " + (mergeEndTime-mergeStartTime) + "\nInsert Sort Time (ms): " + (insertEndTime-insertStartTime));
		*/
		
		// Implement and time the sorts
			long startTime = System.currentTimeMillis();
			mergeSort(intArray, tempArray, 0, (intArray.length-1));
			long endTime = System.currentTimeMillis();
		
		// Send sort timing and information to the screen
		System.out.println("Number of Items: " + intArray.length + "\nSort Time (ms): " + (endTime-startTime));
		
	}

	// Merge Sort
	public static void mergeSort(int[] sortArray, int[] tempArray, int p, int r)
	{
		// Analysis showed that mergeSort is less efficient than insertSort at array sizes >= 100
		if ((r-p)>=100)
			insertSort(sortArray);
		else if (p<r) {
			int q = (p+r)/2;
			mergeSort(sortArray, tempArray, p, q);
			mergeSort(sortArray, tempArray, q+1, r);
			merge(sortArray, tempArray, p, q, r);
		}
	}
	public static void merge(int[] sortArray, int[] tempArray, int p, int q, int r)
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
			else if (tempArray[j] < tempArray[i]) { // copy from the right
				sortArray[k] = tempArray[j];
				j = j+1;
			}
			else {
				sortArray[k] = tempArray[i]; // copy from the left
				i = i+1;
			}
		}
	}

	// Insert Sort
	public static void insertSort(int[] sortArray)
	{
		for (int j=0; j<sortArray.length; j++)
		{
			int key = sortArray[j];

			// Insert sortArray[i[ into the sorted sequence sortArray[0...j-1]
			int i = j-1;
			while (i>0 && sortArray[i] > key)
			{
				sortArray[i+1] = sortArray[i];
				i = i-1;
			}
			sortArray[i+1] = key;
		}
	}
}
