package com.dub.spring.domain;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class BookDocument extends BookBase {

	private ObjectId categoryId;
		
	public BookDocument() {
		super(new BookBase());
	}
	
	public BookDocument(BookBase that) {
		super(that);
	}

	public ObjectId getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(ObjectId categoryId) {
		this.categoryId = categoryId;
	}
}