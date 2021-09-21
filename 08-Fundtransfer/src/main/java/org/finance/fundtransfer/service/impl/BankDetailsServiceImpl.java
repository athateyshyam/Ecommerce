/**
 * 
 */
package org.finance.fundtransfer.service.impl;

import org.finance.fundtransfer.dto.BankDTO;
import org.finance.fundtransfer.entity.BankEntity;
import org.finance.fundtransfer.repository.BankDetailsRepository;
import org.finance.fundtransfer.service.BankDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Athatey Shyam
 *
 */
@Service
@Transactional
public class BankDetailsServiceImpl implements BankDetailsService {
	@Autowired
	private BankDetailsRepository bankDetailsRepository;

	@Override
	public BankDTO addBankDetails(BankDTO bank) {
		BankEntity entity = new BankEntity();
		BeanUtils.copyProperties(bank, entity);
		entity = bankDetailsRepository.save(entity);
		BeanUtils.copyProperties(entity, bank);
		return bank;
	}

	@Override
	public BankDTO getBankDetailsByBranchCode(String branchCode) {
		BankEntity entity = bankDetailsRepository.findByBranchCode(branchCode);
		BankDTO dto = new BankDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public BankDTO getBankDetailsByIfscCode(String ifsc) {
		BankEntity entity = bankDetailsRepository.findByIfsc(ifsc);
		BankDTO dto = new BankDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public BankDTO getBankDetailsByBankNameAndBranchCode(String bankName, String branchCode) {
		BankEntity entity = bankDetailsRepository.findByBankNameAndBranchCode(bankName, branchCode);
		BankDTO dto = new BankDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
