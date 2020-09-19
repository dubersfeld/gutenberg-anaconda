package com.dub.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dub.spring.domain.Category;
import com.dub.spring.exceptions.CategoryNotFoundException;
import com.dub.spring.services.CategoryService;

@RestController
public class CategoryRestEndpoint {

	@Autowired 
	private CategoryService categoryService;
	
	@RequestMapping("/allCategories")
	public ResponseEntity<List<Category>> allCategories() {
			
		List<Category> categories = categoryService.getAllCategories();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}	
		
	@RequestMapping("/category/{slug}")
	public ResponseEntity<Category> getCategoryBySlug(@PathVariable("slug") String categorySlug) {
		
		try {
			Category cat = categoryService.getCategory(categorySlug);
			return new ResponseEntity<Category>(cat, HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}
	}
	
}
