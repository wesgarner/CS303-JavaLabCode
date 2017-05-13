/*
*
* @author Wes Garner
* @date 8FEB2017
* @filename cs303lab5.java
* @version 2 [Version 1: Original, Version 2: Implements Homework Assignments]
* Lab Report 5: Implementation of a quick sort algorithm
* This package is copied from and based off of Lab 4's heap sort algorithm program
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
import java.util.stream.Stream;

public class cs303lab5 {
	public static void main(String[] args) {
		String inputPath = "/home/wesgarner/workspace/CS303-Lab5/src/Sorting";

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

		
		// Quick Sort - array creation and sort with timing		
		int[] quickArray = new int[intArray.length];
		System.arraycopy(intArray, 0, quickArray, 0, intArray.length);
		long quickStartTime = System.currentTimeMillis();
		quickSort(quickArray, 0, quickArray.length-1);
		long quickEndTime = System.currentTimeMillis();
		
		// Quick Sort utilizing a partition of 3 - array creation and sort with timing		
		int[] quickP3Array = new int[intArray.length];
		System.arraycopy(intArray, 0, quickP3Array, 0, intArray.length);
		long quickP3StartTime = System.currentTimeMillis();
		quickP3Sort(quickP3Array, 0, quickP3Array.length-1);
		long quickP3EndTime = System.currentTimeMillis();
		
		// Create the temporary array for mergeSort and  a copy for heapSort and insertSort
		int[] tempArray = new int[intArray.length];
		int[] heapArray = new int[intArray.length];
		int[] insertArray = new int[intArray.length];
		System.arraycopy(intArray, 0, tempArray, 0, intArray.length);
		System.arraycopy(intArray, 0, heapArray, 0, intArray.length);
		System.arraycopy(intArray, 0, insertArray, 0, intArray.length);

		// Sort the array while timing via heapSort
		long heapStartTime = System.currentTimeMillis();
		heapSort(heapArray, heapArray.length);
		long heapEndTime = System.currentTimeMillis();
		
		// Sort the array while timing via mergeSort
		long mergeStartTime = System.currentTimeMillis();
		mergeSort(intArray, intArray, 0, intArray.length-1);
		long mergeEndTime = System.currentTimeMillis();
		
		// Sort the array while timing via insertSort
		long insertStartTime = System.currentTimeMillis();
		insertSort(insertArray, 0, intArray.length-1);
		long insertEndTime = System.currentTimeMillis();

		// Commented out because of the array length would overflow the console
		/*System.out.print("Sorted Array: ");
		for (int i=0; i<quickArray.length; i++)
			System.out.print(quickArray[i] + " ");*/
		
		// Send sort timing and information to the screen
		System.out.println("Number of Items: " + (quickArray.length-1) + "\nQuick Sort Time (ms): " + (quickEndTime-quickStartTime) + "\nPartitioned Quick Sort Time (ms): " + (quickP3EndTime-quickP3StartTime) + "\nHeap Sort Time (ms): " + (heapEndTime-heapStartTime) + "\nMerge Sort Time (ms): " + (mergeEndTime-mergeStartTime) +  "\nInsert Sort Time (ms): " + (insertEndTime-insertStartTime));
	}
	
	// Quick Sort
	public static void quickSort(int[] sortArray, int p, int r)
	{
		if (p<r) {
			int q = partition(sortArray, p, r);
			quickSort(sortArray, p, q-1);
			quickSort(sortArray, q+1, r);
		}
	}
	
	public static int partition(int[] sortArray, int p, int r)
	{
		int x = sortArray[r];
		int i = p-1;
		
		for (int j=p; j<=r-1; j++)
		{
			if (sortArray[j] <= x)
			{
				i = i+1;
				int temp = sortArray[i];
				sortArray[i] = sortArray[j];
				sortArray[j] = temp;
			}
		}

		int temp = sortArray[i+1];
		sortArray[i+1] = sortArray[r];
		sortArray[r] = temp;
		
		return i+1;
	}

	// Quick Sort using Median of 3 Partitioning
	public static void quickP3Sort(int[] sortArray, int p, int r)
	{
		int cutoff = 10; // Testing shows inserSort works better when array size <= 10
		
		int n = r-p+1;
		if (n <= cutoff)
		{
			insertSort(sortArray, p, r);
			return;
		}
		
		int m = median3(sortArray, p, p+(n/2), r);
		int temp = sortArray[m];
		sortArray[m] = sortArray[p];
		sortArray[p] = temp;
		
		int q = partition(sortArray, p, r);
		quickSort(sortArray, p, q-1);
		quickSort(sortArray, q+1, r);
	}
	
	public static int median3(int[] sortArray, int i, int j, int k)
	{
		if (sortArray[j]<sortArray[i] && sortArray[i]<sortArray[k])
			return i;
		
		else if (sortArray[i]<sortArray[j] && sortArray[j]<sortArray[k])
			return j;
		
		else
			return k;
	}
	
	// Insert Sort redesigned to work with quickP3Sort
	public static void insertSort(int[] sortArray, int p, int r)
	{
		for (int j=p; j<=r; j++)
		{
			int key = sortArray[j];

			// Insert sortArray[i] into the sorted sequence sortArray[0...j-1]
			int i = j-1;
			while (i>0 && sortArray[i] > key)
			{
				sortArray[i+1] = sortArray[i];
				i = i-1;
			}
			sortArray[i+1] = key;
		}
	}
		
	// Heap Sort
	public static void heapSort(int[] sortArray, int n)
	{
		buildMaxHeap(sortArray, n-1);

		for (int i = n-1; i>=0; i--)
		{
			int temp = sortArray[0];
			sortArray[0] = sortArray[i];
			sortArray[i] = temp;

			maxHeapify(sortArray, 0, i-1);
		}
	}

	public static void buildMaxHeap(int[] sortArray, int n)
	{
		for (int i = n/2; i>=0; i--)
			maxHeapify(sortArray, i, n);
	}

	public static void maxHeapify(int[] sortArray, int i, int n)
	{
		int l = i*2+1;
		int r = i*2+2;
		int largest = 0;

		if (l<=n && sortArray[l]>sortArray[i])
			largest = l;
		else
			largest = i;

		if (r<=n && sortArray[r]>sortArray[largest])
			largest = r;

		if (largest != i) {
			int temp = sortArray[i];
			sortArray[i] = sortArray[largest];
			sortArray[largest] = temp;
			maxHeapify(sortArray, largest, n);
		}
	}

	// Merge Sort
	public static void mergeSort(int[] sortArray, int[] tempArray, int p, int r)
	{
		if (p<r) {
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
}
