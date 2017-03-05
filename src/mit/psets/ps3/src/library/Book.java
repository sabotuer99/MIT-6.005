package library;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    // rep
    private int year;
    private List<String> authors;
    private String title;
    private String stringified;
	private static Pattern nonSpace = Pattern.compile(".*[^\\s].*");
    
    
    // rep invariant
    //   year is nonnegative
    //   authors is not empty, each name has at least one non-space
    //   title contains at least one non-space character
    
    // abstraction function
	//   year is represented as a primative non-negative int value
	//   authors are stored as a list of strings, and title is a string
	
    // safety from rep exposure argument
	//   title and year are private, immutable fields
	//   the list of authors is mutable, however is defensively copied when created and observed
	//   individual author values are stored as String, which is immutable
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
    	assert authors != null;
    	assert title != null;
    	
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.year = year;
        
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(", ").append(year).append(", by ");
        for(String author : authors){
        	sb.append("'" + author + "'").append(", ");
        }
        sb.setLength(sb.length() - 2);//drop last comma from authors
        this.stringified = sb.toString();
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert year >= 0;
        assert nonSpace.matcher(title).matches();
        assert title != null;
        assert authors != null;
        assert authors.size() > 0;
        for(String author : authors){
        	assert author != null;
        	assert nonSpace.matcher(author).matches();
        }
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        return stringified;
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
     @Override
     public boolean equals(Object that) {
         if(!(that instanceof Book)) return false;
         Book o = (Book) that;
         return o.stringified.equals(this.stringified) && o.authors.size() == this.authors.size();
     }
     
     @Override
     public int hashCode() {
         return stringified.hashCode();
     }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
