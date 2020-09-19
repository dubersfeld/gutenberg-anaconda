package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.exceptions.UserNotFoundException;
import com.dub.spring.services.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	private Address newAddress = new Address();
	private PaymentMethod newPayment = new PaymentMethod();
	
	private Address delAddress = new Address();
	private PaymentMethod delPayment = new PaymentMethod();
	
	
	@PostConstruct
	private void init() {
		newAddress.setCity("London");
		newAddress.setCountry("United Kingdom");
		newAddress.setStreet("10 Downing Street");
		newAddress.setZip("SW1A 2AA");
		
		delAddress.setCity("Paris");
		delAddress.setCountry("France");
		delAddress.setStreet("31 rue du Louvre");
		delAddress.setZip("75001");
		
		newPayment.setCardNumber("1111222233336666");
		newPayment.setName("Mark Zuckerberg");
		
		delPayment.setCardNumber("8888777744441111");
	    delPayment.setName("Jean Castex");
	}
	
	
	@Test
	void createUserTest() {
		List<Address> addresses = Arrays.asList(newAddress);
		List<PaymentMethod> payMeths = Arrays.asList(newPayment);
		
		MyUser user = new MyUser();
		user.setUsername("Boris");
		user.setHashedPassword("{bcrypt}$2a$10$Ip8KBSorI9R39m.KQBk3nu/WhjekgPSmfmpnmnf5yCL3aL9y.ITVW");
		user.setAddresses(addresses);
		user.setPaymentMethods(payMeths);
				
		// actual creation
		MyUser checkUser = this.userService.createUser(user);
		
		assertEquals("Boris", checkUser.getUsername());
	}
	
	@Test
	void testById() {
		String userId = "5a28f2b0acc04f7f2e9740ae";
		MyUser user = this.userService.findById(userId);
		
		assertEquals("Carol", user.getUsername());
	
	}
	
	@Test
	void testByIdNotFound() {
		String userId = "5a28f2b0acc04f7f2e976666";
		assertThrows(UserNotFoundException.class, () -> {
	    	 userService.findById(userId);
	    });
	}
	
	@Test
	void testByUsername() {
		String username = "Carol";
		MyUser user = this.userService.findByUsername(username);
	
		assertEquals("5a28f2b0acc04f7f2e9740ae", user.getId());
	}
	
	@Test
	void testByUsernameNotFound() {
		assertThrows(UserNotFoundException.class, () -> {
	    	 userService.findByUsername("Calvin");
	    });		
	}
	
	@Test
	void setPrimaryAddressTest() {
		String userId = "5a28f2b9acc04f7f2e9740b1";
		int index = 1;
		MyUser user = this.userService.setPrimaryAddress(userId, index);
		
		assertEquals(user.getMainShippingAddress(), 1);
	}
	
	@Test
	void setPrimaryPaymentTest() {
		String userId = "5a28f2b9acc04f7f2e9740b1";
		int index = 1;
		MyUser user = this.userService.setPrimaryPayment(userId, index);
		
		assertEquals(user.getMainPayMeth(), 1);
	}
	
	@Test
	void addAddressTest() {
		String userId = "5a28f2b0acc04f7f2e9740ae";
		// actual update
		MyUser user = this.userService.addAddress(userId, newAddress);
		
		assertTrue(UserRestEndpointTest.addressMatch(user.getAddresses(), newAddress));
	}
	
	@Test
	void addPaymentTest() {
		String userId = "5a28f2b2acc04f7f2e9740af";
		// actual update
		MyUser user = this.userService.addPayment(userId, newPayment);
		
		assertTrue(UserRestEndpointTest.paymentMatch(user.getPaymentMethods(), newPayment));
	}
	
	@Test
	void testDeleteAddress() {
		String userId = "5a28f2b2acc04f7f2e9740b0";
		// actual update
		MyUser user = this.userService.deleteAddress(userId, delAddress);
		assertFalse(UserRestEndpointTest.addressMatch(user.getAddresses(), delAddress));
	}
	
	@Test
	void testDeletePayment() {
		String userId = "5a28f2b2acc04f7f2e9740b0";
		// actual update
		MyUser user = this.userService.deletePaymentMethod(userId, delPayment);
		assertFalse(UserRestEndpointTest.paymentMatch(user.getPaymentMethods(), delPayment));
	}
	
}
