package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    // rep
	private Map<Book, Set<BookCopy>> collection;
	private Map<BookCopy, Boolean> availability;
	private Map<String, Set<Book>> authorIndex;
	private Map<String, Set<Book>> titleIndex;
    
    // rep invariant
	//
	//
	//
    // abstraction function
	//    collections maps from a book to a map.  This inner map ties a copy of a book to a 
	//    boolean indicating if it is available.
	//    authorIndex and titleIndex map from a author or title to a set of books
	//
	//
    // safety from rep exposure argument
	//    String and Book are immutable.  None of the mutable maps are returned or assigned.
	//    BookCopy is mutable but uses the default Object hash and equals functions (so won't blow
	//    up the hashing function of the collection if it changes condition)
    
    public BigLibrary() {
    	collection = new HashMap<>();
    	authorIndex = new HashMap<>();
    	titleIndex = new HashMap<>();
    	availability = new HashMap<>();
    }
    
    // assert the rep invariant
    private void checkRep() {}

    @Override
    public BookCopy buy(Book book) {
    	BookCopy newBook = new BookCopy(book);
    	Set<BookCopy> copies = getOrNew(collection, book);
    	copies.add(newBook);
    	collection.put(book, copies);
    	for(String author : book.getAuthors()){
    		Set<Book> byAuth = getOrNew(authorIndex, author);
    		byAuth.add(book);
    		authorIndex.put(author, byAuth);
    	}
    	Set<Book> byTitle = getOrNew(titleIndex, book.getTitle());
    	byTitle.add(book);
    	titleIndex.put(book.getTitle(), byTitle);
    	availability.put(newBook, true);
    	return newBook;
    }
    
    private <K, T> Set<T> getOrNew(Map<K, Set<T>> map, K key){
    	return map.containsKey(key) ? map.get(key) : new HashSet<T>();
    }
    
    @Override
    public void checkout(BookCopy copy) {
        if(availability.containsKey(copy)){
        	availability.put(copy, false);
        }
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if(availability.containsKey(copy)){
        	availability.put(copy, true);
        }
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
    	Set<BookCopy> copies = collection.get(book);
    	if(copies == null)
    		return new HashSet<>();
    	else 
    		return new HashSet<>(copies);
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> all = allCopies(book);
        Set<BookCopy> available = new HashSet<>();
        for(BookCopy copy : all){
        	if(isAvailable(copy)){
        		available.add(copy);
        	}
        }
        return available;
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        Boolean available = availability.get(copy);
    	
    	return available != null && available;
    }
    
    @Override
    public List<Book> find(String query) {
    	Set<Book> books = new HashSet<>();
    	
    	if(authorIndex.containsKey(query)){
    		books.addAll(authorIndex.get(query));
    	}
    	
    	if(titleIndex.containsKey(query)){
    		books.addAll(titleIndex.get(query));
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
    	
    	//no longer available
        availability.remove(copy);

        //remove from collection
        Book book = copy.getBook();
        Set<BookCopy> copies = collection.get(book);
        copies.remove(copy);
        
        //if this is last copy, remove book from all indicies and collection
        if(copies.size() == 0){
        	collection.remove(book);
        	
        	for(String author : book.getAuthors()){
        		removeFromIndex(book, author, authorIndex);
        	}
        	removeFromIndex(book, book.getTitle(), titleIndex);
        	
        } else {
        	collection.put(book, copies);
        }
    }

	private void removeFromIndex(Book book, String key, Map<String, Set<Book>> index) {
		Set<Book> books = index.get(key);
		books.remove(book);
		if(books.size() == 0){
			index.remove(key);
		} else {
			index.put(key, books);
		}
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
