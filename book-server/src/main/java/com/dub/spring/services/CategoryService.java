package com.dub.spring.services;

import java.util.List;

import com.dub.spring.domain.Category;

public interface CategoryService {

	public List<Category> getAllCategories();
	
	public List<Category> getLeaveCategories();
	
	public Category getCategory(String categorySlug);
}
