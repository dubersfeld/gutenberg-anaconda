package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dub.spring.domain.EditCart;
import com.dub.spring.domain.Item;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.OrderState;
import com.dub.spring.services.OrderService;

@SpringBootTest
public class OrderServiceTest {
	
	@Autowired
	private OrderService orderService;
	
	
	@Test
	void testById() {
		String orderId = "5a28f366acc04f7f2e9740cc";
		Order order = this.orderService.getOrderById(orderId);
		
		assertTrue("5a28f32dacc04f7f2e9740b4".equals(order.getUserId()));
		
	}
	

	@Test
	void testCreateOrder() {
		Order newOrder = new Order();	
		newOrder.setDate(new Date());
		newOrder.setState(OrderState.CART);
		newOrder.setUserId("5a28f364acc04f7f2e9740b7");
		
		// actual creation
		Order checkOrder = this.orderService.saveOrder(newOrder, true);
	
		assertTrue(OrderState.CART.equals(checkOrder.getState()));
	}
	
	@Test
	void testActiveOrder() {
		String userId = "5a28f364acc04f7f2e9740b7";	
		
		Optional<Order> activeOrder = this.orderService.getActiveOrder(userId);
		assertTrue(activeOrder.isPresent());
		assertTrue(OrderState.CART.equals(activeOrder.get().getState()) && activeOrder.get().getLineItems().size() == 1);
		
	}
	
	
	@Test
	void testAddBookToOrder() {
		String orderId = "5a28f366acc04f7f2e9740da";
		String bookId = "5a28f2b0acc04f7f2e97409f";
		
		Order activeOrder = this.orderService.addBookToOrder(orderId, bookId);
		
		List<Item> items = activeOrder.getLineItems();
		
		assertTrue(itemMatch(items, bookId));
		
		
	}
	
	@Test
	void testEditCart() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item("5a28f2b0acc04f7f2e9740a2", 2));
		EditCart editCart = new EditCart();
		editCart.setOrderId("5a28f366acc04f7f2e9740db");
		editCart.setItems(items);
			
		Order activeOrder = this.orderService.editCart(editCart);
		
		List<Item> checkItems = activeOrder.getLineItems();
		
		System.err.println(checkItems.size() + checkItems.get(0).getBookId());
	
		assertTrue(checkItems.size() == 1);
		assertTrue("5a28f2b0acc04f7f2e9740a2".equals(checkItems.get(0).getBookId()));
	}
	
	@Test
	void testSetOrderState() {
		String orderId = "5a28f366acc04f7f2e9740dc";
		OrderState state = OrderState.PRE_SHIPPING;
		
		Order checkOrder = this.orderService.setOrderState(orderId, state);
		
		assertTrue(OrderState.PRE_SHIPPING.equals(checkOrder.getState()));
	}
	
	@Test
	void testCheckoutOrder() {
		String orderId = "5a28f366acc04f7f2e9740dd";
		OrderState state = OrderState.PRE_AUTHORIZE;
		
		Order checkOrder = this.orderService.setOrderState(orderId, state);
		
		assertTrue(OrderState.PRE_AUTHORIZE.equals(checkOrder.getState()));
	}
	
	private boolean itemMatch(List<Item> items, String bookId) {
		
		boolean match = false;
		for (Item item : items) {
			if (bookId.equals(item.getBookId())) {
				match = true;
				break;
			}
		}
		return match;
	}
}
