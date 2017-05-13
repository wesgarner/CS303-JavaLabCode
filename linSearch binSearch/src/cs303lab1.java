import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class cs303lab1 {
	public static void main(String[] args) {
		// Create key file with 1000 numbers ranging from 1-2^25 (if does not exist)
		String file = "key.txt";
		File f = new File(file);
		if (!f.exists())
			createKey(file);

		// Loop to create arrays sized 2^i and run application
		for (int n=4; n<=25; n++)
		//	runSearch(n, file); // Search using a single key once
		bulkSearch(n, file); // Search using 2^n keys in single array
	}

	// Search using a single key per run (with increasing array sizes as in main)
	public static void runSearch(int n, String file) {
		int key = 0;
		Random rand = new Random();

		// Find random key from file 
		try { key = Integer.parseInt(Files.readAllLines(Paths.get(file)).get(n-1));
		} catch (Exception exc) { System.out.println("Unable to read file...\nException: " + exc.toString()); }

		// Initialize and create random integer array in the size of arraySize[n]
		int arraySize = (int) Math.pow(2, n);
		int[] testArray = new int[arraySize];
		for (int i=0; i<arraySize; i++)
			testArray[i] = rand.nextInt(arraySize);

		System.out.println("\n\tKey used: " + key);
		/* Sends array to console
		String str = "";
		for (int i=0; i<testArray.length; i++)
			str = str + " " + testArray[i];
		System.out.println("\n\tArray (size: " + testArray.length + "): " + str); */

		System.out.println("\tArray size: " + testArray.length); // Send array size to console

		// Begin linear search and calculate start and end time
		System.out.println("   Beginning Linear Search");
		long startTime = System.currentTimeMillis();
		linearSearch(testArray, key);
		long endTime = System.currentTimeMillis();
		System.out.println("Linear Search Time (ms): " + (endTime-startTime));

		// Begin recursive binary search and calculate start and end time
		System.out.println("   Beginning Recursive Binary Search");
		startTime = System.currentTimeMillis();
		Arrays.sort(testArray);
		int x = binarySearch(testArray, key, 0, testArray.length-1);
		endTime = System.currentTimeMillis();
		System.out.println("Binary Search Time (ms): " + (endTime-startTime));
	}

	// Search using keys 1-2^25 in a bulk run to calculate average time
	public static void bulkSearch(int n, String file) {
		long linearTime = 0, binaryTime = 0;
		Random rand = new Random();

		// Initialize and create random integer array in the size of arraySize[n]
		int arraySize = (int) Math.pow(2, n);
		int[] testArray = new int[arraySize];
		for (int i=0; i<arraySize; i++)
			testArray[i] = rand.nextInt(arraySize);


		System.out.println("\tArray size: " + testArray.length); // Send array size to console

		// Grab file to use as key
		try {
			FileInputStream fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


			for (int i=1; i<=arraySize; i++)
			{
				// Grab key from line i
				int key = Integer.parseInt(br.readLine());

				// Begin linear search and calculate start and end time
				long startTime = System.currentTimeMillis();
				linearSearch(testArray, key);
				long endTime = System.currentTimeMillis();
				linearTime += (endTime-startTime);

				// Begin recursive binary search and calculate start and end time
				startTime = System.currentTimeMillis();
				Arrays.sort(testArray);
				int x = binarySearch(testArray, key, 0 , testArray.length);
				endTime = System.currentTimeMillis();
				binaryTime += (endTime-startTime);
			}

			// Close buffered reader and fileinputstream
			br.close();
			fstream.close();
		} catch (Exception exc) { System.out.println("Unable to read file...\nException: " + exc.toString()); }

		System.out.println("Total Linear Search Time (ms): " + linearTime);
		System.out.println("Average Linear Search Time (ms): " + (linearTime/arraySize));
		System.out.println("Total Binary Search Time (ms): "  + binaryTime);
		System.out.println("Average Binary Search Time (ms): " + (binaryTime/arraySize));
	}


	// Linear Search
	public static void linearSearch(int[] searchArray, int numtoFind)
	{

		for (int i=0; i<searchArray.length; i++)
		{
			if (searchArray[i] == numtoFind) {
				//System.out.println("The number " + numtoFind + " was found at position " + (i+1));
				return;
			}
		}
		//System.out.println("The number " + numtoFind + " was not found in the array"); 
		return;
	}

	// Recursive Binary Search
	public static int binarySearch(int[] searchArray, int numtoFind, int lowNum, int highNum)
	{
		int midNum = (lowNum+highNum)/2;
		if (highNum < lowNum)
		{
			//System.out.println("The number " + numtoFind + " was not found in the array");
			return -1;
		}
		if (searchArray[midNum] == numtoFind) { 
			//System.out.println("The number " + numtoFind + " was found at position " + (midNum+1));
			return -1;
		}
		else if (searchArray[midNum] > numtoFind)
			return binarySearch(searchArray, numtoFind, lowNum, midNum-1);
		else if (searchArray[midNum] < numtoFind)
			return binarySearch(searchArray, numtoFind, midNum+1, highNum);
		return -1;
	}

	// Create key file of n integers where 1<rand<1000
	public static void createKey(String file)
	{
		// Starting variables 
		File f = new File(file);
		Random rand = new Random();
		int lines = 25; // Number of lines to write
		// int lines = (int) Math.pow(2, 25); // Number of lines to write (bulk search)


		try {
			f.createNewFile();
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for (int i=0; i<lines; i++)
				writer.println(rand.nextInt(1000)); // Random number between 1 and 1000
			writer.close();
		} catch (Exception exc) { System.out.println("Unable to write file...\nException: " + exc.toString()); }

	}
}
