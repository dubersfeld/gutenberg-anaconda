package com.dub.spring.domain;

import org.bson.types.ObjectId;

public class BookRating {
	
	private ObjectId bookId;
	private Double bookRating;
	

	public Double getBookRating() {
		return bookRating;
	}
	public void setBookRating(Double bookRating) {
		this.bookRating = bookRating;
	}
	public ObjectId getBookId() {
		return bookId;
	}
	public void setBookId(ObjectId bookId) {
		this.bookId = bookId;
	}

}