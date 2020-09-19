package com.dub.spring.controller.display;

import com.dub.spring.domain.Item;


public class DisplayItem extends Item {

	protected String title;
	
	public DisplayItem(Item source)  {
		super(source);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
