package com.dub.spring.controller.utils;

import java.util.List;

import com.dub.spring.controller.books.DisplayOthers;
import com.dub.spring.controller.display.DisplayItem;
import com.dub.spring.controller.display.DisplayItemPrice;
import com.dub.spring.controller.reviews.ReviewWithAuthor;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.Review;

public interface DisplayUtils {

	public List<ReviewWithAuthor> getReviewWithAuthors(List<Review> reviews, String userId);
	public List<DisplayOthers> getOtherBooksBoughtWith(Order order);

	List<DisplayItem> getDisplayItems(String orderId);
	List<DisplayItemPrice> getDisplayItemPrices(String orderId);
}
