/**
 * 
 */
package org.finance.fundtransfer.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.finance.fundtransfer.dto.AccountDTO;
import org.finance.fundtransfer.dto.BankDTO;
import org.finance.fundtransfer.dto.UserDTO;
import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.AccountTransaction;
import org.finance.fundtransfer.entity.Address;
import org.finance.fundtransfer.entity.BankEntity;
import org.finance.fundtransfer.entity.UserEntity;
import org.finance.fundtransfer.repository.AccountRepository;
import org.finance.fundtransfer.repository.BankDetailsRepository;
import org.finance.fundtransfer.repository.UserRepository;
import org.finance.fundtransfer.request.AmountRequest;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceImplTest {
	@Mock
	UserRepository userRepository;
	@Mock
	AccountRepository accountRepository;
	@Mock
	BankDetailsRepository bankRepository;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@InjectMocks
	UserServiceImpl userServiceImpl;

	static UserDTO userDto;
	static UserDTO userDto1 = null;
	static UserEntity userEntity;
	static Account account1;
	static Account account2;
	static BankEntity bankEntity;
	static BankDTO bankDto;
	static AmountRequest request;
	static AmountTransferRequest transferRequest;
	static List<UserEntity>listUsers;
	static List<Account>accountList;
	static List<AccountTransaction>transactionList;

	@BeforeAll
	public static void setUp() {
		
		userDto = new UserDTO();
		userDto.setFirstName("Alexander");
		userDto.setMiddleName("Alexandrovich");
		userDto.setLastName("Alekhine");
		userDto.setEmail("alekhine@chess.com");
		userDto.setPanNo("ALPA9865N");
		userDto.setAadharNo("698547824163");
		userDto.setMobile("6354789541");
		userDto.setAge("53");
		userDto.setGender("Male");
		Address address = new Address();
		address.setArea("Central");
		address.setCity("Moscow");
		address.setState("Russia");
		address.setCountry("Soviet Russia");
		address.setPin("101000");
		List<Address> listOfAddress = new ArrayList<Address>();
		listOfAddress.add(address);
		userDto.setListOfAddress(listOfAddress);

		transactionList=new ArrayList<AccountTransaction>();
		
		account1=new Account();
		account1.setAccNo("8448493");
		account1.setAccType("SAVING");
		account1.setBalance(50000d);
		account1.setAccountTransction(transactionList);
		
		account2=new Account();
		account2.setAccNo("8447845");
		account2.setAccType("SAVING");
		account2.setBalance(250000d);
		account2.setAccountTransction(transactionList);
		
		accountList=new ArrayList<Account>();
		accountList.add(account1);
		accountList.add(account2);
		
		bankEntity=new BankEntity();
		bankEntity.setBankName("State Bank Of India");
		bankEntity.setBranchCode("AMT23654");
		bankEntity.setIfsc("SBIN17745");
		bankEntity.setListOfAccounts(accountList);
		
		bankDto=new BankDTO();
		
		BeanUtils.copyProperties(bankEntity, bankDto);
		userDto.setBankDto(bankDto);
		
		request=new AmountRequest();
		request.setAccNo("8448493");
		request.setBalance(10000.00);
		
		transferRequest=new AmountTransferRequest();
		transferRequest.setBalance(1000);
		transferRequest.setFromAccount("8448493");
		transferRequest.setToAccount("8447845");
		
		userEntity = new UserEntity();
		userEntity.setFirstName("Alexander");
		userEntity.setMiddleName("Alexandrovich");
		userEntity.setLastName("Alekhine");
		userEntity.setEmail("alekhine@chess.com");
		userEntity.setPanNo("ALPA9865N");
		userEntity.setAadharNo("698547824163");
		userEntity.setMobile("6354789541");
		userEntity.setAge("53");
		userEntity.setGender("Male");
		userEntity.setUsername("ALPA9865N");
		userEntity.setAccount(account1);
		userEntity.setListOfAddress(listOfAddress);
		
		listUsers=new ArrayList<UserEntity>();
		listUsers.add(userEntity);
	}
	
	@Test
	@DisplayName("Save UserDetails")
	@Order(1)
	public void addUserTest() {
		System.out.println("Inside Add User Method:-");
		when(bankRepository.findByBranchCode("AMT23654")).thenReturn(bankEntity);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		when(bankRepository.save(any(BankEntity.class))).thenReturn(bankEntity);
		
		UserDTO dto=userServiceImpl.addUser(userDto);
		System.out.println("After save user");
		assertNotNull(dto);
	}
	
	@Test
	@DisplayName("Deposite Amount")
	@Order(2)
	public void depositeAmount() {
		when(userRepository.findByUsername("ALPA9865N")).thenReturn(userEntity);
		when(accountRepository.save(any(Account.class))).thenReturn(account1);
		AccountDTO dto=userServiceImpl.depositeAmount("ALPA9865N", request);
		assertEquals(60000.00, dto.getBalance());
	}
	
	@Test
	@DisplayName("Withdrawn Amount")
	@Order(3)
	public void withdrawAmount() {
		when(userRepository.findByUsername("ALPA9865N")).thenReturn(userEntity);
		when(accountRepository.save(any(Account.class))).thenReturn(account1);
		AccountDTO dto=userServiceImpl.withdrawAmount("ALPA9865N", request);
		assertEquals(50000.00, dto.getBalance());
	}
	
	@Test
	@DisplayName("Transfer Amount")
	@Order(4)
	public void transferAmount(){
		when(userRepository.findByUsername("ALPA9865N")).thenReturn(userEntity);
		when(accountRepository.save(any(Account.class))).thenReturn(account1);
		when(accountRepository.findByAccNo("8447845")).thenReturn(account2);
		AccountDTO dto=userServiceImpl.transferAmount("ALPA9865N", transferRequest);
		assertEquals(49000.00, dto.getBalance());
	}
	
}
