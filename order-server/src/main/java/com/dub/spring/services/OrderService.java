package com.dub.spring.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.EditCart;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.OrderState;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.domain.UserAndReviewedBooks;

public interface OrderService {
	
	List<String> getBooksNotReviewed(UserAndReviewedBooks userAndReviewedBooks) 
											throws ParseException;
	
	Order saveOrder(Order order, boolean creation);
	
	Order getOrderById(String orderId);
	
	Optional<Order> getActiveOrder(String userId);// Not in PRE_SHIPPING or SHIPPED state

	Order addBookToOrder(String orderId, String bookId);
	
	Order editCart(EditCart editCart);
	
	Order setOrderState(String orderId, OrderState state);
	
	Order checkoutOrder(String orderId);
			
	Order setShippingAddress(String orderId, Address address);
	
	Order setPaymentMethod(String orderId, PaymentMethod method);
		
	Order setDate(String orderId, Date date);
}
