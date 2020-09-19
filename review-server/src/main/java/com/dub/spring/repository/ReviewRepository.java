package com.dub.spring.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dub.spring.domain.ReviewDocument;

public interface ReviewRepository extends MongoRepository<ReviewDocument, String> {
	
	List<ReviewDocument> findByBookId(ObjectId bookId);
	
	List<ReviewDocument> findByBookId(ObjectId bookId, Sort sort);
	
	List<ReviewDocument> findByUserId(ObjectId userId);

}
