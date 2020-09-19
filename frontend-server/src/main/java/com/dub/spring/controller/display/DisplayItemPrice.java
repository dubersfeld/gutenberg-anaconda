package com.dub.spring.controller.display;

import com.dub.spring.domain.Item;


public class DisplayItemPrice extends DisplayItem {

	protected double price;
	
	public DisplayItemPrice(Item source) {
		super(source);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}	
}
