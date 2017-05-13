import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/*
 *
 * @author Wes Garner
 * @date 22MAR2017
 * @filename cs303lab9.java
 * @version 1
 * Lab Report 9: Implementation of Hash Table
 * Driver Application
 * 
 */

public class cs303lab9 {
	public static void main(String[] args) {
		labProblem();
		// hwProblem();
	}
	public static void labProblem() {
		// Implement Driver Program for get/put
		System.out.println("Beginning Lab Assignment...");
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
		System.out.println("Key 0 found, String: " + hashmap.get(0));
		System.out.println("Key 2 found, String: " + hashmap.get(2));
	}

	public static void hwProblem() {
		System.out.println("\n\nBeginning Homework Assignment...");
		// Variables to be used to create hash table and search it
		int TABLE_SIZE = 225555;
		int SEARCH_SIZE = 17;
		int KEY_SIZE = 0;
		int collisions = 0;
		String[] keyArray = new String[TABLE_SIZE]; // Size of key file
		String[] searchArray = new String[SEARCH_SIZE];
		HashMap hashmap = new HashMap();

		try {
			System.out.println("Placing files into arrays...");
			// Files to be used to create has table and search it
			String searchPath = "/home/wesgarner/workspace/CS303-Lab9/src/input.txt";
			String keyPath = "/home/wesgarner/workspace/CS303-Lab9/src/UPC.csv";
			
			// Search file array creation
			FileInputStream fstream = new FileInputStream(searchPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					
			String strLine;
			for (int i=0; (strLine = br.readLine()) != null; i++)
				searchArray[i] = strLine;
			
			// Key file array creation
			
			fstream = new FileInputStream(keyPath);
			br = new BufferedReader(new InputStreamReader(fstream));

			for (KEY_SIZE=0; (strLine = br.readLine()) != null; KEY_SIZE++)
				keyArray[KEY_SIZE] = strLine;

			// Close the buffered reader and input stream
			br.close();
			fstream.close();

		} catch (Exception exc) {
			System.out.print("Error loading files... ");
			System.out.println(exc.toString());
		}

		// Original Function used above
		System.out.println("Beginning Original Function...");
		/*hashmap = new HashMap(TABLE_SIZE); // Create new HashMap
		long origStartTime = System.currentTimeMillis();
		for (int i=0; i < KEY_SIZE; i++) {
			// Parse CSV line and add to hash map
			String[] str = keyArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String value = str[2] + ", " + str[1];
			
			// collisions += hashmap.put(key, value);
		}
		long origEndTime = System.currentTimeMillis();*/
		System.out.println("Creation time (ms): Infinite");
		System.out.println("Total collisisons: Infinite");

		// Linear Probing
		System.out.println("Beginning Linear Probing...");
		collisions = 0;
		hashmap = new HashMap(TABLE_SIZE); // Create new HashMap
		long linearStartTime = System.currentTimeMillis();
		for (int i=0; i < KEY_SIZE; i++) {
			// Parse key line and add to hash map
			String[] str = keyArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String value = str[2] + ", " + str[1];
			
			collisions += hashmap.linearProbe(key, value);;
		}
		long linearEndTime = System.currentTimeMillis();

		
		// Linear Probing Search
		System.out.println("Beginning Linear Search...");
		long linearSStartTime = System.currentTimeMillis();
		for (int i=0; i < searchArray.length; i++) {
			// Parse search line
			String[] str = searchArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			
			System.out.println("Found key " + key + ": " + hashmap.linearProbeSearch(key));
		}
		long linearSEndTime = System.currentTimeMillis();
		System.out.println("Creation time (ms):" + (linearEndTime-linearStartTime));
		System.out.println("Total collisisons: " + collisions);
		System.out.println("Search time (ms):" + (linearSEndTime-linearSStartTime));


		// Quadratic Probing
		System.out.println("\nBeginning Quadratic Probing...");
		collisions = 0;
		hashmap = new HashMap(TABLE_SIZE); // Create new HashMap
		long quadStartTime = System.currentTimeMillis();
		for (int i=0; i < KEY_SIZE; i++) {
			// Parse key line and add to hash map
			String[] str = keyArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			String value = str[2] + ", " + str[1];
			
			collisions += hashmap.quadraticProbe(key, value);;
		}
		long quadEndTime = System.currentTimeMillis();

		
		// Quadratic Probing Search
		System.out.println("Beginning Quadratic Search...");
		long quadSStartTime = System.currentTimeMillis();
		for (int i=0; i < searchArray.length; i++) {
			// Parse search line and add to hash map
			String[] str = searchArray[i].split(",");
			double key = Double.parseDouble(str[0]);
			
			System.out.println("Found key " + key + ": " + hashmap.quadraticProbeSearch(key));
		}
		long quadSEndTime = System.currentTimeMillis();
		System.out.println("Creation time (ms):" + (quadEndTime-quadStartTime));
		System.out.println("Creation collisisons: " + collisions);
		System.out.println("Search time (ms):" + (quadSEndTime-quadSStartTime));
	}
}
