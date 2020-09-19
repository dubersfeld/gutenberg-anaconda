package com.dub.spring.services;

import static com.dub.spring.controller.DateCorrect.correctDate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.Book;
import com.dub.spring.domain.BookDocument;
import com.dub.spring.domain.BookUser;
import com.dub.spring.domain.OrderDocument;
import com.dub.spring.domain.EditCart;
import com.dub.spring.domain.Item;
import com.dub.spring.domain.Order;
import com.dub.spring.domain.OrderState;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.domain.UserAndReviewedBooks;
import com.dub.spring.exceptions.BookNotFoundException;
import com.dub.spring.exceptions.OrderException;
import com.dub.spring.exceptions.OrderNotFoundException;
import com.dub.spring.repository.BookRepository;
import com.dub.spring.repository.OrderRepository;
import com.dub.spring.utils.BookUtils;
import com.dub.spring.utils.OrderUtils;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${baseBooksUrl}")
	private String baseBooksURL;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private OrderRepository orderRepository;
		
	@Autowired
	private MongoOperations mongoOperations;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Override
	public Order saveOrder(Order order, boolean creation) {
		if (!creation) {
			//check for presence if not creation
			this.getRawOrder(order.getId());
			
			OrderDocument doc = 
					orderRepository.save(OrderUtils.orderToDocument(order));
			
			return OrderUtils.documentToOrder(doc);
		}
		// creation
		
		OrderDocument newDoc = 
				orderRepository.save(OrderUtils.orderToDocument(order));
		
		return OrderUtils.documentToOrder(newDoc);
	}
	
	
	@Override
	public Order addBookToOrder(String orderId, String bookId) {

		Order order = this.getRawOrder(orderId);// not recalculate yet
						
		// first add new item if not already present
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId)
							.and("state").is(OrderState.CART)
							.and("lineItems.bookId").ne(bookId));
					
		Update update = new Update();
		update.push("lineItems", new Item(bookId, 0));
		OrderDocument doc = mongoOperations.findAndModify(query, update, 
				new FindAndModifyOptions().returnNew(true), 
				OrderDocument.class);
				
		// If item already present then increment quantity
		query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId)
					.and("state").is(OrderState.CART)
					.and("lineItems.bookId").is(bookId));
	 
		update = new Update();
		update.inc("lineItems.$.quantity", 1);
	
		doc = mongoOperations.findAndModify(query, update, 
					new FindAndModifyOptions().returnNew(true), 
					OrderDocument.class);
			
		// recalculate needed after change
		order = this.recalculateTotal(orderId);
		return order;
	}


	@Override
	public Order setOrderState(String orderId, OrderState state) {
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId));
											
		Update update = new Update();
		update.set("state", state);
		
		Order oldOrder = this.getOrderById(orderId);
		
		OrderState oldState = oldOrder.getState();
		
		// legal transitions only
		switch (state) {
		case CART:
			if (oldState.equals(OrderState.SHIPPED) ||
					oldState.equals(OrderState.PRE_SHIPPING)) {
				throw new OrderException();
			}
			break;
		case PRE_AUTHORIZE:
			if (oldState.equals(OrderState.SHIPPED) ||
					oldState.equals(OrderState.PRE_SHIPPING)) {
				throw new OrderException();
			}
			break;
		case PRE_SHIPPING:
			if (oldState.equals(OrderState.SHIPPED)) {
				throw new OrderException();
			}
			break;
		default:
			throw new OrderException();// should not be here
		}
											
		OrderDocument doc = mongoOperations.findAndModify(query, update, options, OrderDocument.class);
		
		return OrderUtils.documentToOrder(doc);
	}
	

	@Override
	public Optional<Order> getActiveOrder(String userId) {

		
		
		ObjectId userObj = new ObjectId(userId);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userObj)
											.and("state").nin("PRE_SHIPPING", "SHIPPED"));
		
		// can be null
		OrderDocument doc = mongoOperations.findOne(query, OrderDocument.class);
		
		Order order = doc != null ? OrderUtils.documentToOrder(doc): null;
				
		return Optional.ofNullable(order);
	}

	@Override
	public Order checkoutOrder(String orderId) {
				
		Query query = new Query();	
		query.addCriteria(Criteria.where("id").is(orderId).and("state").is(OrderState.CART));
				
		Update update = new Update();
		update.set("state", OrderState.PRE_AUTHORIZE);
				
		OrderDocument doc = mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), OrderDocument.class);
		
		return OrderUtils.documentToOrder(doc);		
	}

	@Override
	public Order getOrderById(String orderId) {
		Optional<OrderDocument> order = orderRepository.findById(orderId);
		if (order.isPresent()) {
			return this.recalculateTotal(orderId);
		} else {
			throw new OrderNotFoundException();
		}
	}

	
	@Override
	public Order editCart(EditCart editCart) {
		
		List<Item> items = editCart.getItems();
		String orderId = editCart.getOrderId();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId));
		
		Update update = new Update();
		update.set("lineItems", items);
		
		OrderDocument doc = mongoOperations.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true),
				OrderDocument.class);
		
		// recalculate needed after change
		Order order = this.recalculateTotal(orderId);

		return order;
	}
	
	@Override
	public Order setShippingAddress(String orderId, Address address) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId));
		Update update = new Update();
		update.set("shippingAddress", address);
		
		OrderDocument doc = mongoOperations.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true),
				OrderDocument.class);
		
		return OrderUtils.documentToOrder(doc);
	}

	@Override
	public Order setPaymentMethod(String orderId, PaymentMethod method) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId));
		Update update = new Update();
		update.set("paymentMethod", method);
		
		OrderDocument doc = mongoOperations.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true),
				OrderDocument.class);
		
		return OrderUtils.documentToOrder(doc);
	}

	@Override
	public Order setDate(String orderId, Date date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderId));
		Update update = new Update();
		update.set("date", date);
		
		OrderDocument doc = mongoOperations.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true),
				OrderDocument.class);
		
		return OrderUtils.documentToOrder(doc);
	}

	
	/** 
	 * private method that connects to book server
	 * */
	

	private Book getBookById(String bookId) {
			
		Optional<BookDocument> book = this.bookRepository.findById(bookId);
		
	
		if (book.isPresent()) {
			return BookUtils.documentToBook(book.get());
		} else {
			throw new BookNotFoundException();
		}
	}



	// method using an aggregation on orders collection
	@Override
	public List<String> getBooksNotReviewed(UserAndReviewedBooks userAndReviewedBooks) throws ParseException {
		
		System.err.println("getBooksNotReviewed begin "
				+ userAndReviewedBooks.getUserId()
				+ " " + userAndReviewedBooks.getReviewedBookIds().size());
		// preparing an aggregation on orders collection
		
		UnwindOperation unwind = unwind("lineItems");
		
		// set up limit date in application.properties or better add an admin page
		Date limitDate = correctDate(sdf.parse("2017-04-24"));

		MatchOperation match1 = match(Criteria.where("state").is("SHIPPED")
											.and("date").gte(limitDate)
											.and("userId").is(new ObjectId(userAndReviewedBooks.getUserId())));
										
		MatchOperation match2 = match(Criteria.where("bookId").nin(userAndReviewedBooks.getReviewedBookIds()));
		
		GroupOperation group = group("bookId").last("userId").as("userId");			
					
		ProjectionOperation proj1 = project("lineItems", "userId");
		ProjectionOperation proj2 = project("userId").and("bookId").previousOperation();
		ProjectionOperation projAlias = project("userId")							
					.and("lineItems.bookId").as("bookId");
		
		LimitOperation limitOp = limit(userAndReviewedBooks.getOutLimit());
		
		Aggregation aggregation = newAggregation(match1, proj1, unwind, projAlias, group, proj2, match2, limitOp);

		AggregationResults<BookUser> bookResults = mongoOperations.aggregate(
				aggregation, "orders", BookUser.class);

		List<String> booksToReview = new ArrayList<>();
		
		
		
		for (BookUser bu : bookResults) {
			
			booksToReview.add(bu.getBookId());
		}
				
		return booksToReview;
	}
	
	// private methods for convenience
	private Order getRawOrder(String orderId) {
		Optional<OrderDocument> order = orderRepository.findById(orderId);
		if (order.isPresent()) {
			return OrderUtils.documentToOrder(order.get());
		} else {
			throw new OrderNotFoundException();
		}
	}
	
	private Order recalculateTotal(String orderId) {
		Optional<OrderDocument> doc = orderRepository.findById(orderId);
		
		if (doc.isPresent()) {
			Order ord = OrderUtils.documentToOrder(doc.get());
		 
			int total = 0;
			
			// clean this stuff later
		
			for (Item item : ord.getLineItems()) {
				Book book = this.getBookById(item.getBookId());
			
				total += book.getPrice() * item.getQuantity();
			}// for
		
			ord.setSubtotal(total);// actual update
		
			// always save updated order
			orderRepository.save(OrderUtils.orderToDocument(ord));
		
			return ord;// always return updated order
		} else {
			throw new OrderNotFoundException();
		}
	}

}
