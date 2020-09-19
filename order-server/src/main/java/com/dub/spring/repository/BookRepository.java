package com.dub.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dub.spring.domain.BookDocument;
import com.dub.spring.domain.OrderDocument;

public interface BookRepository extends MongoRepository<BookDocument, String> {
	
}
