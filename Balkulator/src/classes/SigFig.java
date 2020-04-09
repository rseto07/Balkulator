package classes;


public class SigFig {
	/*
	 * Description:
	 * - This class performs simple arithmetic in accordance with the rules of significant figures (sig fig).
	 * - This is a static class, meant to be used only for its arithmetic operations.
	 * - All public methods take Strings as parameteres.
	 *   - There are two reasons for this:
	 *     - Input will be obtained through textfields, therefore input will be Strings.
	 *     - If input is converted into a double, trailing zeros are not kept. Sig fig requires all significant
	 *    	 digits be kept, therefore input has to be kept in String form to retain trailing zeros. 
	 */
	
//	 Next Steps:
//	 - Implement support for sci not'n input
//	 - Implement sig fig when sum/difference/product/quotient returned in sci not'n
//	 - Use rng to test methods once done (for extreme edge cases)
//	 - Support case when difference is zero?
//	 - subtract("0.05", "0.03") returns "0.020"
	
	/*
	 * Description:
	 * - a{n} hereby denotes the numerical value of a
	 * - Returns a{n} + b{n} in accordance with sig fig rules
	 * 
	 * Precondition:
	 * - a{n}, b{n} > 0
	 * - a and b must both be able to be parsed by Double.parseDouble()
	 */
	public static String add(String a, String b) {
		// See getDecimal() below
		String decA = getDecimal(a);
		String decB = getDecimal(b);
		
		int sf = 0; // sf --> significant figures/sig fig
		
		// Determines the length of the integer value of the sum
		int front = (int)Double.parseDouble(a) + (int)Double.parseDouble(b);
		front = ("" + front).length();
		
		// Determines the total amount of sig figs of the sum
		sf = front + Math.min(decA.length(), decB.length());
		
		// See setSigFig() at the end of the class
		return setSigFig(Double.parseDouble(a) + Double.parseDouble(b), sf);
	}
	
	/*
	 * Description:
	 * - Returns a{n} - b{n} in accordance with sig fig rules
	 * 
	 * Precondition: 
	 * - a{n} > b{n} > 0
	 * - a and b must both be able to be parsed by Double.parseDouble()
	 */
	public static String subtract(String a, String b) {
		// See getDecimal() below
		String decA = getDecimal(a);
		String decB = getDecimal(b);
		
		int sf = 0;
		/*
		 * The logic of determining the sig figs of the difference is the same for the sum, except
		 * that the String.format() method does not count leading zeros. In other words, if the 
		 * difference of the integer values of a{n} and b{n} is zero, then front = 0. Otherwise,
		 * continue as normal. 
		 * 
		 * NOTE: subtract("0.05", "0.03") returns "0.020"
		 */
		if((int)Double.parseDouble(a) == (int)Double.parseDouble(b)) {
			sf = Math.min(decA.length(), decB.length());
		} else {
			int front = (int)Double.parseDouble(a) - (int)Double.parseDouble(b);
			front = ("" + front).length();
			sf = front + Math.min(decA.length(), decB.length());
		}
		// See setSigFig() at the end of the class
		return setSigFig(Double.parseDouble(a) - Double.parseDouble(b), sf);
	}
	
	/*
	 * Decription:
	 * - Returns a String containing the decimal portion of a floating point
	 */
	private static String getDecimal(String x) {
		boolean afterDecimal = false;
		String decimal = "";
		// Adds the characters of x that occur after the decimal point to String decimal
		for(int i = 0; i < x.length(); i++) {
			if(x.charAt(i) == '.') {
				afterDecimal = true;
				i++;
			}
			if(afterDecimal) {
				decimal += x.charAt(i);
			} 
		}
		return decimal;
	}
	
	/*
	 * Description:
	 * - Returns a{n} * b{n} in accordance with sig fig rules
	 * 
	 * Precondition: 
	 * - a{n}, b{n} > 0
	 */
	public static String multiply(String a, String b) {
		// See determineSigFig() below
		int sf1 = determineSigFig(a);
		int sf2 = determineSigFig(b);
		
		if(sf1 == -1 || sf2 == -1) {
			return "Invalid entry.";
		} else {
			// Returns the product, set to the lower of the sig figs of the two operands
			// See setSigFig() at the end of the class
			return setSigFig(Double.parseDouble(a) * Double.parseDouble(b), sf1<sf2?sf1:sf2);
		}
	}
	
	/*
	 * Description:
	 * - Returns a{n} / b{n} in accordance with sig fig rules
	 * 
	 * Precondition:
	 * - a{n}, b{n} > 0
	 */
	public static String divide(String a, String b) {
		// See determineSigFig() below
		int sf1 = determineSigFig(a);
		int sf2 = determineSigFig(b);
		if(sf1 == -1 || sf2 == -1) {
			return "Invalid entry.";
		} else {
			// Returns the quotient, set to the lower of the sig figs of the two operands
			// See setSigFig() at the end of the class
			return setSigFig(Double.parseDouble(a) / Double.parseDouble(b), sf1<sf2?sf1:sf2);
		}
	}
	
	
	private static int determineSigFig(String x) {
		// Checks if x is a valid double
		try {
			Double.parseDouble(x);
		} catch (NumberFormatException e) {
			return -1;
		} finally {
			if(Double.parseDouble(x) < 0) {
				return -1;
			}
		}
		// Removes the '.' from the string
		for(int i = 0; i < x.length(); i++) {
			if(x.charAt(i) == '.') {
				x = x.substring(0, i) + x.substring(i+1);
				break;
			}
		}
		
		// Counts the number of leading zeros
		int leadingZeros = 0;
		for(int i = 0; i < x.length(); i++) {
			leadingZeros++;
			if(x.charAt(i) != '0') {
				leadingZeros--;
				break;
			}
		}
		return x.length()-leadingZeros;
	}
	
	/*
	 * Description:
	 * - Returns x in the correct number of sig figs sf
	 * 
	 * Precondition:
	 * - sf > 0
	 */
	private static String setSigFig(double x, int sf) {
		return String.format("%." + sf + "g", x);
	}
}
