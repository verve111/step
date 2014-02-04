package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {
	
	private static Pattern parantheses = Pattern.compile("\\((.+)\\)");
	private static Pattern doubleParantheses = Pattern.compile("\\(.+\\((.+)\\).*\\)");
	private static Pattern twoParameters = Pattern.compile("\\w+\\s*\\((.+),(.+)\\)");
	private static Pattern threeParameters = Pattern.compile("\\w+\\s*\\((.+),(.+),(.+)\\)");
	private static Pattern fourParameters = Pattern.compile("\\w+\\s*\\((.+),(.+),(.+),(.+)\\)");
	private static Pattern fiveParameters = Pattern.compile("\\w+\\s*\\((.+),(.+),(.+),(.+),(.+)\\)");
	
	public static String getValueBetweenParentheses(String s) {
		Matcher m = parantheses.matcher(s);
		while (m.find()) {
			return m.group(1).trim();
		}
		return null;
	}
	
	// CLOSED_SHELL ( 'NONE', ( #22, #19, #24, #23, #21, #20 ) ) ->  #22, #19, #24, #23, #21, #20
	public static String getValueBetweenDoubleParentheses(String s) {
		Matcher m = doubleParantheses.matcher(s);
		while (m.find()) {
			return m.group(1).trim();
		}
		return null;
	}
	
	// Example: ADVANCED_FACE ( 'NONE',  #2 , #130, .F. ), 2 ->  #2
	// without parantheses inside
	public static String getParameter(String s, int paramNum, int totalParamsCount) {
		Matcher m = totalParamsCount == 2 ? twoParameters.matcher(s) : totalParamsCount == 3 ? threeParameters.matcher(s)
				: totalParamsCount == 4 ? fourParameters.matcher(s) : totalParamsCount == 5 ? fiveParameters.matcher(s) : null;
		while (m.find()) {
			return m.group(paramNum).trim();
		}
		return null;
	}


}
