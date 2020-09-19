package com.dub.spring.domain;

import org.bson.types.ObjectId;

public class UserResult {
	
	ObjectId userId;

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
}
