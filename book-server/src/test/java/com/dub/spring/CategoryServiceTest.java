package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dub.spring.domain.Category;
import com.dub.spring.exceptions.CategoryNotFoundException;
import com.dub.spring.services.CategoryService;

@SpringBootTest
public class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@Test
	void testAllCategories() {
	     List<Category> cats = categoryService.getAllCategories();
	    
	     assertTrue(match(cats, "Biographies"));
	     assertTrue(cats.size() == 5);
	}
	
	@Test
	void testBySlug() {
	     Category cat = categoryService.getCategory("computer-science");
	     	    
	     assertTrue("Computer science".equals(cat.getName()));
	}
	
	@Test
	void testBySlugNotFound() {    	    
	     assertThrows(CategoryNotFoundException.class, () -> {
	    	 categoryService.getCategory("compuuter-science");
	     });
	}
	
	
	// utility methods
	
	private boolean match(List<Category> cats, String name) {
		boolean match = false;
		for (Category cat : cats) {
			if (name.equals(cat.getName())) {
				match = true;
				break;
			}
		}
		return match;
	}

}
