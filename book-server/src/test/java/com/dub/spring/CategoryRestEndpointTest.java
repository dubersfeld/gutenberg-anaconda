package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CategoryRestEndpointTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	

	@Test
	void testAllCategories() throws Exception {
		MvcResult result = mockMvc.perform(get("/allCategories")).andExpect(status().isOk())
		.andReturn();
		
		String enclume = result.getResponse().getContentAsString();
		
		assertTrue(enclume.contains("Textbooks for professionals"));
					
	}
	
	
	@Test
	void testBySlug() throws Exception {
		MvcResult result = mockMvc.perform(get("/category/computer-science")).andExpect(status().isOk())
		.andReturn();
		
		String enclume = result.getResponse().getContentAsString();
		
		System.err.println(enclume);
		assertTrue(enclume.contains("Computer science"));
					
	}
	
	
	
	
	

}
