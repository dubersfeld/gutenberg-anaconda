package com.dub.spring.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentMethod;

public interface UserService extends UserDetailsService {

	 @Override   
	 UserDetails loadUserByUsername(String username);// custom implementation

	 MyUser findById(String userId);
	 
	 MyUser findByUsername(String username);
	 
	 MyUser saveUser(MyUser user);

	 // more advanced methods
	 MyUser setPrimaryPaymentMethod(String username, int index);
	 MyUser setPrimaryAddress(String username, int index);
	 	 
	 void deleteAddress(String username, Address address);
	 void addAddress(String username, Address newAddress);
	 
	 void deletePaymentMethod(String username, PaymentMethod payMeth);
	 void addPaymentMethod(String username, PaymentMethod payMeth);
}
