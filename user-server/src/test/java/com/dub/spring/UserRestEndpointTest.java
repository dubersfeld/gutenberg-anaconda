package com.dub.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dub.spring.domain.Address;
import com.dub.spring.domain.AddressOperations;
import com.dub.spring.domain.MyUser;
import com.dub.spring.domain.PaymentMethod;
import com.dub.spring.domain.PaymentOperations;
import com.dub.spring.domain.Primary;
import com.dub.spring.domain.ProfileOperations;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * try to clean this code by removing any code duplication
 * */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserRestEndpointTest {

	@Autowired
	public MockMvc mockMvc;
	
	private MyUser newUser = new MyUser();
	private MyUser conflictUser = new MyUser();
	
	private Address newAddress = new Address();
	private PaymentMethod newPayment = new PaymentMethod();
	
	private Address delAddress = new Address();
	private PaymentMethod delPayment = new PaymentMethod();
	
	@PostConstruct
	private void init() {
		newUser.setUsername("Nelson");
		newUser.setHashedPassword("{bcrypt}$2a$10$Ip8KBSorI9R39m.KQBk3nu/WhjekgPSmfmpnmnf5yCL3aL9y.ITVW");
		
		conflictUser.setUsername("Albert");
		conflictUser.setHashedPassword("{bcrypt}$2a$10$Ip8KBSorI9R39m.KQBk3nu/WhjekgPSmfmpnmnf5yCL3aL9y.ITVW");
	
		newAddress.setCity("London");
		newAddress.setCountry("United Kingdom");
		newAddress.setStreet("10 Downing Street");
		newAddress.setZip("SW1A 2AA");
		
		newPayment.setCardNumber("1111222255558888");
		newPayment.setName("Jeff Bezos");
		
		delAddress.setCity("Paris");
		delAddress.setCountry("FR");
		delAddress.setStreet("42 rue Amélie Nothomb");
		delAddress.setZip("75018");
		
		delPayment.setCardNumber("6789432167891234");
		delPayment.setName("Alice Carrol");
	}
	
	@Test
	void createUserTest() throws Exception {
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/createUser")
				.content(toJsonString(newUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				
				.andExpect(status().isCreated())
				.andReturn();
		*/
		
		MvcResult result = performPost("/createUser", toJsonString(newUser), status().isCreated());
		
		String location = result.getResponse().getHeader("location");		
		assertEquals(location, "http://localhost:8084/userByName/Nelson");	
	}	
	
	@Test
	void createUserConflictTest() throws Exception {
		
		/*
		mockMvc.perform(
				MockMvcRequestBuilders
				.post("/createUser")
				.content(toJsonString(conflictUser))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();
		*/
		performPost("/createUser", toJsonString(conflictUser), status().isConflict());
		
	}	
	
	@Test
	void setPrimaryAddressTest() throws Exception {
		Primary primary = new Primary();
		primary.setUsername("Alice");
		primary.setIndex(1);
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/primaryAddress")
				.content(toJsonString(primary))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
			*/
		
		MvcResult result = performPost("/primaryAddress", toJsonString(primary), status().isOk());

		
		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		
		assertEquals(checkUser.getMainShippingAddress(), 1);
		
	}
	
	@Test
	void setPrimaryPaymentTest() throws Exception {
		Primary primary = new Primary();
		primary.setUsername("Alice");
		primary.setIndex(1);
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/primaryPayment")
				.content(toJsonString(primary))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
			*/
		
		MvcResult result = performPost("/primaryPayment", toJsonString(primary), status().isOk());

		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
	
		assertEquals(checkUser.getMainPayMeth(), 1);
		
	}
	
	@Test
	void addAddressTest() throws Exception {
		AddressOperations addOp = new AddressOperations();
		addOp.setAddress(newAddress);
		addOp.setOp(ProfileOperations.ADD);
		addOp.setUserId("5a28f2b2acc04f7f2e9740af");// Albert
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/addAddress")
				.content(toJsonString(addOp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	*/
		
		MvcResult result = performPost("/addAddress", toJsonString(addOp), status().isOk());

		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		
		assertTrue(addressMatch(checkUser.getAddresses(), newAddress));
			
	}
	
	@Test
	void addPaymentMethodTest() throws Exception {
		PaymentOperations payMethOp = new PaymentOperations();
		payMethOp.setPaymentMethod(newPayment);
		payMethOp.setOp(ProfileOperations.ADD);
		payMethOp.setUserId("5a28f2b2acc04f7f2e9740af");// Albert
/*		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/addPaymentMethod")
				.content(toJsonString(payMethOp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	*/
		
		MvcResult result = performPost("/addPaymentMethod", toJsonString(payMethOp), status().isOk());

		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
			
		assertTrue(paymentMatch(checkUser.getPaymentMethods(), newPayment));	
	}
	
	@Test
	void deleteAddressTest() throws Exception {
		AddressOperations addOp = new AddressOperations();
		
		addOp.setAddress(delAddress);
		addOp.setOp(ProfileOperations.DELETE);
		addOp.setUserId("5a28f2b9acc04f7f2e9740b1");// Alice
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/deleteAddress")
				.content(toJsonString(addOp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	*/
		
		MvcResult result = performPost("/deleteAddress", toJsonString(addOp), status().isOk());

		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		
		assertFalse(addressMatch(checkUser.getAddresses(), delAddress));
	}
	
	@Test
	void deletePaymentTest() throws Exception {
		PaymentOperations payMethOp = new PaymentOperations();
		
		payMethOp.setPaymentMethod(delPayment);
		payMethOp.setOp(ProfileOperations.DELETE);
		payMethOp.setUserId("5a28f2b9acc04f7f2e9740b1");// Alice
	
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/deletePaymentMethod")
				.content(toJsonString(payMethOp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
*/	
		MvcResult result = performPost("/deletePaymentMethod", toJsonString(payMethOp), status().isOk());

		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		
		assertFalse(paymentMatch(checkUser.getPaymentMethods(), delPayment));
	}
	
	@Test
	void findByIdTest() throws Exception {
		String userId = "5a28f2b9acc04f7f2e9740b1";
		
		/*
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post("/findById")
				.content(userId)
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		*/
		MvcResult result = performPost("/findById", userId, status().isOk());

		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		assertEquals(checkUser.getUsername(), "Alice");
	}
	
	@Test
	void findByUsernameTest() throws Exception {
		String userId = "5a28f2b9acc04f7f2e9740b1";
		String username = "Alice";
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get("/userByName/" + username)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		MyUser checkUser = toUser(result.getResponse().getContentAsString(StandardCharsets.UTF_8));	
		
		
		assertEquals(checkUser.getId(), userId);
	}
	
	
	private MyUser toUser(String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			MyUser user = mapper.readValue(jsonStr, MyUser.class);
			return user;
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	private String toJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static boolean addressMatch(List<Address> addresses, Address address) {
		boolean match = false;
		for (Address add : addresses) {
			if (address.equals(add)) {
				match = true;
				break;
			}
			
		}
		return match;
	}
	
	public static boolean paymentMatch(List<PaymentMethod> payMeths, PaymentMethod payMeth) {
		boolean match = false;
		for (PaymentMethod pM : payMeths) {
				
			System.err.println(pM.getCardNumber().equals(payMeth.getCardNumber()));
			System.err.println(pM.getName().equals(payMeth.getName()));
			System.err.println(pM.equals(payMeth));
			
			if (payMeth.equals(pM)) {
				match = true;
				break;
			}
			
		}
		return match;
	}
	
	private MvcResult performPost(String uri, String content, ResultMatcher expectedStatus) throws Exception {
	
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(expectedStatus)
				.andReturn();
		
		return result;
	}
}
