package com.dub.spring.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class CategoryBase {
	
	@Id
	private String id;
	
	private String slug;
	
	private String name;
	
	private String description;
		
	private List<Map<String, Object>> ancestors;
	
	public CategoryBase() {
		this.ancestors = new ArrayList<>();
	}
	
	public CategoryBase(CategoryBase that) {
		this.ancestors = that.ancestors;
		this.description = that.description;
		this.id = that.id;
		this.slug = that.slug;
		this.name = that.name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Map<String, Object>> getAncestors() {
		return ancestors;
	}

	public void setAncestors(List<Map<String, Object>> ancestors) {
		this.ancestors = ancestors;
	}

}