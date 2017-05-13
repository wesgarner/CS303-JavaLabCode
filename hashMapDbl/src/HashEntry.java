/*
 *
 * @author Wes Garner
 * @date 22MAR2017
 * @filename HashEntry.java
 * @version 1
 * Lab Report 9: Implementation of Hash Table
 *
 */


public class HashEntry {
	private int key;
	private String value;

	HashEntry(int key, String value) {
		this.key = key;
		this.value = value;
	}     

	public int getKey() {
		return key;
	}

	public String  getValue() {
		return value;
	}

	public void setValue(String val) {
		this.value = val;
	}
}
