package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BookRestEndpointTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	

	@Test
	void testBySlug() throws Exception {
		//MvcResult result = mockMvc.perform(get("/books/mess-harefaq-1542")).andExpect(status().isOk())
		//.andReturn();	
		MvcResult result = performGet("/books/mess-harefaq-1542", status().isOk());
		String enclume = result.getResponse().getContentAsString();
		assertTrue(enclume.contains("Messaging with HareFAQ"));			
	}
	
	@Test
	void testBySlugNotFound() throws Exception {
		//mockMvc.perform(get("/books/mess-harefaq-666")).andExpect(status().isNotFound())
		//.andReturn();					
		performGet("/books/mess-harefaq-666", status().isNotFound());
		
	}
	
	@Test
	void testById() throws Exception {
//		MvcResult result = mockMvc.perform(get("/booksById/5a28f2b0acc04f7f2e97409f")).andExpect(status().isOk())
//		.andReturn();	
		MvcResult result = performGet("/booksById/5a28f2b0acc04f7f2e97409f", status().isOk());
		String enclume = result.getResponse().getContentAsString();
		assertTrue(enclume.contains("Messaging with HareFAQ"));			
	}

	@Test
	void testByIdNotFound() throws Exception {
		//mockMvc.perform(get("/booksById/5a28f2b0acc04f7f2e976666")).andExpect(status().isNotFound())
		//.andReturn();	
		performGet("/booksById/5a28f2b0acc04f7f2e976666", status().isNotFound());	
	}

	
	// more advanced methods
	
	@Test
	void testBoughtWith() throws Exception {
		/*
		MvcResult result = mockMvc.perform(get("/booksBoughtWith/5a28f2b0acc04f7f2e9740a0/outLimit/10"))
				.andExpect(status().isOk())
			    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))	       
		.andReturn();
		*/	
		MvcResult result = performGet("/booksBoughtWith/5a28f2b0acc04f7f2e9740a0/outLimit/10", status().isOk());	

		String enclume = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(enclume.contains("Eleanor Brontë and the Blank Page Challenge"));			
	}
	
	private MvcResult performGet(String uri, ResultMatcher expectedStatus) throws Exception {		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
		return result;
	}

}
