package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dub.spring.domain.Book;
import com.dub.spring.exceptions.BookNotFoundException;
import com.dub.spring.services.BookService;

@SpringBootTest
public class BookServiceTest {
	
	@Autowired
	private BookService bookService;
	
	
	@Test
	void testAllCategories() {
	     List<Book> books = bookService.allBooksByCategory("computer-science", "title");
	 
	     assertTrue(books.size() == 5);
	}
	
	
	@Test
	void testBySlug() {
	     Book book = bookService.getBookBySlug("mess-harefaq-1542");    	     	    
	     assertTrue("Messaging with HareFAQ".equals(book.getTitle()));
	}
	
	
	@Test
	void testBySlugNotFound() {    	    
	     assertThrows(BookNotFoundException.class, () -> {
	    	 bookService.getBookBySlug("mess-harefaq-666");
	     });
	}
	
	
	@Test
	void testById() {
	     Book book = bookService.getBookById("5a28f2b0acc04f7f2e97409f");    	     	    
	     assertTrue("Messaging with HareFAQ".equals(book.getTitle()));
	}
	
	@Test
	void testByIdNotFound() {
		assertThrows(BookNotFoundException.class, () -> {
	    	 bookService.getBookById("5a28f2b0acc04f7f2e976666");
	     });		
	}
	
	// more advanced methods
	@Test
	void testBoughtWith() {
		
		List<Book> books = bookService.getBooksBoughtWith("5a28f2b0acc04f7f2e9740a0", 10);
		
		System.err.println(books.size());
		System.err.println(books.get(0).getSlug());
		
		assertTrue(books.size() == 1 && "marbront-1902".equals(books.get(0).getSlug()));	
	}
		
}
