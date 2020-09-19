package com.dub.spring.domain;

import java.io.Serializable;

public class Order extends OrderBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2623188979726019146L;
	
	private String userId;
		
	public Order() {
		super(new OrderBase());
	}
	
	public Order(OrderBase that) {
		super(that);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
