package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<Tweet>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsCompleteGraph() {
    	Tweet tweet1 = new Tweet(1, "alyssa", "@bbitdiddle is the man!", d1);
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa quick stalking me....", d2);
    	
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));
        
        assertEquals(2, followsGraph.size());
    }
    
    @Test
    public void testGuessFollowsCaseInsensitive() {
    	Tweet tweet1 = new Tweet(1, "alyssa", "@BBitdiddle is the man!", d1);
    	Tweet tweet2 = new Tweet(1, "alyssa", "@bbitdiddle is the man!", d1);
    	
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));
        
        for(Map.Entry<String, Set<String>> entry : followsGraph.entrySet()){
        	if(entry.getKey().toLowerCase().equals("alyssa")){
        		assertEquals(1, entry.getValue().size());
        	}
        }
    }
    
    @Test
    public void testGuessFollowsTestRelationshipDirection() {
    	Tweet tweet1 = new Tweet(1, "alyssa", "@bit is the man!", d1);
    	
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        
        for(Map.Entry<String, Set<String>> entry : followsGraph.entrySet()){
        	if(entry.getKey().toLowerCase().equals("alyssa")){
        		assertEquals(1, entry.getValue().size());
        		assertEquals("bit", entry.getValue().iterator().next().toLowerCase());
        	}
        }
    }
    
    @Test
    public void testGuessFollowsNoSelfFollow() {
    	Tweet tweet1 = new Tweet(1, "alyssa", "@alyssa is awesome!", d1);
    	
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        
        for(Map.Entry<String, Set<String>> entry : followsGraph.entrySet()){
        	if(entry.getKey().toLowerCase().equals("alyssa")){
        		assertEquals(0, entry.getValue().size());
        	}
        }
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersCaseInsensitive() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        followsGraph.put("troy", new HashSet<String>(Arrays.asList("Sara", "Joe")));
        followsGraph.put("sara", new HashSet<String>(Arrays.asList("Joe")));
        followsGraph.put("joe", new HashSet<String>(Arrays.asList("Troy")));
        
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals(3, influencers.size());
        assertTrue(Pattern.matches("[Jj][Oo][Ee]", influencers.get(0)));
    }
    
    @Test
    public void testInfluencersEmptyNodes() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        followsGraph.put("troy", new HashSet<String>(Arrays.asList("Sara", "Joe")));
        followsGraph.put("sara", new HashSet<String>(Arrays.asList("Joe")));
        followsGraph.put("joe", new HashSet<String>(Arrays.asList("Troy")));
        followsGraph.put("empty", new HashSet<String>());
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals(4, influencers.size());
        assertTrue(Pattern.matches("[Jj][Oo][Ee]", influencers.get(0)));
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
