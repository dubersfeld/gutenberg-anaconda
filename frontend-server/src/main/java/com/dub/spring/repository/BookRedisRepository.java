package com.dub.spring.repository;

import com.dub.spring.domain.Book;

public interface BookRedisRepository {
	
	Book findBookById(String bookId);	
	void saveBook(Book book);
	
	void deleteBook(String bookId);
}
