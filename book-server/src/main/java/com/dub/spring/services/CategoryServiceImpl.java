package com.dub.spring.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dub.spring.domain.Category;
import com.dub.spring.domain.DocumentCategory;
import com.dub.spring.exceptions.CategoryNotFoundException;
import com.dub.spring.repository.CategoryRepository;
import com.dub.spring.utils.CategoryUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAllCategories() {
			
		List<DocumentCategory> docs = categoryRepository.findAll();
		List<Category> cats = new ArrayList<>();
		
		for (DocumentCategory doc : docs) {
			cats.add(CategoryUtils.documentToCategory(doc));
		}
		return cats;
	}

	@Override
	public List<Category> getLeaveCategories() {
		
		List<Category> cats = getAllCategories();
		List<Category> leaves = new ArrayList<>();
		
		for (Category cat : cats) {
			if (cat.getChildren().isEmpty()) {
				leaves.add(cat);
			}
		}
		return leaves;
	}

	@Override
	public Category getCategory(String categorySlug) {
		DocumentCategory doc = categoryRepository.findOneBySlug(categorySlug);
		if (doc != null) {
			return CategoryUtils.documentToCategory(doc);
		} else {
			throw new CategoryNotFoundException();
		}
		
	}

}
