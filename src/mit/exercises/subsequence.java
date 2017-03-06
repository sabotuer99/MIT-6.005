package mit.exercises;

public class subsequence {

	public static String subsequences(String word) {
	    partialSubsequence = "";
	    return subsequencesLouis(word);
	}

	private static String partialSubsequence = "";

	public static String subsequencesLouis(String word) {
	    
		System.out.println(word + " (" + partialSubsequence + ")");
		
		if (word.isEmpty()) {
	        // base case
	        return partialSubsequence;
	    } else {
	        // recursive step
	        String withoutFirstLetter = subsequencesLouis(word.substring(1));
	        partialSubsequence += word.charAt(0);
	        String withFirstLetter = subsequencesLouis(word.substring(1));
	        return withoutFirstLetter + "," + withFirstLetter;
	    }
	}
	
	public static void main(String[] args){
		subsequences("xy");
	}
	
}
