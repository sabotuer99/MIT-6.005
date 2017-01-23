package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.MIN;
    private static final Instant d4 = Instant.MAX;
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "troy", "@mit is the place to be", d3);
    private static final Tweet tweet4 = new Tweet(4, "troy", "@MIT rocks till the end of time", d4);
    private static final Tweet tweet5 = new Tweet(5, "troy", "@MIT @mit @berkely @CalTech @blahdeyBlah", d1);
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */
    
    @Test
    public void testGetTimespanEmptySet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet2));
        
        assertEquals("expected zero length", 0, timespan.getStart().compareTo(timespan.getEnd()));
    }
    
    @Test
    public void testGetTimespanMultipleSameTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet2));
        
        assertEquals("expected start", d2, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanOrderDoesNotMatter() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanMinTime() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3));
        
        assertEquals("expected start", d3, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanMaxTime() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4));
        
        assertEquals("expected start", d4, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertEquals(1, mentionedUsers.size());
        assertTrue("expected empty set", Pattern.matches("[Mm][Ii][Tt]", mentionedUsers.iterator().next()));
    }

    @Test
    public void testGetMentionedUsersTwoMentionsSameUsername() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        
        assertEquals(1, mentionedUsers.size());
        assertTrue("expected empty set", Pattern.matches("[Mm][Ii][Tt]", mentionedUsers.iterator().next()));
    }
    
    @Test
    public void testGetMentionedUsersFourUniqueMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        Set<String> expected = new HashSet<>(Arrays.asList("mit", "berkely", "caltech", "blahdeyblah"));
        
        assertEquals(4, mentionedUsers.size());
        assertTrue(expected.contains(mentionedUsers.iterator().next().toLowerCase()));
        assertTrue(expected.contains(mentionedUsers.iterator().next().toLowerCase()));
        assertTrue(expected.contains(mentionedUsers.iterator().next().toLowerCase()));
        assertTrue(expected.contains(mentionedUsers.iterator().next().toLowerCase()));
    }
    
    @Test
    public void testGetMentionedUsersEmailOmitted() {
    	final Tweet tweet6 = new Tweet(6, "troy", "email@email.com should not show up", d1);
    	
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersWithPunctuation() {
    	final Tweet tweet6 = new Tweet(6, "troy", "@mit.  should show up", d1);
    	
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertEquals(1, mentionedUsers.size());
        assertTrue(Pattern.matches("[Mm][Ii][Tt]", mentionedUsers.iterator().next()));
    }
    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
