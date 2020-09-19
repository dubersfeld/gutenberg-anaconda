package com.dub.spring.services;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.dub.spring.domain.Book;
import com.dub.spring.domain.BookCount;
import com.dub.spring.domain.BookDocument;
import com.dub.spring.domain.DocumentCategory;
import com.dub.spring.domain.UserResult;
import com.dub.spring.exceptions.BookNotFoundException;
import com.dub.spring.repository.BookRepository;
import com.dub.spring.repository.CategoryRepository;
import com.dub.spring.utils.BookUtils;



@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
		
	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public Book getBookBySlug(String slug) {
		
		BookDocument doc = bookRepository.findOneBySlug(slug);
		
		if (doc != null) {
			return BookUtils.documentToBook(doc);
		} else {
			throw new BookNotFoundException();
		}
	}


	@Override
	public Book getBookById(String bookId) {
		
		Optional<BookDocument> book = bookRepository.findById(bookId);
		if (book.isPresent()) {
			return BookUtils.documentToBook(book.get());
		} else {
			throw new BookNotFoundException();
		}
	}


	@Override
	public List<Book> allBooksByCategory(String categorySlug, String sortBy) {
			
		// find category id
		DocumentCategory cat = categoryRepository.findOneBySlug(categorySlug);
					
		List<BookDocument> docs = bookRepository.findByCategoryId(
							new ObjectId(cat.getId()), Sort.by(Sort.Direction.ASC, sortBy));
		
		List<Book> books = new ArrayList<>(); 
		for (BookDocument doc : docs) {
			books.add(BookUtils.documentToBook(doc));
		}
			 
		return books;
	}


	@Override
	public List<Book> getBooksBoughtWith(String bookId, int outLimit) {
			
		// first aggregation: find all users who bought the book referenced by bookId
		
		MatchOperation match1 = match(Criteria.where("state").is("SHIPPED"));		
		ProjectionOperation proj1 = project("lineItems", "userId");
		UnwindOperation unwind = unwind("lineItems");
		MatchOperation match2 = match(Criteria.where("lineItems.bookId").is(bookId));	
		ProjectionOperation proj2 = project("userId");
				
		Aggregation aggregation = Aggregation.newAggregation(match1, proj1, unwind, match2, proj2);
			
		AggregationResults<UserResult> result = mongoOperations.aggregate(aggregation, "orders", UserResult.class);
		
		// result contains all users who bought the book referenced by bookId
		
		List<ObjectId> userIds = new ArrayList<>();
			
		for (UserResult bu : result.getMappedResults()) {
			userIds.add(bu.getUserId());
		}
			
		// second aggregation: find all books bought by the users returned by the first aggregation
		
		match1 = match(Criteria.where("state").is("SHIPPED")
												.and("userId").in(userIds));
	
		GroupOperation group = group("bookId").count().as("count");
		
		proj2 = project("count").and("bookId").previousOperation();
		
		ProjectionOperation projAlias = project("userId")							
									.and("lineItems.bookId").as("bookId");
		
		match2 = match(Criteria.where("bookId").ne(bookId));
		
		SortOperation sort = sort(Sort.Direction.DESC, "count");
		LimitOperation limitOp = limit(outLimit);
		
		aggregation = newAggregation(match1, unwind, projAlias, match2, group, proj2, sort, limitOp);
		
		AggregationResults<BookCount> bookCounts = mongoOperations.aggregate(
											aggregation, "orders", BookCount.class);
		
		
		// building the actual output List<Book>
		List<BookCount> bookCountList = bookCounts.getMappedResults();
		
		List<Book> outBooks = new ArrayList<>();
		
		for (BookCount bookCount : bookCountList) { 
			Optional<BookDocument> book = bookRepository.findById(bookCount.getBookId());
			if (book.isPresent()) {
				outBooks.add(new Book(book.get()));
			}
		}
	
		return outBooks;
	}
}