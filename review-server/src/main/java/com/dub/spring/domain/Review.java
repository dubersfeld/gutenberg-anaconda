package com.dub.spring.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/** 
 * A given user can write only one review for the same book
 * */
// POJO, not document
public class Review extends ReviewBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Review() {
		super(new ReviewBase());// to be checked
		this.voterIds = new HashSet<>();
	}
	
	public Review(ReviewBase that) {
		super(that);
		this.voterIds = new HashSet<>();
	}
	
	private String bookId;
	private String userId;
	private Set<String> voterIds;
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Set<String> getVoterIds() {
		return voterIds;
	}
	public void setVoterIds(Set<String> voterIds) {
		this.voterIds = voterIds;
	}
	
	
	
	
}