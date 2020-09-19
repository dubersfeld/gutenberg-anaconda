package com.dub.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.exceptions.DuplicateUserException;
import com.dub.spring.exceptions.UserNotFoundException;
import com.dub.spring.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired private 
	UserRepository userRepository;
	
	@Autowired 
	private MongoOperations mongoOperations;

	@Override
	public MyUser findByUsername(String username) {
	
		MyUser user = userRepository.findByUsername(username);
		
		if (user != null) {
			return user;
		} else {
			throw new UserNotFoundException();
		}
		
	}

	@Override
	public MyUser setPrimaryAddress(String userId, int index) {
		
		Optional<MyUser> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			MyUser myUser = user.get();
			myUser.setMainShippingAddress(index);
			return userRepository.save(myUser);
		} else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public MyUser setPrimaryPayment(String userId, int index) {
		Optional<MyUser> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			MyUser myUser = user.get();
			myUser.setMainPayMeth(index);
			return userRepository.save(myUser);
		} else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public MyUser addAddress(String userId, Address newAddress) {
		Optional<MyUser> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			MyUser myUser = user.get();
			myUser.getAddresses().add(newAddress);
			return userRepository.save(myUser);
		} else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public MyUser addPayment(String userId, PaymentMethod newPayment) {
		Optional<MyUser> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			MyUser myUser = user.get();
			myUser.getPaymentMethods().add(newPayment);
			return userRepository.save(myUser);
		} else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public MyUser deleteAddress(String userId, Address delAddress) {
		
		try {
			Query query = new Query();
			Update update = new Update();
			query.addCriteria(Criteria.where("_id").is(userId));
			update.pull("addresses", delAddress);
			MyUser user = mongoOperations.findAndModify(
											query, 
											update, 
											new FindAndModifyOptions()
													.returnNew(true),
											MyUser.class);
			return user;
		} catch (Exception e) {
			throw new UserNotFoundException(); 
		}
	}

	@Override
	public MyUser deletePaymentMethod(String userId, PaymentMethod payMeth) {
		
		try {
			Query query = new Query();
			Update update = new Update();
			query.addCriteria(Criteria.where("_id").is(userId));
			update.pull("paymentMethods", payMeth);
			MyUser user = mongoOperations.findAndModify(
											query, 
											update, 
											new FindAndModifyOptions()
													.returnNew(true),
											MyUser.class);
			return user;
		} catch (Exception e) {
			throw new UserNotFoundException(); 
		}
	}

	@Override
	public MyUser findById(String userId) {
		
		Optional<MyUser> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserNotFoundException();
		}
	}

	@Override
	public MyUser createUser(MyUser user) {
		// check if username already present
		MyUser check = userRepository.findByUsername(user.getUsername());
		
		if (check == null) {
			// actual creation
			return userRepository.save(user);
		} else {
			throw new DuplicateUserException();
		}
		
	}

}
