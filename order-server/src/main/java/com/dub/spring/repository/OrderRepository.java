package com.dub.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dub.spring.domain.OrderDocument;

public interface OrderRepository extends MongoRepository<OrderDocument, String> {
	
}
