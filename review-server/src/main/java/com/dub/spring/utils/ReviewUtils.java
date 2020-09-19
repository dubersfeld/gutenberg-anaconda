package com.dub.spring.utils;

import org.bson.types.ObjectId;

import com.dub.spring.domain.Review;
import com.dub.spring.domain.ReviewDocument;

public class ReviewUtils {

	public static Review documentToReview(ReviewDocument doc) {
		
		Review rev = new Review(doc);
		rev.setBookId(doc.getBookId().toString());
		rev.setUserId(doc.getUserId().toString());
		for (ObjectId voterId : doc.getVoterIds()) {
			rev.getVoterIds().add(voterId.toString());
		}
		
		return rev;
	}
	
	public static ReviewDocument reviewToDocument(Review rev) {
		
		ReviewDocument doc = new ReviewDocument(rev);
		doc.setBookId(new ObjectId(rev.getBookId()));
		doc.setUserId(new ObjectId(rev.getUserId()));
		for (String voterId : rev.getVoterIds()) {
			doc.getVoterIds().add(new ObjectId(voterId));
		}
		
		return doc;
	}
}
