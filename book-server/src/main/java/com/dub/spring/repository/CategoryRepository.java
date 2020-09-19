package com.dub.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dub.spring.domain.DocumentCategory;

public interface CategoryRepository extends MongoRepository<DocumentCategory, String> {

	DocumentCategory findOneBySlug(String slug);
}
