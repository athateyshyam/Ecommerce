/**
 * 
 */
package org.finance.fundtransfer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.finance.fundtransfer.dto.AccountDTO;
import org.finance.fundtransfer.dto.BankDTO;
import org.finance.fundtransfer.dto.UserDTO;
import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.Address;
import org.finance.fundtransfer.request.AmountRequest;
import org.finance.fundtransfer.request.UserRegistrationRequest;
import org.finance.fundtransfer.response.UserRegistrationResponse;
import org.finance.fundtransfer.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

/**
 * @author Athatey Shyam
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest {

	@Mock
	private UserService service;	
	@InjectMocks
	private UserController userController;

	static UserDTO userDto;
	static Account account1;
	static UserRegistrationRequest userRegistrationRequest;
	static HttpServletRequest httpRequest;
	static AmountRequest amountRequest;
	static BankDTO bankDto;
	static Address address1;
	static List<Address> addressList;

	@BeforeAll
	public static void setUp() {
		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setFirstName("Alexander");
		userRegistrationRequest.setMiddleName("Alexandrovich");
		userRegistrationRequest.setLastName("Alekhine");
		userRegistrationRequest.setEmail("alexi@chess.com");
		userRegistrationRequest.setAadharNo("784521569541");
		userRegistrationRequest.setMobile("8895498745");
		userRegistrationRequest.setAge("55");
		userRegistrationRequest.setGender("Male");
		userRegistrationRequest.setPanNo("ALEX2564");

		address1 = new Address();
		address1.setId(1L);
		address1.setArea("Area-78");
		address1.setCity("Moscow");
		address1.setState("Central Russia");
		address1.setCountry("Russia");
		address1.setPin("556847");

		addressList = new ArrayList<Address>();
		addressList.add(address1);

		userRegistrationRequest.setListOfAddress(addressList);

		bankDto = new BankDTO();
		bankDto.setBankName("State Bank Of India");
		bankDto.setBranchCode("AMT26548");
		bankDto.setIfsc("SBIN17754");
		bankDto.setId(1L);

		account1 = new Account();
		account1.setAccNo("8448493");
		account1.setAccType("SAVING");
		account1.setBalance(50000d);

		userRegistrationRequest.setBankDto(bankDto);

		userDto = new UserDTO();
		BeanUtils.copyProperties(userRegistrationRequest, userDto);

		userDto.setAccount(account1);
		userDto.setUsername("ALEX2564");
		userDto.setPassword("password");

		amountRequest = new AmountRequest();
		amountRequest.setAccNo("8448493");
		amountRequest.setBalance(10000.00);
		
		httpRequest=Mockito.mock(HttpServletRequest.class);
	}

	@Test
	@DisplayName("User Registeration")
	@Order(1)
	void testRegisterUser() {
		when(service.addUser(any(UserDTO.class))).thenReturn(userDto);
		ResponseEntity<UserRegistrationResponse> response = userController.registerUser(userRegistrationRequest);
		assertEquals("ALEX2564", response.getBody().getUsername());
		assertEquals("8448493", response.getBody().getAccountNo());
	}

	/*
	@Test
	@DisplayName("Deposite amount ")
	@Order(2)
	void testDepositeAmount() {
		when(httpRequest.getUserPrincipal().getName()).thenReturn("ALEX2564");
		when(service.depositeAmount("ALEX2564", amountRequest));
		ResponseEntity<AccountDTO> response = userController.depositeAmount(httpRequest, amountRequest);
		assertEquals(60000.00, response.getBody().getBalance());
	}
*/
	@Test
	void testWithdrawAmount() {
		fail("Not yet implemented");
	}

	@Test
	void testTransferAmount() {
		fail("Not yet implemented");
	}

}
