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

import com.dub.spring.domain.Review;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ReviewRestEndpointTest {

	@Autowired
	public MockMvc mockMvc;
	
	@Test
	void reviewsByUserId() throws Exception {
		String userId = "5a28f35cacc04f7f2e9740b6";
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/reviewsByUserId")
				.content(userId)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		
		MvcResult result = this.performPost("/reviewsByUserId", userId, status().isOk());
		String reviews = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue(reviews.contains("biography of the most controversial scientist"));				
	}	
	
	@Test
	void reviewsByUserIdFail() throws Exception {
		String userId = "5a28f35cacc04f7f2e976666";
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/reviewsByUserId")
				.content(userId)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		
		MvcResult result = this.performPost("/reviewsByUserId", userId, status().isOk());
		
		String reviews = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue("[]".equals(reviews));	
	}	
	
	@Test
	void testByBookId() throws Exception {
		String bookId = "5a28f2b0acc04f7f2e9740a5";
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get("/reviewsByBookId/" + bookId + "/sort/title")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		
		MvcResult result = this.performGet("/reviewsByBookId/" + bookId + "/sort/title", status().isOk());
	
		String enclume = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(enclume.contains("biography of the most controversial scientist"));					
	}	
	
	@Test
	void testCreateReview() throws Exception {
		Review review = new Review();
		review.setBookId("5a28f2b0acc04f7f2e9740a9");
		review.setUserId("5a28f364acc04f7f2e9740b7");
		review.setText("Lorem ipsum");
		review.setRating(3);
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/createReview")
				.content(asJsonString(review))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		*/
		
		MvcResult result = this.performPost("/createReview", asJsonString(review), status().isCreated());
		
		String enclume = result.getResponse().getHeader("location");
		
		assertTrue(enclume.contains("http://localhost:8082/reviewById"));
	}
	
	@Test
	void testVoteHelpful() throws Exception {
		String reviewId = "5a28f366acc04f7f2e9740c6";
		String userId = "5a28f32dacc04f7f2e9740b4";
		Boolean helpful = true;
		/*
		mockMvc.perform(
				MockMvcRequestBuilders
				.post("/addVote/" + reviewId + "/user/" + userId)
				.content(asJsonString(helpful))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		
		performPost("/addVote/" + reviewId + "/user/" + userId, asJsonString(helpful), status().isOk());
		
	}
	
	@Test
	void testVoteHelpfulFail() throws Exception {
		String reviewId = "5a28f366acc04f7f2e9740ba";
		String userId = "5a28f306acc04f7f2e9740b3";
		Boolean helpful = true;
		/*
		mockMvc.perform(
				MockMvcRequestBuilders
				.post("/addVote/" + reviewId + "/user/" + userId)
				.content(asJsonString(helpful))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();
				*/
		performPost("/addVote/" + reviewId + "/user/" + userId, asJsonString(helpful), status().isConflict());

	}
		
	@Test
	void testById() throws Exception {
		String reviewId = "5a28f366acc04f7f2e9740ba";

		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get("/reviewById/" + reviewId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		MvcResult result = performGet("/reviewById/" + reviewId, status().isOk());

		String enclume = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue(enclume.contains("comprehensive reference manual for Emerald"));				
	}
		

	@Test
	void testBookRating() throws Exception {
		
		String bookId = "5a28f2b0acc04f7f2e9740a5";

		/*
		MvcResult result = mockMvc.perform(
		MockMvcRequestBuilders
		.get("/bookRating/" + bookId)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn();
*/
		MvcResult result = performGet("/bookRating/" + bookId, status().isOk());

		String enclume = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue(enclume.contains("2.6666"));					
	}	
	
	@Test
	void testBookRatingFail() throws Exception {
		
		String bookId = "5a28f2b0acc04f7f2e976666";

		/*
		mockMvc.perform(
		MockMvcRequestBuilders
		.get("/bookRating/" + bookId)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent())
		.andReturn();	
		*/
		
		performGet("/bookRating/" + bookId, status().isNoContent());

	}	
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private MvcResult performPost(String uri, String content, ResultMatcher expectedStatus) throws Exception {
			
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
		
		return result;
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
