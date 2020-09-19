package com.dub.spring;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dub.spring.domain.EditCart;
import com.dub.spring.domain.Item;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.OrderState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class OrderRestEndpointTest {
		
	@Autowired
	public MockMvc mockMvc;
	
	
	@Test
	void orderByIdTest() throws Exception {
		String orderId = "5a28f366acc04f7f2e9740cc";
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get("/orderById/" + orderId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		MvcResult result = this.performGet("/orderById/" + orderId, status().isOk());
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		//System.err.println(checkOrder.getState());
		assertTrue(OrderState.SHIPPED.equals(checkOrder.getState()) && checkOrder.getLineItems().size() == 1);				
	}	
	
	
	@Test
	void createOrderTest() throws Exception {
		Order newOrder = new Order();
		newOrder.setDate(new Date());
		newOrder.setState(OrderState.CART);
		newOrder.setUserId("5a28f2b0acc04f7f2e9740ae");
	
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/createOrder")
				.content(toJsonString(newOrder))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
				*/
		MvcResult result = this.performPost("/createOrder", toJsonString(newOrder), status().isCreated());
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		//System.err.println(checkOrder.getState());
		assertTrue(OrderState.CART.equals(checkOrder.getState()) && "5a28f2b0acc04f7f2e9740ae".equals(checkOrder.getUserId()));				
	}
	
	
	@Test
	void editCartTest() throws Exception {
		
		List<Item> items = new ArrayList<Item>();
		items.add(new Item("5a28f2b0acc04f7f2e9740a2", 3));
		EditCart editCart = new EditCart();
		editCart.setOrderId("5a28f366acc04f7f2e9740de");
		editCart.setItems(items);
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/editCart")
				.content(toJsonString(editCart))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		MvcResult result = this.performPost("/editCart", toJsonString(editCart), status().isOk());
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		System.out.println(checkOrder.getLineItems().size());
		assertTrue(checkOrder.getLineItems().size() == 1 
				&& "5a28f2b0acc04f7f2e9740a2".equals(checkOrder.getLineItems().get(0).getBookId()));
	}
		
	
	@Test
	void getActiveOrderTest() throws Exception {
		String userId = "5a28f364acc04f7f2e9740b4";	
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/getActiveOrder")
				.content(userId)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		MvcResult result = this.performPost("/getActiveOrder", userId, status().isOk());
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		System.out.println(checkOrder.getLineItems().size());
		assertTrue(OrderState.CART.equals(checkOrder.getState())
				&& "5a28f366acc04f7f2e9740de".equals(checkOrder.getId()));	
	}
	

	@Test
	void setOrderStateTest() throws Exception {

		String orderId = "5a28f366acc04f7f2e9740e0";
		String state = "PRE_SHIPPING";
			
		Map<String, String> contentTypeParams = new HashMap<String, String>();  
		contentTypeParams.put("boundary", "265001916915724");  
		MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/setOrderState")
				.param("orderId", orderId).param("state", state)
				.contentType(mediaType)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//MvcResult result = this.performPost("/setOrderState", userId, status().isOk());
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
			
		System.out.println(checkOrder.getLineItems().size());
		assertTrue(OrderState.PRE_SHIPPING.equals(checkOrder.getState())
				&& "5a28f366acc04f7f2e9740e0".equals(checkOrder.getId()));		
	}
	
	@Test
	void checkoutOrderTest() throws Exception {		
		String orderId = "5a28f366acc04f7f2e9740e1";
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/checkoutOrder")
				.content(orderId)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		MvcResult result = this.performPost("/checkoutOrder", orderId, status().isOk());
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		System.out.println(checkOrder.getLineItems().size());
		assertTrue(OrderState.PRE_AUTHORIZE.equals(checkOrder.getState()));
	
	}
	
	@Test
	void addBookToOrderTest() throws Exception {
			
		//OrderAndBook orderAndBook = new OrderAndBook();
		String orderId = "5a28f366acc04f7f2e9740e2";
		String bookId = "5a28f2b0acc04f7f2e9740a8";
		
		Map<String, String> contentTypeParams = new HashMap<String, String>();  
		contentTypeParams.put("boundary", "265001916915724");  
		MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
	
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addBookToOrder")
				.param("orderId", orderId).param("bookId", bookId)
				.contentType(mediaType)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		Order checkOrder = toOrder(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		
		assertTrue(itemMatch(checkOrder.getLineItems(), bookId));
		
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
	
	
	private Order toOrder(String jsonStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		Order order = mapper.readValue(jsonStr, Order.class);
		
		return order;
	}
	
	private  String toJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private MvcResult performPost(String uri, String content, ResultMatcher expectedStatus) throws Exception {
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
		
		return result;
	}
	
	private MvcResult performGet(String uri, ResultMatcher expectedStatus) throws Exception {
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
		
		return result;
	}
	
}
