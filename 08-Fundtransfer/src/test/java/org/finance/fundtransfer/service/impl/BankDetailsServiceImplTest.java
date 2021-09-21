/**
 * 
 */
package org.finance.fundtransfer.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.finance.fundtransfer.dto.BankDTO;
import org.finance.fundtransfer.entity.BankEntity;
import org.finance.fundtransfer.repository.BankDetailsRepository;
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
class BankDetailsServiceImplTest {
	@Mock
	private BankDetailsRepository bankDetailsRepository;
	@InjectMocks
	private BankDetailsServiceImpl bankDetailsServiceImpl;
	
	static BankDTO bankDto;
	static BankEntity bankEntity;
	@BeforeAll
	public static void setUp() {
		bankDto = new BankDTO();
		bankDto.setBankName("State Bank Of India");
		bankDto.setBranchCode("AMT65872");
		bankDto.setIfsc("SBIN17745");
		bankEntity=new BankEntity();
		BeanUtils.copyProperties(bankDto, bankEntity);
	}

	@Test
	@DisplayName("Add Bank ")
	@Order(1)
	void testAddBankDetails() {
		when(bankDetailsRepository.save(any(BankEntity.class))).thenAnswer(i->{
			bankEntity.setId(1L);
			return bankEntity;
		});
		
		BankDTO dto= bankDetailsServiceImpl.addBankDetails(bankDto);
		assertEquals(1L, dto.getId());
	}

	
	@Test
	@DisplayName("Get bank from branch code")
	@Order(2)
	void testGetBankDetailsByBranchCode() {
		when(bankDetailsRepository.findByBranchCode("AMT65872")).thenReturn(bankEntity);
		BankDTO dto= bankDetailsServiceImpl.getBankDetailsByBranchCode("AMT65872");
		assertEquals("SBIN17745", dto.getIfsc());
	}

	
	@Test
	@DisplayName("Get bank from name and branch code")
	@Order(3)
	void testGetBankDetailsByIfscCode() {
		when(bankDetailsRepository.findByIfsc("SBIN17745")).thenReturn(bankEntity);
		BankDTO dto= bankDetailsServiceImpl.getBankDetailsByIfscCode("SBIN17745");
		assertEquals("AMT65872", dto.getBranchCode());
	}

	
	@Test
	@DisplayName("Get bank from IFSC code")
	@Order(4)
	void testGetBankDetailsByBankNameAndBranchCode() {
		when(bankDetailsRepository.findByBankNameAndBranchCode("State Bank Of India", "AMT65872")).thenReturn(bankEntity);
		BankDTO dto= bankDetailsServiceImpl.getBankDetailsByBankNameAndBranchCode("State Bank Of India", "AMT65872");
		assertEquals("AMT65872", dto.getBranchCode());
	}

}
