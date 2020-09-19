package com.dub.spring.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dub.spring.domain.EditCart;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.OrderState;
import com.dub.spring.domain.UserAndReviewedBooks;
import com.dub.spring.exceptions.OrderException;
import com.dub.spring.exceptions.OrderNotFoundException;
import com.dub.spring.services.OrderService;



@RestController
public class OrderRestEndpoint {
	
	@Autowired 
	private OrderService orderService;
	
	
	@RequestMapping(
			value = "/getBooksNotReviewed",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getBookNotReviewed(
							@RequestBody UserAndReviewedBooks userAndReviewedBooks) {
	
	
		try {
			List<String> booksToReview = orderService.getBooksNotReviewed(userAndReviewedBooks);
			return new ResponseEntity<List<String>>(booksToReview, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(
			value = "/updateOrder",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> saveOrder(
									@RequestBody Order order) {
					
		Order newOrder = orderService.saveOrder(order, false);//.getOrderById(orderId);
							
		return new ResponseEntity<Order>(newOrder, HttpStatus.OK);	
	}
	
	
	@RequestMapping(
			value = "/setPreShipping",
			method = RequestMethod.POST) 
	public ResponseEntity<Order> setPreShipping(@RequestBody String orderId) {
		
		try {
			Order newOrder = orderService.setOrderState(orderId, OrderState.PRE_SHIPPING);
			
			return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		} catch (OrderException e) {
			return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	@RequestMapping(
			value = "/checkoutOrder",
			method = RequestMethod.POST)
	public ResponseEntity<Order> checkOrder(
							@RequestBody String orderId) {
		
		try {
			Order newOrder = orderService.checkoutOrder(orderId);
			return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			value = "/setOrderState",
			method = RequestMethod.POST,
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> setOrderState(
			@RequestParam("orderId") String orderId,
			@RequestParam("state") String state) {
		
		try {			
			Order newOrder = orderService.setOrderState(orderId, stringToState(state));
			return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			value = "/getActiveOrder",
			method = RequestMethod.POST)
	public ResponseEntity<Order> getActiveOrder(@RequestBody String userId) {
		
		
		Optional<Order> order = orderService.getActiveOrder(userId);
			
		if (order.isPresent()) {
			return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
		} else {
			// no active order found	
			return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	@RequestMapping(
			value = "/editCart",
			method = RequestMethod.POST) 
	public ResponseEntity<Order> editCart(@RequestBody EditCart editCart) {
		
		try {			
			Order order = orderService.editCart(editCart);
		
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@RequestMapping(
			value = "/addBookToOrder",
			method = RequestMethod.POST,
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> addBookToOrder(
								@RequestParam("orderId") String orderId,
								@RequestParam("bookId") String bookId) {
			
		try {
			Order order = orderService.addBookToOrder(orderId, bookId);
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			value = "/createOrder",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
					
		Order newOrder = orderService.saveOrder(order, true);
							
		return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/orderById/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable("orderId") String orderId) {
		
		try {
			Order order = orderService.getOrderById(orderId);	
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
	}
	

	private OrderState stringToState(String stateStr) {
		
		OrderState state;
		
		switch (stateStr) {
		case "CART":
			state = OrderState.CART;
			break;
		case "PRE_SHIPPING":
			state = OrderState.PRE_SHIPPING;
			break;
		case "SHIPPED":
			state = OrderState.SHIPPED;
			break;
		case "PRE_AUTHORIZE":
			state = OrderState.PRE_AUTHORIZE;
			break;
		default:
			state = OrderState.CART;
		}
		
		return state;
	}

}

