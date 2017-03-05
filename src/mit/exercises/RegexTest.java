package mit.exercises;

import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args){
		Boolean isMatch = Pattern.matches("[a-g]+(_|\\^)?", "a^");
		System.out.println(isMatch);
	}
}
