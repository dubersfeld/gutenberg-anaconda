package com.dub.spring.services;

import java.util.List;
import java.util.Optional;

import com.dub.spring.domain.Review;


public interface ReviewService {

	Review createReview(Review review);	
	
	Review getReviewById(String reviewId);	
	
	List<Review> getReviewsByUserId(String userId);
	
	List<Review> getReviewByBookId(
								String bookId, 
								String sortBy);
	
	Optional<Double> getBookRating(String bookId);
	
	boolean voteHelpful(String reviewId, String userId, boolean helpful);

}