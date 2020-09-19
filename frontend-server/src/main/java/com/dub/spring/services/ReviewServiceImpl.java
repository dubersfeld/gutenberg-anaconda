package com.dub.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.dub.spring.controller.config.ServiceConfig;
import com.dub.spring.domain.Review;


@Service
public class ReviewServiceImpl implements ReviewService {
			
	@Autowired
	ServiceConfig serviceConfig;
	
	@Autowired
	private RestOperations restTemplate;
		
	@Override
	public Review createReview(Review review) {
		
		HttpHeaders headers = new HttpHeaders();
		List<MediaType> amt = new ArrayList<>();
		amt.add(MediaType.APPLICATION_JSON);
		headers.setAccept(amt);
				
		String reviewsURI = serviceConfig.getBaseReviewsURL() + "/createReview";
		
		ResponseEntity<Review> response 
		= restTemplate.postForEntity(reviewsURI, review, Review.class);
		
		return review;
	}


	@Override
	public List<Review> getReviewsByBookId(String bookId, String sortBy) {

		String reviewsURI = serviceConfig.getBaseReviewsURL() + "/reviewsByBookId/" + bookId
				+ "/sort/" + sortBy;
				
		ResponseEntity<List<Review>> response 
		= restTemplate.exchange(
			reviewsURI, HttpMethod.POST, null, new ParameterizedTypeReference<List<Review>>(){});
	
		List<Review> reviews = response.getBody();//.getReviews();
											
		return reviews;
	}

	@Override
	public Optional<Double> getBookRating(String bookId) {
		
		String reviewsURI = serviceConfig.getBaseReviewsURL() + "/bookRating/" + bookId;
		
		ResponseEntity<Double> response 
		= restTemplate.getForEntity(reviewsURI, Double.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return Optional.of(response.getBody());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public boolean hasVoted(String reviewId, String userId) {
		
		String reviewURI = serviceConfig.getBaseReviewsURL() + "/reviewById/" + reviewId;
			
		ResponseEntity<Review> response 
		= restTemplate.getForEntity(reviewURI, Review.class);
		
		Review review = response.getBody();
		Set<String> voterIds = review.getVoterIds();
		
		return voterIds.contains(userId);
	}

	@Override
	public void voteHelpful(String reviewId, String userId, boolean helpful) {
	
		HttpHeaders headers = new HttpHeaders();
		List<MediaType> amt = new ArrayList<>();
		amt.add(MediaType.APPLICATION_JSON);
		headers.setAccept(amt);
			
		Boolean help = Boolean.valueOf(helpful);
		
		String reviewURI = serviceConfig.getBaseReviewsURL()
		+ "/addVote/" + reviewId + "/user/" + userId;
				
		// actual POST
		restTemplate.postForEntity(
			reviewURI, help, Boolean.class);
			
	}


	@Override
	public List<Review> getReviewsByUserId(String userId) {
		
		String reviewsURI = serviceConfig.getBaseReviewsURL() + "/reviewsByUserId";
					
		HttpEntity<String> request 
					= new HttpEntity<>(userId, null);
		
		// actual POST		
		ResponseEntity<List<Review>> response 
		= restTemplate.exchange(
			reviewsURI, HttpMethod.POST, request, new ParameterizedTypeReference<List<Review>>(){});
	
		return response.getBody();
	}
}
