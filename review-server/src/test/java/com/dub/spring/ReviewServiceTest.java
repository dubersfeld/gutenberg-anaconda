package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dub.spring.domain.Review;
import com.dub.spring.services.ReviewService;

@SpringBootTest
public class ReviewServiceTest {
	
	@Autowired
	private ReviewService reviewService;
	
	@Test
	void testCreateReview() {	     
		Review review = new Review();
		review.setBookId("5a28f2b0acc04f7f2e97409f");
		review.setUserId("5a28f2b9acc04f7f2e9740b1");
		review.setText("Lorem ipsum");
		review.setRating(3);
		
		// actual creation
		Review checkReview = this.reviewService.createReview(review);
	
		assertEquals("5a28f2b0acc04f7f2e97409f", checkReview.getBookId());
		assertEquals("5a28f2b9acc04f7f2e9740b1", checkReview.getUserId());
		assertEquals("Lorem ipsum", checkReview.getText());
		assertEquals(3, checkReview.getRating());
	}
	
	@Test
	void testReviewById() {
		String reviewId = "5a28f366acc04f7f2e9740b8";
		Review review = this.reviewService.getReviewById(reviewId);
		assertTrue(review.getText().contains("everything HareFAQ"));		
	}
	
	@Test
	void testReviewsByUserId() {
		String userId = "5a28f306acc04f7f2e9740b3";
		List<Review> reviews = this.reviewService.getReviewsByUserId(userId);
		System.err.println(reviews.size());
		assertTrue(reviews.size() == 4 && reviewMatch(reviews, "everything HareFAQ"));
	}
	
	@Test
	void testReviewsByBookId() {
		String bookId = "5a28f2b0acc04f7f2e9740a5";
		List<Review> reviews = this.reviewService.getReviewByBookId(bookId, "rating");
		
	//	assertTrue(reviews.size() == 3 && reviewMatch(reviews, "the most controversial scientist"));
		assertTrue(reviews.size() == 3 && reviews.get(0).getText().contains("the most controversial scientist"));

	}
	
	@Test
	void testBookRating() {
		String bookId = "5a28f2b0acc04f7f2e9740a5";
		Optional<Double> rating = this.reviewService.getBookRating(bookId);
		assertTrue(rating.isPresent());
		Double rat = rating.get();
		assertTrue(rat < 2.7 && rat > 2.6);
		
	}
	
	@Test
	void testBookRating2() {
		String bookId = "5a28f2b0acc04f7f2e976666";
		Optional<Double> rating = this.reviewService.getBookRating(bookId);
		assertTrue(rating.isEmpty());	
	}
	
	@Test
	void testVoteHelpful() {
		String reviewId = "5a28f366acc04f7f2e9740b9";
		String userId = "5a28f306acc04f7f2e9740b3";
			
		Boolean status = this.reviewService.voteHelpful(reviewId, userId, true);
			
		assertTrue(status);
		
	}
	
	@Test
	void testVoteHelpful2() {
		String reviewId = "5a28f366acc04f7f2e9740b9";
		String userId = "5a28f306acc04f7f2e9740b3";
			
		Boolean status = this.reviewService.voteHelpful(reviewId, userId, true);
			
		assertFalse(status);
		
	}
	
	private boolean reviewMatch(List<Review> reviews, String text) {
		
		boolean match = false;
		for (Review review : reviews) {
			if (review.getText().contains(text)) {
				match = true;
				break;
			}
		}
		return match;
	}
		
}
