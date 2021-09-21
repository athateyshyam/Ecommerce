/**
 * 
 */
package org.finance.fundtransfer.service.impl;

import java.util.List;

import org.finance.fundtransfer.dto.DispensaryAccountDTO;
import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.AccountTransaction;
import org.finance.fundtransfer.entity.DispensaryAccount;
import org.finance.fundtransfer.entity.UserEntity;
import org.finance.fundtransfer.exceptions.AccountServiceException;
import org.finance.fundtransfer.exceptions.UserServiceException;
import org.finance.fundtransfer.exceptions.errormessages.ErrorMessages;
import org.finance.fundtransfer.repository.AccountRepository;
import org.finance.fundtransfer.repository.DispensaryAccountRepository;
import org.finance.fundtransfer.repository.UserRepository;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.finance.fundtransfer.service.DispensaryAccountService;
import org.finance.fundtransfer.utils.FundUtility;
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
public class DispensaryAccountServiceImpl implements DispensaryAccountService {
	@Autowired
	private DispensaryAccountRepository repo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public DispensaryAccountDTO addDispensaryAccount(String username, DispensaryAccountDTO dto) {
		UserEntity user = userRepo.findByUsername(username);
		Account account = user.getAccount();
		if (!account.getAccNo().equals(dto.getSavingAccNo()))
			throw new AccountServiceException(
					"Account number: " + dto.getSavingAccNo() + " or Username: " + username + " is invalid.");
		List<DispensaryAccount> list = account.getListOfDispensary();

		DispensaryAccount entity = new DispensaryAccount();
		BeanUtils.copyProperties(dto, entity);
		list.add(entity);
		account.setListOfDispensary(list);
		accountRepo.save(account);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public String depositAmountInDispensary(String username, AmountTransferRequest request) {
		UserEntity user = userRepo.findByUsername(username);
		if (!user.getAccount().getAccNo().equals(request.getFromAccount()))
			throw new AccountServiceException(
					"User: " + username + " doesn't have account number: " + request.getFromAccount());
		boolean flag = false;
		DispensaryAccount dispensaryAccount = null;
		//Account account = accountRepo.findByAccNo(request.getFromAccount());
		Account account =user.getAccount();
		if (account == null)
			throw new AccountServiceException("Account not exist " + request.getFromAccount());
		List<DispensaryAccount> accountList = account.getListOfDispensary();
		if (accountList.isEmpty())
			throw new AccountServiceException("You dont have any Dispensary Account added yet.");
		for (DispensaryAccount obj : accountList) {
			if (obj.getDispensaryAccountNo().equals(request.getToAccount())) {
				flag = true;
				dispensaryAccount = obj;
				break;
			}
		}
		if (!flag)
			throw new AccountServiceException(
					"Dispensary Account Number: " + request.getToAccount() + " is not valid for user: " + username);
		if(account.getBalance()<request.getBalance())
			throw new AccountServiceException(ErrorMessages.INSUFFICIENT_FUND.getErrorMessage()+" : "+request.getBalance());
		account.setBalance(account.getBalance() - request.getBalance());
		dispensaryAccount
				.setDispensaryAccountBalance(dispensaryAccount.getDispensaryAccountBalance() + request.getBalance());
		accountList.add(dispensaryAccount);
		account.setListOfDispensary(accountList);
		String transactionId = new FundUtility().genrateTransactionId(10);
		AccountTransaction transaction = FundUtility.generateTransactionDetails(request.getFromAccount(),
				request.getToAccount(), request.getBalance(), "Dispensary", transactionId);
		List<AccountTransaction> transactionList = account.getAccountTransction();
		transactionList.add(transaction);
		account.setAccountTransction(transactionList);
		accountRepo.save(account);
		return "Successful added amount in dispensary";
	}

}
