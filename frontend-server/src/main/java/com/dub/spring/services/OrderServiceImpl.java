package com.dub.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import com.dub.spring.controller.config.ServiceConfig;
import com.dub.spring.domain.Address;
import com.dub.spring.domain.EditCart;
import com.dub.spring.domain.Item;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.exceptions.OrderNotFoundException;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private RestOperations restTemplate;
		
	//@Autowired
	//private BookService bookService;
	
	@Autowired
	private ServiceConfig serviceConfig;
			
	@Override
	public Order saveOrder(Order order) {
		
		String orderURI = serviceConfig.getBaseOrdersURL() + "/updateOrder";
			
		try {
			ResponseEntity<Order> response 
			= restTemplate.postForEntity(orderURI, order, Order.class);

			Order newOrder = response.getBody();
		
			return newOrder;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public Order createOrder(Order order) {
		
		String orderURI = serviceConfig.getBaseOrdersURL() + "/createOrder";
			
		ResponseEntity<Order> response 
		= restTemplate.postForEntity(orderURI, order, Order.class);

		Order newOrder = response.getBody();
		
		return newOrder;
	}

	@Override
	public Order addBookToOrder(String orderId, String bookId) {
				
		// call order server
		MultiValueMap<String, Object> map 
							= new LinkedMultiValueMap<>();
		map.add("orderId", orderId);
		map.add("bookId", bookId);
				
		List<MediaType> amt = new ArrayList<>(); 
		amt.add(MediaType.APPLICATION_JSON);   
		HttpHeaders headers = new HttpHeaders();	
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAccept(amt);// JSON expected from resource server
						    	
		HttpEntity<MultiValueMap<String, Object>> requestEntity = 
		    		new HttpEntity<MultiValueMap<String, Object>>(map, headers);
			
		String orderURI = serviceConfig.getBaseOrdersURL() + "/addBookToOrder";    

		try {
			ResponseEntity<Order> response = restTemplate.exchange(
		    			orderURI, HttpMethod.POST, requestEntity, Order.class);
		
			Order newOrder = response.getBody();    
			return newOrder;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException();
		}
	}

	
	@Override
	public Optional<Order> getActiveOrder(String userId) {

		String orderURI = serviceConfig.getBaseOrdersURL() + "/getActiveOrder";
		
		try {
			ResponseEntity<?> response 
			= restTemplate.postForEntity(orderURI, userId, Order.class);
	
			if (response.getStatusCode() == HttpStatus.OK) {				
				return Optional.of((Order)response.getBody());
			} else {	
				return Optional.empty();
			}
		} catch (HttpClientErrorException e) {
			throw new RuntimeException();
		}
	
	
		
	}

	@Override
	public Order checkoutOrder(String orderId) {
				
		String orderURI = serviceConfig.getBaseOrdersURL() + "/checkoutOrder";
	
		try {
			ResponseEntity<Order> response 
			= restTemplate.postForEntity(orderURI, orderId, Order.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 404) {
				throw new OrderNotFoundException();
			} else {
				throw new RuntimeException();
			}	
		}
		
	}

	@Override
	public Order setPreShipping(String orderId) {
			
		String orderURI = serviceConfig.getBaseOrdersURL() + "/setPreShipping";
				
		try {
			ResponseEntity<Order> response 
			= restTemplate.postForEntity(orderURI, orderId, Order.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 404) {
				throw new OrderNotFoundException();
			} else {
				throw new RuntimeException();
			}	
		}
	}
	
	
	@Override
	public Order setCart(String orderId) {
		
		MultiValueMap<String, Object> map 
						= new LinkedMultiValueMap<>();
		map.add("orderId", orderId);
		map.add("state", Order.State.CART);

		List<MediaType> amt = new ArrayList<>();
		amt.add(MediaType.APPLICATION_JSON);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAccept(amt);// JSON expected from resource server

		HttpEntity<MultiValueMap<String, Object>> requestEntity = 
				new HttpEntity<MultiValueMap<String, Object>>(map, headers);

		String orderURI = serviceConfig.getBaseOrdersURL() + "/setOrderState";
				
		try {
			ResponseEntity<Order> response = restTemplate.exchange(
	    			orderURI, HttpMethod.POST, requestEntity, Order.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 404) {
				throw new OrderNotFoundException();
			} else {
				throw new RuntimeException();
			}	
		}
	}

	@Override
	public Order getOrderById(String orderId) {
		
		String orderURI = serviceConfig.getBaseOrdersURL() + "/orderById/" + orderId;
		
		ResponseEntity<Order> response 
		= restTemplate.getForEntity(orderURI, Order.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new OrderNotFoundException();
		}
	}

	@Override
	public Order editOrder(String orderId, List<Item> items) {
		
		String orderURI = serviceConfig.getBaseOrdersURL() + "/editCart";
		
		// encapsulation
		EditCart editCart = new EditCart(orderId, items);
			
		try {
			ResponseEntity<Order> response 
			= restTemplate.postForEntity(orderURI, editCart, Order.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 404) {
				throw new OrderNotFoundException();
			} else {
				throw new RuntimeException();
			}	
		}
	}
	
	@Override
	public Order setOrderState(String orderId, Order.State state) {
		
		MultiValueMap<String, Object> map 
						= new LinkedMultiValueMap<>();
		map.add("orderId", orderId);
		map.add("state", stateToString(state));

		List<MediaType> amt = new ArrayList<>();
		amt.add(MediaType.APPLICATION_JSON);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAccept(amt);// JSON expected from resource server

		HttpEntity<MultiValueMap<String, Object>> requestEntity = 
				new HttpEntity<MultiValueMap<String, Object>>(map, headers);

		String orderURI = serviceConfig.getBaseOrdersURL() + "/setOrderState";
		
		try {
			ResponseEntity<Order> response = restTemplate.exchange(
	    			orderURI, HttpMethod.POST, requestEntity, Order.class);
	    		
			return response.getBody();
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 404) {
				throw new OrderNotFoundException();
			} else {
				throw new RuntimeException();
			}	
		}
	}
	
	private String stateToString(Order.State state) {
		
		String stateStr;
		
		switch (state) {
			case CART:
				stateStr = "CART";
				break;
			case PRE_SHIPPING:
				stateStr = "PRE_SHIPPING";
				break;
			case SHIPPED:
				stateStr = "SHIPPED";
				break;
			case PRE_AUTHORIZE:
				stateStr = "PRE_AUTHORIZE";
				break;
			default:
				stateStr = "CART";
		}
		return stateStr;
	}

	@Override
	public Order finalizeOrder(Order order, Address shippingAddress, PaymentMethod payMeth) {
		
		order.setDate(new Date());
		order.setState(Order.State.PRE_SHIPPING);
		order.setPaymentMethod(payMeth);
		order.setShippingAddress(shippingAddress);
		
		return order;
	}
}