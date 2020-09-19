package com.dub.spring.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dub.spring.domain.AddressOperations;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentOperations;
import com.dub.spring.domain.Primary;
import com.dub.spring.domain.ProfileOperations;
import com.dub.spring.exceptions.DuplicateUserException;
import com.dub.spring.exceptions.UserNotFoundException;
import com.dub.spring.services.UserService;

@RestController
public class UserRestEndpoint {
	
	@Autowired 
	private UserService userService;
	
	@Value("${baseUsersUrl}")
	String baseUsersURL;
	
	// should rather return a location
	@RequestMapping(
			value = "/createUser",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyUser> createUser(
								@RequestBody MyUser user) {	
		try {
			MyUser newUser = userService.createUser(user);			
			URI location = new URI(baseUsersURL + "/userByName/" + newUser.getUsername());			
			return ResponseEntity.created(location).body(null);
		} catch (DuplicateUserException e) {
			return new ResponseEntity<MyUser>(HttpStatus.CONFLICT);
		} catch (URISyntaxException e) {
			return new ResponseEntity<MyUser>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@RequestMapping(
			value = "/findById",
			method = RequestMethod.POST) 
	public ResponseEntity<MyUser> findUserById(
			@RequestBody String userId) {
		
		try {
			MyUser user = userService.findById(userId);
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		} 
		
	}
	
	@RequestMapping(
			value = "/deletePaymentMethod",
			method = RequestMethod.POST)
	public ResponseEntity<MyUser> deletePaymentMethod(
				@RequestBody PaymentOperations paymentDelete) {
			
		if (!paymentDelete.getOp().equals(ProfileOperations.DELETE)) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_ACCEPTABLE);
		}
			
		try {
			MyUser user = userService.deletePaymentMethod(
										paymentDelete.getUserId(), 
										paymentDelete.getPaymentMethod());
				
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}
			
	}
	
	@RequestMapping(
		value = "/deleteAddress",
		method = RequestMethod.POST)
	public ResponseEntity<MyUser> deleteAddress(
			@RequestBody AddressOperations addressDelete) {
		
		if (!addressDelete.getOp().equals(ProfileOperations.DELETE)) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		try {
			MyUser user = userService.deleteAddress(
									addressDelete.getUserId(), 
									addressDelete.getAddress());
			
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			value = "/addPaymentMethod",
			method = RequestMethod.POST)
	public ResponseEntity<MyUser> addPayment(
			@RequestBody PaymentOperations paymentAdd) {
		
		if (!paymentAdd.getOp().equals(ProfileOperations.ADD)) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		try {
			MyUser user = userService.addPayment(
								paymentAdd.getUserId(), 
								paymentAdd.getPaymentMethod());
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			value = "/addAddress",
			method = RequestMethod.POST)
	public ResponseEntity<MyUser> addAddress(
			@RequestBody AddressOperations addressAdd) {
			
		if (!addressAdd.getOp().equals(ProfileOperations.ADD)) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		try {
			MyUser user = userService.addAddress(
								addressAdd.getUserId(), 
								addressAdd.getAddress());
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			value = "/primaryPayment",
			method = RequestMethod.POST)
	public ResponseEntity<MyUser> setPrimaryPayment(
			@RequestBody Primary primary) {
		
		try {
			MyUser user = userService.findByUsername(primary.getUsername());
			user = userService.setPrimaryPayment(user.getId(), primary.getIndex());
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			value = "/primaryAddress",
			method = RequestMethod.POST)
	public ResponseEntity<MyUser> setPrimaryAddress(
			@RequestBody Primary primary) {
		
		try {
			MyUser user = userService.findByUsername(primary.getUsername());
			user = userService.setPrimaryAddress(user.getId(), primary.getIndex());
			return new ResponseEntity<MyUser>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/userByName/{username}")
	public ResponseEntity<MyUser> getUserByName(
			@PathVariable("username") String username) {
		
		MyUser user = userService.findByUsername(username);
		
		return ResponseEntity.ok(user);
	}

}
