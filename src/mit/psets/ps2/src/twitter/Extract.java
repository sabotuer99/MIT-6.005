package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant min = Instant.MAX;
        Instant max = Instant.MIN;
        
        Timespan minspan = new Timespan(min,min);
        
        if(tweets == null || tweets.size() == 0){
        	return minspan;
        }
        
        for(Tweet tweet : tweets){
        	min = min.compareTo(tweet.getTimestamp()) > 0 ? tweet.getTimestamp() : min ;
        	max = max.compareTo(tweet.getTimestamp()) < 0 ? tweet.getTimestamp() : max ;
        }
        
        minspan = new Timespan(min, max);
        return minspan;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        /* Tweet.getAuthor() spec...
         * @return Twitter username who wrote this tweet.
         *         A Twitter username is a nonempty sequence of letters (A-Z or
         *         a-z), digits, underscore ("_"), or hyphen ("-").
         *         Twitter usernames are case-insensitive, so "jbieber" and "JBieBer"
         *         are equivalent.
         */
    	
    	String regex = "(?<![A-Za-z0-9_-])@[A-Za-z0-9_-]+(?![A-Za-z0-9_-])";
    	Pattern p = Pattern.compile(regex);
    	Set<String> usernames = new HashSet<>();
    	for(Tweet tweet : tweets){
    		Matcher m = p.matcher(tweet.getText());
    		while(m.find()){
    			usernames.add(m.group().toLowerCase().substring(1));
    		}
    	}
    	
    	return usernames;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
