/*
 *
 * @author Wes Garner
 * @date 22MAR2017
 * @filename HashMap.java
 * @version 1
 * Lab Report 9: Implementation of Hash Table
 *
 */


public class HashMap {
	private int TABLE_SIZE = 100;

	HashEntry[] table;

	HashMap() {
		table = new HashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}
	// Allow the user to decide how large the table size can be
	HashMap(int size) {
		TABLE_SIZE = size;
		table = new HashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}

	public String get(double key) {
		int index = (int) (key % TABLE_SIZE); // H(key) = key mod mapsize

		while (table[index].getKey() != key)
			index = (7*index+1) % TABLE_SIZE; // H(key)=(7*H(key)+1) mod mapsize

		return table[index].getValue();
	}

	public int put(double key, String value) {
		int index = (int) (key % TABLE_SIZE); // H(key) = key mod mapsize
		int collisions = 0;
		while (table[index] != null && table[index].getKey() != key) {
			System.out.println("Collision detected at index " + index);
			collisions++;
			index = (7*index+1) % TABLE_SIZE; // H(key)=(7*H(key)+1) mod mapsize
		}

		if (table[index] == null) {
			System.out.println("Key " + key + " placed at index " + index);
			table[index] = new HashEntry(key, value);
		}
		else { // Update value on current key instead of using a brand new one
			System.out.println("Updated value at key " + key);
			table[index].setValue(value);
		}
		return collisions;
	}

	public int linearProbe(double key, String value){
		int index = (int) (key % TABLE_SIZE); // Prepare variable in case original is null, below will not modify this as 0 is used
		int collisions = 0;
		for (int i = 0; table[index] != null; i++) {
			//System.out.println("Collision detected at index " + index);
			collisions++;
			index = (index + i) % TABLE_SIZE; // H(key) = (H(key) + i) mod mapsize
		}

		//System.out.println("Key " + key + " placed at index " + index);
		table[index] = new HashEntry(key, value);
		return collisions;
	}

	public String linearProbeSearch(double key){
		int index = (int) (key % TABLE_SIZE); // Prepare variable in case original is null, below will not modify this as 0 is used

		for (int i = 0; table[index].getKey() != key; i++)
			index = (index + i) % TABLE_SIZE; // H(key) = (H(key) + i) mod mapsize

		return table[index].getValue();
	}

	public int quadraticProbe(double key, String value){
		int index = (int) (key % TABLE_SIZE); // Prepare variable in case original is null, below will not modify this as 0 is used
		int collisions = 0;
		for (int i = 0; table[index] != null; i++) {
			//System.out.println("Collision detected at index " + index);
			collisions++;
			index = (index + (int) Math.pow(i, 2)) % TABLE_SIZE; // H(key) = (H(key) + i^2) mod mapsize
		}

		// System.out.println("Key " + key + " placed at index " + index);
		table[index] = new HashEntry(key, value);
		return collisions;
	}

	public String quadraticProbeSearch(double key){
		int index = (int) (key % TABLE_SIZE); // Prepare variable in case original is null, below will not modify this as 0 is used

		for (int i = 0; table[index].getKey() != key; i++)
			index = (index + (int) Math.pow(i, 2)) % TABLE_SIZE; // H(key) = (H(key) + i^2) mod mapsize

		return table[index].getValue();
	}

}
