package com.dub.spring.services;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentMethod;

public interface UserService {

	 MyUser findById(String userId);// not override
		
	 MyUser findByUsername(String username);// not override
	 
	 MyUser setPrimaryAddress(String userId, int index);
		
	 MyUser setPrimaryPayment(String userId, int index);
	 
	 MyUser addAddress(String userId, Address newAddress);
	 
	 MyUser addPayment(String userId, PaymentMethod newPayment);
		
	 MyUser deleteAddress(String userId, Address delAddress);
	 
	 MyUser deletePaymentMethod(String userId, PaymentMethod payMeth);
	 
	 MyUser createUser(MyUser user);
	 
}
