package library;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * Test suite for BigLibrary's stronger specs.
 */
public class BigLibraryTest {
    
    /* 
     * NOTE: use this file only for tests of BigLibrary.find()'s stronger spec.
     * Tests of all other Library operations should be in LibraryTest.java 
     */

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for BigLibrary.find() should go here.
     * Make sure you have partitions.
     */
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        // this is just an example test, you should delete it
        Library library = new BigLibrary();
        assertEquals(Collections.emptyList(), library.find("This Test Is Just An Example"));
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    private static final Book b1 = new Book("a", Arrays.asList("a"), 1990);
    
    @Test
    public void BigLibrary_testIsAvailable_newBook(){
    	Library library = new BigLibrary();
    	library.buy(b1);
    	BookCopy copy = library.availableCopies(b1).iterator().next();
    	assertTrue(library.isAvailable(copy));
    }
    
    @Test
    public void BigLibrary_testIsAvailable_lostBook(){
    	Library library = new BigLibrary();
    	library.buy(b1);
    	BookCopy copy = library.availableCopies(b1).iterator().next();
    	library.lose(copy);
    	assertFalse(library.isAvailable(copy));
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
