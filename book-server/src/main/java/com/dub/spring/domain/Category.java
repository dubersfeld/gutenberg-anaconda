package com.dub.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class Category extends CategoryBase {
	
	private String parentId;
	private List<String> children;
	
	public Category() {
		super(new CategoryBase());
		this.children = new ArrayList<>();
	}
	
	public Category(CategoryBase that) {
		super(that);
		this.children = new ArrayList<>();
	}
	

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}
}