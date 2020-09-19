package com.dub.spring.services;


import java.util.List;

import com.dub.spring.domain.Book;

public interface BookService {
	
	List<Book> allBooksByCategory(String categorySlug, String sortBy);// category slug, not name
	
	Book getBookBySlug(String slug);
	
	Book getBookById(String bookId);
		
	// more advanced methods
	List<Book> getBooksBoughtWith(String bookId, int limit);
}