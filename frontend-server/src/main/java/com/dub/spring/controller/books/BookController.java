package com.dub.spring.controller.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dub.spring.controller.reviews.ReviewController;
import com.dub.spring.controller.reviews.ReviewWithAuthor;
import com.dub.spring.controller.utils.DisplayUtils;
import com.dub.spring.controller.utils.UserUtils;
import com.dub.spring.domain.Book;
import com.dub.spring.domain.Review;
import com.dub.spring.exceptions.BookNotFoundException;
import com.dub.spring.services.BookService;
import com.dub.spring.services.ReviewService;

@Controller
public class BookController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private DisplayUtils displayUtils;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserUtils userUtils;
	
	
	@RequestMapping("/sortBy")
	public ModelAndView getBookSortHelpful(
							@RequestParam("field") String sortBy,
							HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.setAttribute("sortBy", sortBy);
		
		String redirect = "redirect:/books/" + session.getAttribute("bookSlug");
		
		return new ModelAndView(redirect);
	}
	
	
	@RequestMapping("/books/{bookSlug}")
	public ModelAndView getBook(@PathVariable String bookSlug,
								HttpServletRequest request) {
			
		HttpSession session = request.getSession();
		session.setAttribute("bookSlug", bookSlug);
		
		Map<String, Object> params = new HashMap<>();
		
		try {
			Book book = bookService.getBookBySlug(bookSlug);

			Double price = book.getPrice()/100.0;
			params.put("book", book);
			params.put("price", price);
			
			List<Review> reviews = null;
			
			
			String sortBy = (String)session.getAttribute("sortBy");
			if (sortBy == null)  {// default
				sortBy = "rating";
			}
			// retrieve reviews	
			reviews = reviewService.getReviewsByBookId(book.getId(), sortBy);
		
			String userId = userUtils.getLoggedUser(session).getId();
				
			List<ReviewWithAuthor> reviewWithAuthors = displayUtils.getReviewWithAuthors(reviews, userId);
			
			Optional<Double> bookRating = reviewService.getBookRating(book.getId());
			
			if (bookRating.isPresent()) {
				params.put("rating", bookRating.get());
			} else {
				params.put("rating", null);
			}
			
			params.put("reviews", reviewWithAuthors);
			params.put("bookSlug", bookSlug);
			params.put("voteForm", new ReviewController.VoteForm());
			
			return new ModelAndView("books/book", params);
		} catch (BookNotFoundException e) {
			return new ModelAndView("bookNotFound", params);
			
		}
		
	}
}
