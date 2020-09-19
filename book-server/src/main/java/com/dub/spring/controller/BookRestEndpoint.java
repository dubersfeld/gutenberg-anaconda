package com.dub.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dub.spring.domain.Book;
import com.dub.spring.exceptions.BookNotFoundException;
import com.dub.spring.services.BookService;

@RestController
public class BookRestEndpoint {
	
	@Autowired
	private BookService bookService;
	
	
	@RequestMapping(
			value = "/booksBoughtWith/{bookId}/outLimit/{outLimit}",
			method = RequestMethod.GET)
	public ResponseEntity<List<Book>> getBooksBoughtWith(
			@PathVariable("bookId") String bookId,
			@PathVariable("outLimit") int outLimit) {
		
		try {
			List<Book> books = bookService.getBooksBoughtWith(bookId, outLimit);
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	
		} catch (Exception e) {
			System.out.println("Exception caught " + e);
			return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	@RequestMapping("/books/{bookSlug}")
	public ResponseEntity<Book> getBookBySlug(@PathVariable String bookSlug) {
		
		try {
			Book book = bookService.getBookBySlug(bookSlug);
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		} catch (BookNotFoundException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/booksById/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") String bookId) {
			
		try {
			Book book = bookService.getBookById(bookId);
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		} catch (BookNotFoundException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(
			value = "/books/{categorySlug}/sort/{sortBy}",
			produces = MediaType.APPLICATION_JSON_VALUE)
			
	public ResponseEntity<List<Book>> allBooksByCategory(
				@PathVariable("categorySlug") String categorySlug,
				@PathVariable() String sortBy) {
		
		List<Book> books = bookService.allBooksByCategory(categorySlug, sortBy);
		
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}	
}
