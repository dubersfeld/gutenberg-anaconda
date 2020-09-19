package com.dub.spring.domain;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;


public class BookBase {

	@Id
	private String id;
	
	private String slug;
	private String sku;
	private String title;
	private String publisher;
	private List<String> authors;
	private String description;
	private int price;// price in cents
	//private ObjectId categoryId;
	private List<String> tags;
	
	public BookBase() {
		this.tags = new ArrayList<>();
		this.authors = new ArrayList<>();
	}
	
	public BookBase(BookBase that) {
		this.id = that.id;
		this.slug = that.slug;
		this.sku = that.sku;
		this.title = that.title;
		this.publisher = that.publisher;
		this.authors = that.authors;
		this.description = that.description;
		this.price = that.price;
		this.tags = that.tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}