package com.dub.spring.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reviews")

@CompoundIndexes({
    @CompoundIndex(name = "product_user", 
    					def = "{'bookId' : 1, 'userId': 1}", 
    					unique = true)
})
public class ReviewDocument extends ReviewBase {

	public ReviewDocument() {
		super(new ReviewBase());
		this.voterIds = new HashSet<>();
	}
	
	public ReviewDocument(ReviewBase that) {
		super(that);
		this.voterIds = new HashSet<>();
	}
	
	private ObjectId bookId;
	private ObjectId userId;
	private Set<ObjectId> voterIds;
	
	public ObjectId getBookId() {
		return bookId;
	}
	public void setBookId(ObjectId bookId) {
		this.bookId = bookId;
	}
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	public Set<ObjectId> getVoterIds() {
		return voterIds;
	}
	public void setVoterIds(Set<ObjectId> voterIds) {
		this.voterIds = voterIds;
	}
	
	
}
