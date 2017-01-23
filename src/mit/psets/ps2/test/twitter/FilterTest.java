package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */
    @Test
    public void testContainingMatchAnyString() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk", "blarg"));
                
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testContainingNoSubstrings() {
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talking in 30 minutes #hype", d2);
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet2), Arrays.asList("talk"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingCaseInsensitive() {
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("TALK"));
                
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanNoTweetsInSpan() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talking in 30 minutes #hype", Instant.MIN);
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet2), new Timespan(testStart, testEnd));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    @Test
    public void testInTimespanIgnoresOrder() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet2, tweet1), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet2));
    }
    
    @Test
    public void testInTimespanLengthZeroTimespan() {
        Instant testTime = Instant.parse("2016-02-17T09:00:00Z");
        Tweet tweet = new Tweet(2, "bbitdiddle", "rivest talking in 30 minutes #hype", testTime);
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet), new Timespan(testTime, testTime));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.contains(tweet));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet));
    }
    
    @Test
    public void testWrittenByAllTweetsSameAuthor() {
    	Tweet tweetA = new Tweet(1, "troy", "test 1", Instant.MIN);
    	Tweet tweetB = new Tweet(2, "troy", "test 2", Instant.MIN);
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweetA, tweetB), "troy");
        
        assertEquals("wrong size list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweetA));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweetB));
    }
    
    @Test
    public void testWrittenByCaseInsensitive() {
    	Tweet tweetA = new Tweet(1, "troy", "test 1", Instant.MIN);
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweetA), "Troy");
        
        assertEquals("wrong size list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweetA));
    }
    
    @Test
    public void testWrittenByAuthorNotFound() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "nobody");
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }
    
    @Test
    public void testWrittenByEmptyTweets() {
        List<Tweet> writtenBy = Filter.writtenBy(new ArrayList<Tweet>(), "nobody");
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }
    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
