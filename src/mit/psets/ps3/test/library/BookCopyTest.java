package library;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * 
     * Partitions:
     *     book: null (fail), valid
     *     condition: good, damaged
     *     equals: reference equality

     */
    
	private static Book book1 = new Book("Test Book", Arrays.asList("John. Q. Tester"), 1990);
    
    @Test
    public void BookCopy_sanityCheck() {
        BookCopy copy = new BookCopy(book1);
        assertEquals(book1, copy.getBook());
        assertEquals(BookCopy.Condition.GOOD, copy.getCondition());
    }
    
    @Test(expected=AssertionError.class)
    public void BookCopy_ctor_nullBook_fails() {
    	new BookCopy(null);
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
