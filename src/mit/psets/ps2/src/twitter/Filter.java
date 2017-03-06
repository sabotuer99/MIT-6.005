package twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> result = new ArrayList<>();
        for(Tweet tweet : tweets){
        	if(username.toLowerCase().equals(tweet.getAuthor().toLowerCase())){
        		result.add(tweet);
        	}
        }
        return result;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
    	List<Tweet> result = new ArrayList<>();
        for(Tweet tweet : tweets){
        	if(timespan.getStart().compareTo(tweet.getTimestamp()) <= 0 &&
        	   timespan.getEnd().compareTo(tweet.getTimestamp()) >= 0){
        		result.add(tweet);
        	}
        }
        return result;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Pattern> patterns = new ArrayList<>();
        for(String word : words){
        	patterns.add(Pattern.compile(".*(?<=\\s|^)" + word + "(?=\\s|$).*",Pattern.CASE_INSENSITIVE));
        }
        
        List<Tweet> result = new ArrayList<>();
        for(Tweet tweet : tweets){
        	boolean found = false;
        	for(int i = 0; i < patterns.size() && !found; i++){
        		found = patterns.get(i).matcher(tweet.getText()).matches();
        	}
        	if(found){
        		result.add(tweet);
        	}
        }
        
        return result;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}