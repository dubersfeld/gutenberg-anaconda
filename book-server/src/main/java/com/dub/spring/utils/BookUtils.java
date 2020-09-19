package com.dub.spring.utils;

import org.bson.types.ObjectId;

import com.dub.spring.domain.Book;
import com.dub.spring.domain.BookDocument;

public class BookUtils {

	public static Book documentToBook(BookDocument doc) {
		 	
		Book book = new Book(doc);
		book.setCategoryId(doc.getCategoryId().toString());
		
		return book;
	}
	
	public static BookDocument bookToDocument(Book book) {
		
		BookDocument doc = new BookDocument(book);
		doc.setCategoryId(new ObjectId(book.getCategoryId()));
		
		return doc;
	}
}
