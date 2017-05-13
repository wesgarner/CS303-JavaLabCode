import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/*
 *
 * @author Wes Garner
 * @date 22MAR2017
 * @filename cs330lab9.java
 * @version 1
 * Lab Report 9: Implementation of Hash Table
 * Driver Application
 * 
 */

public class cs330lab9 {

	public static void main(String[] args) {
		// labProblem();
		hwProblem();
	}	
	public static void labProblem() {
		// Implement Driver Program for get/put
		// Create HashMap
		HashMap hashmap = new HashMap();

		// Put strings
		hashmap.put(100, "Write");
		hashmap.put(200, "a");
		hashmap.put(105, "driver");
		hashmap.put(205, "program");
		hashmap.put(305, "to");
		hashmap.put(502, "test");
		hashmap.put(102, "your");
		hashmap.put(2, "implementation");
		hashmap.put(5, "of");
		hashmap.put(105, "hash");
		hashmap.put(0, "table");

		// Get strings
		System.out.println("Key 105 found, String: " + hashmap.get(105));
		System.out.println("Key 205 found, String: " + hashmap.get(0));
		System.out.println("Key 2 found, String: " + hashmap.get(2));
	}

	public static void hwProblem() {
		// Variables to be used to create hash table and search it
		// int hashSize = 177650; // Size of UPC.csv
		int hashSize = 100;
		int collissions = 0;
		String[] csvArray = new String[hashSize];
		String[] keyArray = new String[hashSize];
		HashMapDbl hashmap = new HashMapDbl(hashSize); // Because of the size of the keys, a double must be used

		try {
			System.out.println("Placing files into arrays");
			// Files to be used to create has table and search it
			String csvPath = "/home/wesgarner/workspace/CS303-Lab9/src/UPC.csv";
			String keyPath = "/home/wesgarner/workspace/CS303-Lab9/src/input.txt";
			
			// Load files into arrays
			String strLine;
			
			// CSV file array creation
			FileInputStream fstream = new FileInputStream(csvPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					
			for (int i=0; i<10; i++)
				csvArray[i] = br.readLine();
			
			// Key file array creation
			fstream = new FileInputStream(keyPath);
			br = new BufferedReader(new InputStreamReader(fstream));
			
			for (int i=0; i<hashSize; i++)
				keyArray[i] = br.readLine();

			// Close the buffered reader and input stream
			br.close();
			fstream.close();

		} catch (Exception exc) {
			System.out.print("Error loading files... ");
			System.out.println(exc.toString());
		}

		// Original Function used above
		System.out.println("Beginning Original Function...");
		hashmap = new HashMapDbl(hashSize); // Create new HashMap
		long origStartTime = System.currentTimeMillis();
		for (int i=0; i < 10; i++) {
			// Parse CSV line and add to hash map
			String[] str = csvArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String value = str[2] + ", " + str[1];
			
			collissions += hashmap.put(key, value);
		}
		long origEndTime = System.currentTimeMillis();
		System.out.println("Creation time (ms):" + (origEndTime-origStartTime));
		System.out.println("Total collisisons: " + collissions);

		// Linear Probing
		System.out.println("Beginning Linear Probing...");
		collissions = 0;
		hashmap = new HashMapDbl(hashSize); // Create new HashMap
		long linearStartTime = System.currentTimeMillis();
		for (int i=0; i < 10; i++) {
			// Parse CSV line and add to hash map
			String[] str = csvArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String value = str[2] + ", " + str[1];
			
			collissions += hashmap.linearProbe(key, value);;
		}
		long linearEndTime = System.currentTimeMillis();
		System.out.println("Creation time (ms):" + (linearEndTime-linearStartTime));
		System.out.println("Total collisisons: " + collissions);

		// Quadratic Probing
		System.out.println("Beginning Quadratic Probing...");
		collissions = 0;
		hashmap = new HashMapDbl(hashSize); // Create new HashMap
		long quadStartTime = System.currentTimeMillis();
		for (int i=0; i < hashSize; i++) {
			// Parse CSV line and add to hash map
			String[] str = csvArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String value = str[2] + ", " + str[1];
			
			collissions += hashmap.quadraticProbe(key, value);;
		}
		long quadEndTime = System.currentTimeMillis();
		System.out.println("Creation time (ms):" + (quadEndTime-quadStartTime));
		System.out.println("Total collisisons: " + collissions);
	}
}
