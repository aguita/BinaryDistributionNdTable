import java.math.BigInteger;
import java.util.Iterator;
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

	/**
	 * X-ORS two strings.
	 * 
	 * @param a the first string
	 * @param b the second string
	 * @param n the length of the string
	 * @return the XOR'd value
	 */
	public static String xoring(String a, String b, int n) {
		String ans = "";
		for (int i = 0; i < n; i++) {
			// If the Character matches
			if (a.charAt(i) == b.charAt(i))
				ans += "0";
			else
				ans += "1";
		}
		return ans;
	}

	/**
	 * Parse the values and print the table.
	 * 
	 * @param mainValue the value to use -- it'll be the binary value of the HEX
	 *                  characters from 0-F
	 */
	public static void parseAndPrintNdBox(String mainValue) {

		// Setting the 0-F variables
		String zero = "0000";
		String one = "0001";
		String two = "0010";
		String three = "0011";
		String four = "0100";
		String five = "0101";
		String six = "0110";
		String seven = "0111";
		String eight = "1000";
		String nine = "1001";
		String A = "1010";
		String B = "1011";
		String C = "1100";
		String D = "1101";
		String E = "1110";
		String F = "1111";

		TreeMap<String, String> sBoxMap = new TreeMap<String, String>();
		// Create the sBoxMap of the values. 0-F and give the values for each.
		// E 2 1 3 D 9 0 6 F 4 5 A 8 C 7 B
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

		int n = mainValue.length();
		String x0 = xoring(mainValue, zero, n);
		String x1 = xoring(mainValue, one, n);
		String x2 = xoring(mainValue, two, n);
		String x3 = xoring(mainValue, three, n);
		String x4 = xoring(mainValue, four, n);
		String x5 = xoring(mainValue, five, n);
		String x6 = xoring(mainValue, six, n);
		String x7 = xoring(mainValue, seven, n);
		String x8 = xoring(mainValue, eight, n);
		String x9 = xoring(mainValue, nine, n);
		String xa = xoring(mainValue, A, n);
		String xb = xoring(mainValue, B, n);
		String xc = xoring(mainValue, C, n);
		String xd = xoring(mainValue, D, n);
		String xe = xoring(mainValue, E, n);
		String xf = xoring(mainValue, F, n);

		TreeMap<String, Integer> resultMap = new TreeMap<String, Integer>();
		initializeMap(resultMap);

		// XOR each of the values of HEX between 0-F
		String result0 = xoring(sBoxMap.get(zero), sBoxMap.get(x0), n);
		String result1 = xoring(sBoxMap.get(one), sBoxMap.get(x1), n);
		String result2 = xoring(sBoxMap.get(two), sBoxMap.get(x2), n);
		String result3 = xoring(sBoxMap.get(three), sBoxMap.get(x3), n);
		String result4 = xoring(sBoxMap.get(four), sBoxMap.get(x4), n);
		String result5 = xoring(sBoxMap.get(five), sBoxMap.get(x5), n);
		String result6 = xoring(sBoxMap.get(six), sBoxMap.get(x6), n);
		String result7 = xoring(sBoxMap.get(seven), sBoxMap.get(x7), n);
		String result8 = xoring(sBoxMap.get(eight), sBoxMap.get(x8), n);
		String result9 = xoring(sBoxMap.get(nine), sBoxMap.get(x9), n);
		String resulta = xoring(sBoxMap.get(A), sBoxMap.get(xa), n);
		String resultb = xoring(sBoxMap.get(B), sBoxMap.get(xb), n);
		String resultc = xoring(sBoxMap.get(C), sBoxMap.get(xc), n);
		String resultd = xoring(sBoxMap.get(D), sBoxMap.get(xd), n);
		String resulte = xoring(sBoxMap.get(E), sBoxMap.get(xe), n);
		String resultf = xoring(sBoxMap.get(F), sBoxMap.get(xf), n);

		// add the results of each XOR to the result Map, tallying up the results
		addToMap(resultMap, result0);
		addToMap(resultMap, result1);
		addToMap(resultMap, result2);
		addToMap(resultMap, result3);
		addToMap(resultMap, result4);
		addToMap(resultMap, result5);
		addToMap(resultMap, result6);
		addToMap(resultMap, result7);
		addToMap(resultMap, result8);
		addToMap(resultMap, result9);
		addToMap(resultMap, resulta);
		addToMap(resultMap, resultb);
		addToMap(resultMap, resultc);
		addToMap(resultMap, resultd);
		addToMap(resultMap, resulte);
		addToMap(resultMap, resultf);

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
		// Creating the Box using values 0-F

		String zero = "0000";
		String one = "0001";
		String two = "0010";
		String three = "0011";
		String four = "0100";
		String five = "0101";
		String six = "0110";
		String seven = "0111";
		String eight = "1000";
		String nine = "1001";
		String A = "1010";
		String B = "1011";
		String C = "1100";
		String D = "1101";
		String E = "1110";
		String F = "1111";

		parseAndPrintNdBox(zero);
		parseAndPrintNdBox(one);
		parseAndPrintNdBox(two);
		parseAndPrintNdBox(three);
		parseAndPrintNdBox(four);
		parseAndPrintNdBox(five);
		parseAndPrintNdBox(six);
		parseAndPrintNdBox(seven);
		parseAndPrintNdBox(eight);
		parseAndPrintNdBox(nine);
		parseAndPrintNdBox(A);
		parseAndPrintNdBox(B);
		parseAndPrintNdBox(C);
		parseAndPrintNdBox(D);
		parseAndPrintNdBox(E);
		parseAndPrintNdBox(F);
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

}
