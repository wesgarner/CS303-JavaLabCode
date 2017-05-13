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

	HashMap(int tableSize) {
		TABLE_SIZE = tableSize;
		table = new HashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}

	public String get(int key) {
		int index = key % TABLE_SIZE; // H(key) = key mod mapsize

		while (table[index].getKey() != key)
			index = ((7*index)+1) % TABLE_SIZE; // H(key)=(7*H(key)+1) mod mapsize

		return table[index].getValue();
	}

	public void put(int key, String value) {
		int index = key % TABLE_SIZE; // H(key) = key mod mapsize

		while (table[index] != null && table[index].getKey() != key) {
			System.out.println("Collission detected at index " + index);
			index = ((7*index)+1) % TABLE_SIZE; // H(key)=(7*H(key)+1) mod mapsize
		}

		if (table[index] == null) {
			System.out.println("Key " + key + " placed at index " + index);
			table[index] = new HashEntry(key, value);
		}
		else { // Update value on current key instead of using a brand new one
			System.out.println("Updated value at key " + key);
			table[index].setValue(value);
		}
	}

	public void linearProbe(int key, String value){
		int index = key % TABLE_SIZE; // Prepare variable in case original is empty, below will not modify this as 0 is used

		for (int i = 0; table[index] != null; i++) {
			System.out.println("Collission detected at index " + index);
			index = (index + i) % TABLE_SIZE; // H(key) = (H(key) + i) mod mapsize
		}

		System.out.println("Key " + key + " placed at index " + index);
		table[index] = new HashEntry(key, value);
	}

	public String linearProbeSearch(int key){
		int index = key % TABLE_SIZE; // Prepare variable in case original is empty, below will not modify this as 0 is used

		for (int i = 0; table[index].getKey() != key; i++)
			index = (index + i) % TABLE_SIZE; // H(key) = (H(key) + i) mod mapsize

		return table[index].getValue();
	}

	public void quadraticProbe(int key, String value){
		int index = key % TABLE_SIZE; // Prepare variable in case original is empty, below will not modify this as 0 is used

		for (int i = 0; table[index] != null; i++) {
			// System.out.println("Collission detected at index " + index);
			index = (index + (int) Math.pow(i, 2)) % TABLE_SIZE; // H(key) = (H(key) + i^2) mod mapsize
		}

		// System.out.println("Key " + key + " placed at index " + index);
		table[index] = new HashEntry(key, value);
	}

	public String quadraticProbeSearch(int key){
		int index = key % TABLE_SIZE; // Prepare variable in case original is empty, below will not modify this as 0 is used

		for (int i = 0; table[index].getKey() != key; i++)
			index = (index + (int) Math.pow(i, 2)) % TABLE_SIZE; // H(key) = (H(key) + i^2) mod mapsize

		return table[index].getValue();
	}

}
