package com.dub.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import com.dub.spring.controller.config.ServiceConfig;
import com.dub.spring.domain.Address;
import com.dub.spring.domain.AddressOperation;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.domain.PaymentOperation;
import com.dub.spring.domain.Primary;
import com.dub.spring.domain.ProfileOperations;
import com.dub.spring.domain.UserPrincipal;
import com.dub.spring.exceptions.DuplicateUserException;
import com.dub.spring.exceptions.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RestOperations restTemplate;
	
	@Autowired
	ServiceConfig serviceConfig;
	
	private static final Logger logger = 
			LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) {
			
		
		MyUser user = this.findByUsername(username);
		
        UserPrincipal principal = new UserPrincipal(user);
         	
        return principal;   
    }

	@Override
	public MyUser findByUsername(String username) {
		logger.warn("User reloaded from DB");
			
		String usersURI = serviceConfig.getBaseUsersURL() + "/userByName/" + username;
				
		
		ResponseEntity<MyUser> response 	
		= restTemplate.getForEntity(usersURI, MyUser.class);
	
		MyUser user = response.getBody();
		
		return user;
	}

	@Override
	public MyUser setPrimaryPaymentMethod(String username, int index) {
		
		String usersURI = serviceConfig.getBaseUsersURL() + "/primaryPayment";
			 
		Primary primary = new Primary();
		
		primary.setIndex(index);
		primary.setUsername(username);
		
		try {
			
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 404) {
				throw new UserNotFoundException(); 
			} else {
				throw new RuntimeException(); 
			}
		}
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, primary, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new UserNotFoundException(); 
		}
	}
	
	@Override
	public MyUser setPrimaryAddress(String username, int index) {
	
		String usersURI = serviceConfig.getBaseUsersURL() + "/primaryAddress";
			 
		Primary primary = new Primary();
		
		primary.setIndex(index);
		primary.setUsername(username);
		
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, primary, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new UserNotFoundException(); 
		}
		
	}

	@Override
	public MyUser findById(String userId) {
				
		String usersURI = serviceConfig.getBaseUsersURL() + "/findById";
					 
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, userId, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new UserNotFoundException(); 
		}
	}
	
	@Override
	public void addAddress(String username, Address newAddress) {
			
		String usersURI = serviceConfig.getBaseUsersURL() + "/addAddress";
	 
		AddressOperation addOp = new AddressOperation();
		
		addOp.setUserId(this.findByUsername(username).getId());
		addOp.setAddress(newAddress);
		addOp.setOp(ProfileOperations.ADD);
		
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, addOp, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return;
		} else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new UserNotFoundException(); 
		} else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			throw new RuntimeException(); 
		}
	}

	@Override
	public void deleteAddress(String username, Address address) {
		
		String usersURI = serviceConfig.getBaseUsersURL() + "/deleteAddress";
	
	
		 
		AddressOperation addOp = new AddressOperation();
		
		addOp.setUserId(this.findByUsername(username).getId());
		addOp.setAddress(address);
		addOp.setOp(ProfileOperations.DELETE);
					
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, addOp, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return;
		} else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new UserNotFoundException(); 
		} else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			throw new RuntimeException(); 
		}
	}

	@Override
	public void deletePaymentMethod(String username, PaymentMethod payMeth) {
		
		String usersURI = serviceConfig.getBaseUsersURL() + "/deletePaymentMethod";
		 
		PaymentOperation deleteOp = new PaymentOperation();
		
		deleteOp.setUserId(this.findByUsername(username).getId());
		deleteOp.setPaymentMethod(payMeth);
		deleteOp.setOp(ProfileOperations.DELETE);
			
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, deleteOp, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return;
		} else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new UserNotFoundException(); 
		} else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			throw new RuntimeException(); 
		}
	}

	@Override
	public void addPaymentMethod(String username, PaymentMethod newPayMeth) {
		
		String usersURI = serviceConfig.getBaseUsersURL() + "/addPaymentMethod";
		 
		PaymentOperation addOp = new PaymentOperation();
		
		addOp.setUserId(this.findByUsername(username).getId());
		addOp.setPaymentMethod(newPayMeth);
		addOp.setOp(ProfileOperations.ADD);
					
		ResponseEntity<MyUser> response 
		= restTemplate.postForEntity(usersURI, addOp, MyUser.class);
	
		if (response.getStatusCode() == HttpStatus.OK) {
			return;
		} else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new UserNotFoundException(); 
		} else {
			throw new RuntimeException(); 
		}
	}

	@Override
	public MyUser saveUser(MyUser user) {
			
		String usersURI = serviceConfig.getBaseUsersURL() + "/createUser";
						
		try {
			ResponseEntity<MyUser> response 
			= restTemplate.postForEntity(usersURI, user, MyUser.class);
		
			return response.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("Exception caught " + e.getRawStatusCode());
			if (e.getRawStatusCode() == 409) {
				throw new DuplicateUserException();
			} else {
				throw new RuntimeException();
			}	
		}
	}
}
