package com.dub.spring.utils;

import org.bson.types.ObjectId;

import com.dub.spring.domain.OrderDocument;
import com.dub.spring.domain.Order;

public class OrderUtils {

public static Order documentToOrder(OrderDocument doc) {
		
		Order ord = new Order(doc);
		ord.setUserId(doc.getUserId().toString());
		
		return ord;
	}
	
	public static OrderDocument orderToDocument(Order ord) {
		
		OrderDocument doc = new OrderDocument(ord);
		doc.setUserId(new ObjectId(ord.getUserId()));
			
		return doc;
	}
}
