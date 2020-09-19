package com.dub.spring.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dub.spring.domain.BookDocument;

public interface BookRepository extends MongoRepository<BookDocument, String> {

	List<BookDocument> findByCategoryId(ObjectId categoryId, Sort sort);
	
	BookDocument findOneBySlug(String bookSlug);	
}