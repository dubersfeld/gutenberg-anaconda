package com.dub.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.Item;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.PaymentMethod;

@PreAuthorize("hasAuthority('ROLE_USER')")
public interface OrderService {

	Order createOrder(Order order);
	
	Order saveOrder(Order order);
	
	Order getOrderById(String orderId);
	Optional<Order> getActiveOrder(String userId);// Not in PRE_SHIPPING or SHIPPED state
	Order addBookToOrder(String orderId, String bookId);
		
	Order checkoutOrder(String orderId);
	
	Order setPreShipping(String orderId);
	
	Order setOrderState(String orderId, Order.State state);
	
	Order setCart(String orderId);
	Order editOrder(String orderId, List<Item> items);
	
	Order finalizeOrder(Order order, Address shippingAddress, PaymentMethod payMeth);
}
