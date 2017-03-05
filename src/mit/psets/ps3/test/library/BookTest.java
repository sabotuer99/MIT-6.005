package library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * Partitions:
     *     title: short, long, non-alpha, empty (fail), null (fail), all space (fail)
     *     authors: one author, several authors, empty (fail), null (fail)
     *        author: short, long, empty (fail), null (fail), all space (fail)
     *     year: negative (fail), zero, modern
     *     equals: author order and capitalization, delimiters in author name
     * 
     * Special Cases:
     *     check equality of books with author names containing delimiter (comma, space, semi-colon) i.e.
     *            Book 1:   {"fred, george", "anne"}
     *            Book 2:   {"fred", "george", "anne"}
     * 
     */
    
	
	/**
	 *  TEST FAILURE CASES
	 */
    @Test(expected=AssertionError.class)
    public void Book_ctor_emptyTitle_fails() {
    	new Book("", Arrays.asList("a"), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_nullTitle_fails() {
    	new Book(null, Arrays.asList("a"), 0);
    }
	
    @Test(expected=AssertionError.class)
    public void Book_ctor_allSpaceTitle_fails() {
    	new Book("        ", Arrays.asList("a"), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_emptyAuthorList_fails() {
    	new Book("valid", new ArrayList<>(), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_nullAuthorList_fails() {
    	new Book("valid", null, 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_authorNameEmpty_fails() {
    	new Book("valid", Arrays.asList(""), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_authorNameNull_fails() {
    	new Book("valid", Arrays.asList("valid", null), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_authorNameAllSpaces_fails() {
    	new Book("valid", Arrays.asList("valid", "       "), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void Book_ctor_negativeYear_fails() {
    	new Book("valid", Arrays.asList("valid"), -999);
    }
    
    
    
    
    /**
     *   NON-FAILURE TESTS
     */
	@Test
    public void Book_equals_commonDelimitersInAuthors_notEqual(){
		String[] delimiters = {" ", ",", ", ", ",  ", ";", "; ", ";  ", "_", " _ ",".", ". ", ".  "};
		
		for(String del : delimiters){
			Book book1 = new Book("a", Arrays.asList("a" + del + "b", "c"), 0);
			Book book2 = new Book("a", Arrays.asList("a", "b" + del + "c"), 0);
	        assertFalse("Delimiter '" + del + "' caused a problem with equals", book1.equals(book2));
		}
	}
    
	@Test
    public void Book_authors_noRepExposure(){	
		List<String> authors = Arrays.asList("b", "a");
		Book book1 = new Book("a", authors , 0);		
        assertFalse(authors == book1.getAuthors());
	}
	
	@Test
    public void Book_equals_orderAuthors_notEqual(){	
		Book book1 = new Book("a", Arrays.asList("b", "a"), 0);
		Book book2 = new Book("a", Arrays.asList("a", "b"), 0);
        assertFalse(book1.equals(book2));
	}
	
	@Test
    public void Book_equals_authorsCaseSensitive_notEqual(){	
		Book book1 = new Book("a", Arrays.asList("a", "B"), 0);
		Book book2 = new Book("a", Arrays.asList("A", "b"), 0);
        assertFalse(book1.equals(book2));
	}
	
	@Test
    public void Book_equals_titleCaseSensitive_notEqual(){	
		Book book1 = new Book("A", Arrays.asList("a", "b"), 0);
		Book book2 = new Book("a", Arrays.asList("a", "b"), 0);
        assertFalse(book1.equals(book2));
	}
    
	@Test
    public void Book_ctor_shortValidInputs(){
		Book book = new Book("a", Arrays.asList("a"), 0);
        assertEquals("a", book.getTitle());
        assertEquals(1, book.getAuthors().size());
        assertEquals(0, book.getYear());
	}
	
	@Test
    public void Book_ctor_NonAlphaTitle(){
		Book book = new Book("1984", Arrays.asList("George Orwell"), 1949);
        assertEquals("1984", book.getTitle());
        assertEquals(1, book.getAuthors().size());
        assertEquals(1949, book.getYear());
	}
	
	@Test
    public void Book_ctor_longValidInputs(){
		StringBuilder sb_title = new StringBuilder();
		List<String> authors = new ArrayList<>();
		
		for(int i = 0; i < 100; i++){
			sb_title.append(i).append(": ").append("This is a ridiculously long title for the purposes of testing this book class ");
			authors.add("Adolph Blaine Charles David Earl Frederick Gerald Hubert Irvin John Kenneth Lloyd Martin Nero Oliver Paul Quincy Randolph Sherman Thomas Uncas Victor William Xerxes Yancy Wolfeschlegelsteinhausenbergerdorff, Senior ");
		}
		
		Book book = new Book(sb_title.toString(), 
				authors, Integer.MAX_VALUE);
        assertEquals(sb_title.toString(), book.getTitle());
        assertEquals(100, book.getAuthors().size());
        assertEquals(Integer.MAX_VALUE, book.getYear());
	}
	
	
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("This Test Is Just An Example", book.getTitle());
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
