import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MatchUtil {

	
	public static String matchString(String input, String regex) {
		Pattern compile = Pattern.compile(regex);
		Matcher matcher = compile.matcher(input);
		matcher.matches();
		int groupCount = matcher.groupCount();
		return null;
	}
	
	public static void main(String[] args) {
//		Pattern compile = Pattern.compile("helloWorld/$/.class");
		System.out.println(Pattern.matches("helloWorld\\$\\.class", "helloWorld$.class"));
//		Matcher matcher = compile.matcher("helloWorld$.class");
//		System.out.println(matcher.matches());
//		int groupCount = matcher.groupCount();
//		for (int i = 0; i < groupCount; i++) {
//			System.out.println(matcher.group(i));
//			
//		}
	}
}
