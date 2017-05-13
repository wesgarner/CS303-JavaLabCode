/*
 *
 * @author Wes Garner
 * @date 22MAR2017
 * @filename HashEntryDbl.java
 * @version 1
 * Lab Report 9: Implementation of Hash Table
 *
 */


public class HashEntryDbl {
	// Because of the size of the keys in UPC.csv, a double must be used
	private double key;
	private String value;

	HashEntryDbl(double key, String value) {
		this.key = key;
		this.value = value;
	}     

	public double getKey() {
		return key;
	}

	public String  getValue() {
		return value;
	}

	public void setValue(String val) {
		this.value = val;
	}
}
