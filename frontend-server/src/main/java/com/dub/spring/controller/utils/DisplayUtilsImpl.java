package com.dub.spring.controller.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dub.spring.controller.books.DisplayOthers;
import com.dub.spring.controller.display.DisplayItem;
import com.dub.spring.controller.display.DisplayItemPrice;
import com.dub.spring.controller.reviews.ReviewWithAuthor;
import com.dub.spring.domain.Book;
import com.dub.spring.domain.Item;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.Review;
import com.dub.spring.services.BookService;
import com.dub.spring.services.OrderService;
import com.dub.spring.services.ReviewService;
import com.dub.spring.services.UserService;

@Component
public class DisplayUtilsImpl implements DisplayUtils {

	@Autowired 
	private UserService userService;
	
	@Autowired 
	private OrderService orderService;
	
	@Autowired 
	private BookService bookService;
	
	@Autowired 
	private ReviewService reviewService;
	

	public List<ReviewWithAuthor> getReviewWithAuthors(List<Review> reviews, String userId)  {
		List<ReviewWithAuthor> reviewWithAuthors = new ArrayList<>();
		
		for (Review review : reviews) {
			String authorId = review.getUserId().toString();
			String authorname = userService.findById(authorId).getUsername();
			ReviewWithAuthor rwa = new ReviewWithAuthor(review, authorname);
			reviewWithAuthors.add(rwa);
			rwa.setVotedByUser(reviewService.hasVoted(review.getId(), userId));
		}
		
		return reviewWithAuthors;
		
	}


	@Override
	public List<DisplayOthers> getOtherBooksBoughtWith(Order order) {
		// find other books frequently bought with books in order
		List<DisplayOthers> others = new ArrayList<>();
				
		for (Item item : order.getLineItems()) {
			String bookId = item.getBookId();
			List<Book> otherBooks = bookService.getBooksBoughtWith(bookId, 3);
			if (!otherBooks.isEmpty()) {
				Book book = bookService.getBookById(bookId);
				others.add(new DisplayOthers(
						book, otherBooks));
			}
		}
							
		return others;
	}
	
	@Override
	public List<DisplayItem> getDisplayItems(String orderId) {
		
		Order order = orderService.getOrderById(orderId);
			
		List<Item> items = order.getLineItems();	
		List<DisplayItem> displayItems = new ArrayList<>();
									
		for (Item item : items) {
				
			Book dBook = bookService.getBookById(item.getBookId());
			DisplayItem dItem = new DisplayItem(item);
										
			dItem.setTitle(dBook.getTitle());					
			displayItems.add(dItem);	
	
		}// for
				
		return displayItems;
			
		//return null;
		
	}

	@Override
	public List<DisplayItemPrice> getDisplayItemPrices(String orderId) {
		Order order = orderService.getOrderById(orderId);
		//if (order.isPresent()) {
			List<Item> items = order.getLineItems();
			List<DisplayItemPrice> itemsWithPrices = new ArrayList<>();
								
			for (Item item : items) {
				Book dBook = bookService.getBookById(item.getBookId());
			
				//if (dBook.isPresent()) {
					double price = item.getQuantity() * dBook.getPrice()/100.0;
					DisplayItemPrice dip = new DisplayItemPrice(item);
					
					dip.setTitle(dBook.getTitle());
					dip.setPrice(price);
					itemsWithPrices.add(dip);	
				
				
			}
			
			return itemsWithPrices;
			
		
		
		
	}
}