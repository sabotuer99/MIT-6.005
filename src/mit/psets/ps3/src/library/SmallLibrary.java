package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // TODO: safety from rep exposure argument
    
    public SmallLibrary() {
        inLibrary = new HashSet<>();
        checkedOut = new HashSet<>();
    }
    
    // assert the rep invariant
    private void checkRep() {
    	Set<BookCopy> intersection = new HashSet(inLibrary);
    	intersection.retainAll(checkedOut);
    	assert intersection.size() == 0;
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy newBook = new BookCopy(book);
        inLibrary.add(newBook);
        checkRep();
        return newBook;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        if(isAvailable(copy)){
        	inLibrary.remove(copy);
        	checkedOut.add(copy);
        }
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if(checkedOut.contains(copy)){
        	checkedOut.remove(copy);
        	inLibrary.add(copy);
        }
        checkRep();
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
    	return getCopiesFromSet(book, inLibrary);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
    	Set<BookCopy> copies = availableCopies(book);
    	copies.addAll(  getCopiesFromSet(book, checkedOut) );
    	return copies;
    }
    
    private Set<BookCopy> getCopiesFromSet(Book book, Set<BookCopy> set){
    	Set<BookCopy> copies = new HashSet<>();
    	for(BookCopy copy : set){
    		if(copy.getBook().equals(book)){
    			copies.add(copy);
    		}
    	}
    	return copies;
    }

    @Override
    public List<Book> find(String query) {
    	Set<BookCopy> collection = new HashSet<>(inLibrary);
    	collection.addAll(checkedOut);
    	
    	Set<Book> books = new HashSet<>();
    	
    	for(BookCopy copy : collection){
    		Book book = copy.getBook();
    		String title = book.getTitle();
    		List<String> authors = book.getAuthors();
    		if(title.equals(query) || authors.contains(query)){
    			books.add(book);
    		}
    	}
    	
    	List<Book> found = new ArrayList<>(books);
    	Collections.sort(found, new Comparator<Book>(){
			@Override
			public int compare(Book o1, Book o2) {
				String title1 = o1.getTitle();
				String title2 = o2.getTitle();
				String auth1 = Arrays.toString(o1.getAuthors().toArray());
				String auth2 = Arrays.toString(o2.getAuthors().toArray());
				
				
				if(title1.equals(title2) && auth1.equals(auth2)){
					return o2.getYear() - o1.getYear();
				}
				
				if(title1.equals(title2)){
					return auth1.compareTo(auth2);
				}
				return title1.compareTo(title2);
			}
    	});
    	
    	return found;
    }
    
    @Override
    public void lose(BookCopy copy) {
    	inLibrary.remove(copy);
    	checkedOut.remove(copy);
    	checkRep();
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
