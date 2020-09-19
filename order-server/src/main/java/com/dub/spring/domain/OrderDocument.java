package com.dub.spring.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="orders")
public class OrderDocument extends OrderBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2623188979726019146L;

	@Indexed
	private ObjectId userId;
	
	
	public OrderDocument() {
		super(new OrderBase());
	}

	public OrderDocument(OrderBase that) {
		super(that);
	}

	
	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}


}
