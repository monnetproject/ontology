package eu.monnetproject.label.rdf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegExp Tools
 * 
 * @author Tobias Wunner
 *
 */
public class RegExpTools {


	/**
	 * Extracts a pattern from an input string
	 * 
	 */
	public static String extractPattern(String input,String regExpattern) {
		Pattern p = Pattern.compile(regExpattern);
		Matcher m = p.matcher(input);
		while (m.find()) {
			for (int i = 1; i <= m.groupCount();) {
				return m.group(i);
			}
		}
		return null;
	}

	public static boolean containsPattern(String input,String regExpattern) {
		Pattern p = Pattern.compile(regExpattern);
		Matcher m = p.matcher(input);
		if(m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {

		// define input string and regexp
		//String input = "http://www.the-wine-domain.com/Region1/City/Wine";
		String input = "http://www.w3.org/TR/2003/CR-owl-guide-20030818/wine";
		String pattern = ".*\\/(.*)$";

		// process 
		String result = RegExpTools.extractPattern(input,pattern);

		// print results
		System.out.println("  input: "+input);
		System.out.println("pattern: "+pattern);
		System.out.println(" result: "+result);
	}

}
