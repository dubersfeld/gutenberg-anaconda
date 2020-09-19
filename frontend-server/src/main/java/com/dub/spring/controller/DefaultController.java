package com.dub.spring.controller;

import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dub.spring.controller.utils.UserUtils;
import com.dub.spring.domain.Book;
import com.dub.spring.domain.Category;
import com.dub.spring.domain.MyUser;
import com.dub.spring.services.BookService;
import com.dub.spring.services.CategoryService;
import com.dub.spring.services.UserService;

@Controller
public class DefaultController {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(DefaultController.class);
		
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserUtils userUtils;
	
	@RequestMapping({"/", "/backHome", "/index"})
    public String greeting(Model model, HttpServletRequest request) throws ParseException {
       		
		List<Category> categories = categoryService.getLeaveCategories();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String username = auth.getName();
		MyUser user = userService.findByUsername(username);
		String userId = user.getId();

		List<Book> booksToReview 
				= bookService.getBooksNotReviewed(userId, 5);
		
		model.addAttribute("categories", categories); 	
		model.addAttribute("booksToReview", booksToReview);	
		model.addAttribute("username", username);
			
		// attach user to current session
		HttpSession session = request.getSession();
		
		userUtils.setLoggedUser(session, user);
		
		// add a Set<String> to store invalid book Ids
		session.setAttribute("invalidBooks", new HashSet<String>());
		
        return "index";
    }
	

    @GetMapping("/login")
    public String login(Model model,
    		HttpServletRequest request) {
    
    	try {
    		
    		if(SecurityContextHolder.getContext().getAuthentication() instanceof
                UsernamePasswordAuthenticationToken) {
    			return "index";
    		} else {
    			
    		}
    	
    		Enumeration<String> params = request.getParameterNames();
    	
    		while (params.hasMoreElements()) {
    			if (params.nextElement().equals("loginFailed")) {
    				model.addAttribute("loginFailed", "loginFailed");
    			}
    		}
    	
    	} catch (NoSuchElementException e) {
    		logger.warn("Exception caught " + e);
    	}
    	return "login";
    }
    
    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();    	
    	if (auth != null) { 
            new SecurityContextLogoutHandler().logout(request, response, auth);
    	}

        return "redirect:/login?logout";
    }
}