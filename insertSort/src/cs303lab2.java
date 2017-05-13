/*
 *
 * @author Wes Garner
 * @date 19JAN2017
 * @filename cs303lab2.java
 * @version 2 [Version 1: Original, Version 2: Implements Homework Assignments]
 * Lab Report 2: Implementation of an insertion sort algorithm
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

public class cs303lab2 {
	public static void main(String[] args) {
		String inputPath = "/home/wesgarner/workspace/CS303-Lab2/src/Sorting";
		
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
		
		// Sort the array while timing
		long startTime = System.currentTimeMillis();
		intArray = insertSort(intArray);
		long endTime = System.currentTimeMillis();
		
		// Send sorted array to screen with timing
		System.out.print("Sorted Array: ");
		/* for (int i=0; i<intArray.length; i++)
			System.out.print(intArray[i] + " "); */
		System.out.println("\nArray Sort Time (ms): " + (endTime-startTime) + "\nNumber of Items: " + intArray.length);
		
	}
	
	// Insert Sort
	public static int[] insertSort(int[] sortArray)
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
		return sortArray;
	}
}
