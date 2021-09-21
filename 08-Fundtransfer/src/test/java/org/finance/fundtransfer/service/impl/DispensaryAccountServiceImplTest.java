/**
 * 
 */
package org.finance.fundtransfer.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.finance.fundtransfer.dto.DispensaryAccountDTO;
import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.AccountTransaction;
import org.finance.fundtransfer.entity.DispensaryAccount;
import org.finance.fundtransfer.entity.UserEntity;
import org.finance.fundtransfer.repository.AccountRepository;
import org.finance.fundtransfer.repository.DispensaryAccountRepository;
import org.finance.fundtransfer.repository.UserRepository;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

/**
 * @author Athatey Shyam
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class DispensaryAccountServiceImplTest {

	@Mock
	private DispensaryAccountRepository repo;
	@Mock
	private UserRepository userRepo;
	@Mock
	private AccountRepository accountRepo;
	@InjectMocks
	private DispensaryAccountServiceImpl dispensaryAccountServiceImpl;
	
	static DispensaryAccountDTO dispensaryAccountDTO;
	static DispensaryAccount dispensaryAccount;
	static UserEntity userEntity;
	static Account account1;
	static AmountTransferRequest amountTransferRequest;
	static List<AccountTransaction>transactionList;
	static List<DispensaryAccount> dispensaryAccountList;
	
	@BeforeAll
	public static void setUp() {
		
		dispensaryAccountDTO=new DispensaryAccountDTO();
		dispensaryAccountDTO.setDispensaryAccountName("Henry Edward Bird");
		dispensaryAccountDTO.setDispensaryAccountNo("7584126");
		dispensaryAccountDTO.setDispensaryAccountBalance(1000.00);
		dispensaryAccountDTO.setSavingAccNo("8448493");
		
		dispensaryAccount=new DispensaryAccount();
		BeanUtils.copyProperties(dispensaryAccountDTO, dispensaryAccount);
		
		transactionList=new ArrayList<AccountTransaction>();
		
		dispensaryAccountList=new ArrayList<DispensaryAccount>();
		dispensaryAccountList.add(dispensaryAccount);
		
		account1=new Account();
		account1.setAccNo("8448493");
		account1.setAccType("SAVING");
		account1.setBalance(50000d);
		account1.setListOfDispensary(dispensaryAccountList);
		account1.setAccountTransction(transactionList);
		
		amountTransferRequest=new AmountTransferRequest();
		amountTransferRequest.setBalance(1000);
		amountTransferRequest.setFromAccount("8448493");
		amountTransferRequest.setToAccount("7584126");
		
		userEntity=new UserEntity();
		userEntity.setUsername("ALPA9865N");
		userEntity.setAccount(account1);

	}
	
	@Test
	@DisplayName("Add Dispensary to Account")
	@Order(1)
	void testAddDispensaryAccount() {
		when(userRepo.findByUsername("ALPA9865N")).thenReturn(userEntity);
		when(accountRepo.save(any(Account.class))).thenReturn(account1);
		DispensaryAccountDTO dto= dispensaryAccountServiceImpl.addDispensaryAccount("ALPA9865N",  dispensaryAccountDTO);
		assertEquals("7584126", dto.getDispensaryAccountNo());
	}

	
	@Test
	@DisplayName("Add Amount to Dispensary Account")
	@Order(2)
	void testDepositAmountInDispensary() {
		when(userRepo.findByUsername("ALPA9865N")).thenReturn(userEntity);
		when(accountRepo.save(any(Account.class))).thenReturn(account1);
		String str=dispensaryAccountServiceImpl.depositAmountInDispensary("ALPA9865N", amountTransferRequest);
		assertEquals("Successful added amount in dispensary", str);
	}

}
