package com.dub.spring.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import com.dub.spring.controller.config.ServiceConfig;
import com.dub.spring.domain.Book;
import com.dub.spring.domain.Review;
import com.dub.spring.domain.UserAndReviewedBooks;
import com.dub.spring.exceptions.BookNotFoundException;
import com.dub.spring.repository.BookRedisRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private RestOperations restTemplate;
		
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ServiceConfig serviceConfig;

	
	@Override
	public Book getBookBySlug(String slug) {
			
		try {
			String booksURI 
			= serviceConfig.getBaseBooksURL() + "/books/" + slug;
		
			ResponseEntity<Book> response 
			= restTemplate.getForEntity(booksURI, Book.class);
			
			return response.getBody();
		} catch (Exception e) {
			System.out.println("Exception caught" + e);
			throw new BookNotFoundException();
		}
	}

	
	@Override
	public Book getBookById(String bookId) {
			
	String booksURI 
			= serviceConfig.getBaseBooksURL() + "/booksById/" + bookId;
		
	Book book;
			try {
				ResponseEntity<Book> response 
				= restTemplate.getForEntity(booksURI, Book.class);
					
				book = response.getBody(); 
				
				return book;
			} catch (HttpClientErrorException e) {
				if (e.getRawStatusCode() == 404) {
					throw new BookNotFoundException(); 
				} else {
					throw new RuntimeException();
				}
			}
		
	}

	
	@Override
	public List<Book> allBooksByCategory(String categorySlug, String sortBy) {
			
		String booksURI = serviceConfig.getBaseBooksURL() + "/books/"
			+ categorySlug + "/sort/" + sortBy;
		
		ResponseEntity<List<Book>> response 
			= restTemplate.exchange(
				booksURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>(){});
		
		List<Book> books = response.getBody();
		 
		return books;
	}
	

	@Override
	public List<Book> getBooksBoughtWith(String bookId, int outLimit) {
		
		String booksURI = serviceConfig.getBaseBooksURL() + "/booksBoughtWith/"  
				+ bookId + "/outLimit/" + outLimit;
				 
		ResponseEntity<List<Book>> response 
			= restTemplate.exchange(
				booksURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>(){});
		
		List<Book> outBooks = response.getBody();
		return outBooks;	
	}
	


	@Override
	public List<Book> getBooksNotReviewed(String userId, int outLimit) throws ParseException {
		
		/** 
		 * first step: find all books 
		 * already reviewed by user referenced by userId 
		 * */
			
	
		List<Review> reviews = reviewService.getReviewsByUserId(userId);
			
		List<String> reviewedBookIds = new ArrayList<>();
		
		for (Review review : reviews) {
			reviewedBookIds.add(review.getBookId().toString());
		}
		
		/** 
		 * second step: find all books 
		 * recently bought by user referenced by userId 
		 * that were not reviewed by user yet.
		 * this step is implemented on order server side
		 * we need to post a List<String> to order server
		 * it is easier here to post an encapsulating object
		 * the helper class is named UserAndReviews
		 * */
		
		UserAndReviewedBooks userAndReviewedBooks = new UserAndReviewedBooks(userId, reviewedBookIds, outLimit);
		
		String ordersURI = serviceConfig.getBaseOrdersURL() + "/getBooksNotReviewed";
			
		List<MediaType> amt = new ArrayList<>(); 
		amt.add(MediaType.APPLICATION_JSON);   
		HttpHeaders headers = new HttpHeaders();	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(amt);// JSON expected from resource server
		
		HttpEntity<UserAndReviewedBooks> requestEntity = 
	    		new HttpEntity<UserAndReviewedBooks>(userAndReviewedBooks, headers);
				
		ResponseEntity<List<String>> response 
				= restTemplate.exchange(
					ordersURI, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<String>>(){});
		
		List<Book> booksToReview = new ArrayList<>();
		for (String bookId : response.getBody()) {	
			Book book = getBookById(bookId); 
			booksToReview.add(book);
		}
	
		return booksToReview;
	}
	
	
	 
	
}
