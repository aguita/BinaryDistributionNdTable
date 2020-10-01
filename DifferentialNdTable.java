import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Quick and Dirty class I created for creating the Differential Nd table in
 * Differential Cryptanalysis.
 * 
 * @author Aguita
 *
 */
public class DifferentialNdTable {
	private TreeMap<String, String> sBoxMap;
	private TreeMap<String, Integer> resultMap;

	public DifferentialNdTable() {
		sBoxMap = new TreeMap<String, String>();
		resultMap = new TreeMap<String, Integer>();
	}

	/**
	 * X-ORS two binary strings
	 * 
	 * @param a the first string
	 * @param b the second string
	 * @param n the length of the string
	 * @return the XOR'd value
	 */
	public String xoring(String a, String b, int n) {
		String ans = "";
		for (int i = 0; i < n; i++) {
			// If the Character matches, add a zero because 1+1 = 0 and 0+0 = 0
			if (a.charAt(i) == b.charAt(i)) {
				ans += "0";
			} else {
				// If it doesn't match, then the result will always be 1. 0+1 = 1, 1+0 = 1.
				ans += "1";
			}
		}
		return ans;
	}

	/**
	 * Parse the values and print the table.
	 * 
	 * @param mainValue the value to use -- it'll be the binary value of the HEX
	 *                  characters from 0-F
	 */
	public void parseAndPrintNdBox(String mainValue, List<String> ndList) {
		initializeMap(resultMap);
		int n = mainValue.length();
		for (String ndString : ndList) {
			// XOR the X and X' to get X*
			String result = xoring(mainValue, ndString, n);
			// XOR each of the values of HEX between 0-F
			// XOR the Y and Y*. Y is gotten by looking up the hex value within the SBox of
			// X, and Y* is retrieved by looking up the hex value within SBox of X*
			String result2 = xoring(sBoxMap.get(ndString), sBoxMap.get(result), n);
			// Add this to the result map, so we can group and tally the results for each
			// section within 0-F
			addToMap(resultMap, result2);
		}

		// Loop through Parse the map for the results and counts
		Set<Entry<String, Integer>> set = resultMap.entrySet();
		Iterator<Entry<String, Integer>> iter = set.iterator();

		System.out.println("Results for: " + mainValue);
		StringBuilder key = new StringBuilder();
		StringBuilder values = new StringBuilder();

		// Loop and build the strings
		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			key.append(entry.getKey());
			key.append("\t");
			values.append(entry.getValue());
			values.append("\t");
		}
		System.out.println(key);
		System.out.println(values);
	}

	/**
	 * Initializes the map with zeros, so we don't have to deal with nulls
	 * 
	 * @param resultMap
	 */
	public static void initializeMap(TreeMap<String, Integer> resultMap) {
		resultMap.put("0000", new Integer(0));
		resultMap.put("0001", new Integer(0));
		resultMap.put("0010", new Integer(0));
		resultMap.put("0011", new Integer(0));
		resultMap.put("0100", new Integer(0));
		resultMap.put("0101", new Integer(0));
		resultMap.put("0110", new Integer(0));
		resultMap.put("0111", new Integer(0));
		resultMap.put("1000", new Integer(0));
		resultMap.put("1001", new Integer(0));
		resultMap.put("1010", new Integer(0));
		resultMap.put("1011", new Integer(0));
		resultMap.put("1100", new Integer(0));
		resultMap.put("1101", new Integer(0));
		resultMap.put("1110", new Integer(0));
		resultMap.put("1111", new Integer(0));
	}

	/**
	 * Main argument
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DifferentialNdTable ndTable = new DifferentialNdTable();
		// Creating the Box using values 0-F
		List<String> ndList = new ArrayList<String>();

		ndList.add("0000");
		ndList.add("0001");
		ndList.add("0010");
		ndList.add("0011");
		ndList.add("0100");
		ndList.add("0101");
		ndList.add("0110");
		ndList.add("0111");
		ndList.add("1000");
		ndList.add("1001");
		ndList.add("1010");
		ndList.add("1011");
		ndList.add("1100");
		ndList.add("1101");
		ndList.add("1110");
		ndList.add("1111");

		TreeMap<String, String> sBoxMap = new TreeMap<String, String>();
		// Create the sBoxMap of the values. 0-F and give the values for each.
		// E 2 1 3 D 9 0 6 F 4 5 A 8 C 7 B
		// Default for the 4th Gen of the Stinson book in example 4.1 is
		// E 4 D 1 2 F B 8 3 A 6 C 5 9 0 7
		sBoxMap.put("0000", "1110");
		sBoxMap.put("0001", "0010");
		sBoxMap.put("0010", "0001");
		sBoxMap.put("0011", "0011");
		sBoxMap.put("0100", "1101");
		sBoxMap.put("0101", "1001");
		sBoxMap.put("0110", "0000");
		sBoxMap.put("0111", "0110");
		sBoxMap.put("1000", "1111");
		sBoxMap.put("1001", "0100");
		sBoxMap.put("1010", "0101");
		sBoxMap.put("1011", "1010");
		sBoxMap.put("1100", "1000");
		sBoxMap.put("1101", "1100");
		sBoxMap.put("1110", "0111");
		sBoxMap.put("1111", "1011");
		ndTable.setsBoxMap(sBoxMap);

		for (String ndString : ndList) {
			ndTable.parseAndPrintNdBox(ndString, ndList);
		}
	}

	/**
	 * Adds the results to the map, keeping the tally for each of the sections to
	 * print out of the box
	 * 
	 * @param resultMap
	 * @param result
	 */
	public static void addToMap(TreeMap<String, Integer> resultMap, String result) {
		Integer count = resultMap.get(result);
		resultMap.put(result, ++count);
	}

	public TreeMap<String, String> getsBoxMap() {
		return sBoxMap;
	}

	public void setsBoxMap(TreeMap<String, String> sBoxMap) {
		this.sBoxMap = sBoxMap;
	}

	public TreeMap<String, Integer> getResultMap() {
		return resultMap;
	}

	public void setResultMap(TreeMap<String, Integer> resultMap) {
		this.resultMap = resultMap;
	}

}
