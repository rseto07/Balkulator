package classes;


public class SigFig {
	// Reasons for methods to take string parameters
	// - Input is from textfields (ie. Strings)
	// - Need to track trailing zeros in sig fig, therefore doubles do not work since 1.000 cuts to 1.0
	
	// Next Steps:
	// - Implement support for sci not'n input
	// - Implement sig fig when answer returned in sci not'n
	// - Use rng to test methods once done (for extreme edge cases)
	// - Support case when difference is zero?
	// - Implement check if difference is negative
	
	public static String add(String a, String b) {
		String decA = getDecimal(a);
		String decB = getDecimal(b);
		int sf = 0;
		int front = (int)Double.parseDouble(a) + (int)Double.parseDouble(b);
		front = ("" + front).length();
		sf = front + Math.min(decA.length(), decB.length());
		return setSigFig(Double.parseDouble(a) + Double.parseDouble(b), sf);
	}
	
	public static String subtract(String a, String b) {
		String decA = getDecimal(a);
		String decB = getDecimal(b);
		int sf = 0;
		if((int)Double.parseDouble(a) == (int)Double.parseDouble(b)) {
			sf = Math.min(decA.length(), decB.length());
		} else {
			int front = (int)Double.parseDouble(a) - (int)Double.parseDouble(b);
			front = ("" + front).length();
			sf = front + Math.min(decA.length(), decB.length());
		}
		return setSigFig(Double.parseDouble(a) - Double.parseDouble(b), sf);
	}
	
	private static String getDecimal(String x) {
		boolean afterDecimal = false;
		String decimal = ""; // Avoids string containing null
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
	public static String multiply(String a, String b) {
		int sf1 = determineSigFig(a);
		int sf2 = determineSigFig(b);
		if(sf1 == -1 || sf2 == -1) {
			return "Invalid entry.";
		} else {
			return setSigFig(Double.parseDouble(a) * Double.parseDouble(b), sf1<sf2?sf1:sf2);
		}
	}
	
	public static String divide(String a, String b) {
		int sf1 = determineSigFig(a);
		int sf2 = determineSigFig(b);
		if(sf1 == -1 || sf2 == -1) {
			return "Invalid entry.";
		} else {
			return setSigFig(Double.parseDouble(a) / Double.parseDouble(b), sf1<sf2?sf1:sf2);
		}
	}
	
	private static int determineSigFig(String x) {
		try {
			Double.parseDouble(x);
		} catch (NumberFormatException e) {
			return -1;
		} finally {
			if(Double.parseDouble(x) < 0) {
				return -1;
			}
		}
		int leadingZeros = 0;
		for(int i = 0; i < x.length(); i++) {
			if(x.charAt(i) == '.') {
				x = x.substring(0, i) + x.substring(i+1);
				break;
			}
		}
		for(int i = 0; i < x.length(); i++) {
			leadingZeros++;
			if(x.charAt(i) != '0') {
				leadingZeros--;
				break;
			}
		}
		return x.length()-leadingZeros;
	}
	
	// Note: g includes numbers before and after decimal, f only after
	private static String setSigFig(double x, int sf) {
		return String.format("%." + sf + "g", x);
	}
}
