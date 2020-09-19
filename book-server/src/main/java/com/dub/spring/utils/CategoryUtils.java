package com.dub.spring.utils;

import org.bson.types.ObjectId;

import com.dub.spring.domain.Category;
import com.dub.spring.domain.DocumentCategory;

public class CategoryUtils {

	public static Category documentToCategory(DocumentCategory doc) {
		
		// here parentId can be null 	
		Category cat = new Category(doc);
		
		if (doc.getParentId() != null) {
			cat.setParentId(doc.getParentId().toString());
		}
		
		for (ObjectId child : doc.getChildren()) {
			cat.getChildren().add(child.toString());
		}
		
		return cat;
	}
	
	public static DocumentCategory categoryToDocument(Category cat) {
		
		DocumentCategory doc = new DocumentCategory(cat);
		doc.setParentId(new ObjectId(cat.getParentId()));
		for (String child : cat.getChildren()) {
			doc.getChildren().add(new ObjectId(child));
		}
			
		return doc;
	}
}
