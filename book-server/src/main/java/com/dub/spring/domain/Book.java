package com.dub.spring.domain;

public class Book extends BookBase {

	private String categoryId;
	
	public Book() {
		super(new BookBase());

	}
	
	public Book(BookBase that) {
		super(that);
	}

	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}