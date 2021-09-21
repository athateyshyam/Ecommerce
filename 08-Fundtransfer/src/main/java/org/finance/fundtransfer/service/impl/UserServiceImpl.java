/**
 * 
 */
package org.finance.fundtransfer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.finance.fundtransfer.dto.AccountDTO;
import org.finance.fundtransfer.dto.UserDTO;
import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.AccountTransaction;
import org.finance.fundtransfer.entity.Address;
import org.finance.fundtransfer.entity.BankEntity;
import org.finance.fundtransfer.entity.UserEntity;
import org.finance.fundtransfer.exceptions.UserServiceException;
import org.finance.fundtransfer.exceptions.errormessages.ErrorMessages;
import org.finance.fundtransfer.repository.AccountRepository;
import org.finance.fundtransfer.repository.AccountTransactionRepository;
import org.finance.fundtransfer.repository.BankDetailsRepository;
import org.finance.fundtransfer.repository.UserRepository;
import org.finance.fundtransfer.request.AmountRequest;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.finance.fundtransfer.request.UpdateUserRequest;
import org.finance.fundtransfer.service.UserService;
import org.finance.fundtransfer.utils.FundUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Athatey Shyam
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository repo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private BankDetailsRepository bankRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDTO addUser(UserDTO userDto) {
		if (userDto == null)
			throw new UserServiceException("Request object must not be null");
		UserDTO dto = null;
		UserEntity user = new UserEntity();
		if (repo.findByEmail(userDto.getEmail()) != null) {
			LOGGER.info("This {} email address is already in database", userDto.getEmail());
			throw new UserServiceException(
					ErrorMessages.EMAIL_ALREADY_EXISTS.getErrorMessage() + " " + userDto.getEmail());
		}
		if (repo.findByUsername(userDto.getPanNo()) != null) {
			LOGGER.info("This {} pancard number is already in database", userDto.getPanNo());
			throw new UserServiceException(
					ErrorMessages.PAN_NO_ALREADY_EXISTS.getErrorMessage() + " " + userDto.getPanNo());
		}
		BankEntity bankEntity = bankRepo.findByBranchCode(userDto.getBankDto().getBranchCode());
		if (bankEntity == null)
			throw new UserServiceException("Branch code not exists: " + userDto.getBankDto().getBranchCode());
		// Preparing User Object
		BeanUtils.copyProperties(userDto, user);
		ArrayList<Address> address = new ArrayList<Address>();
		for (Address addObj : userDto.getListOfAddress()) {
			addObj.setId(null);
			address.add(addObj);
		}
		user.setListOfAddress(address);
		// Setting Account Details For New User
		Account account = generateAccountDetailsForNewUser();
		List<Account> accountList = bankEntity.getListOfAccounts();
		accountList.add(account);
		user.setAccount(account);

		// Setting Username For New User
		user.setUsername(userDto.getPanNo());

		// Setting Password of length 8 characters For New User
		String password = new FundUtility().generatePassword(8);

		// LOGGER.info("Password genrated successfully before Encryption : "+password);
		// user.setPassword(password);
		user.setPassword(bCryptPasswordEncoder.encode(password));

		// Save user to database
		UserEntity entity = repo.save(user);
		// Update Bank with new User Account
		bankRepo.save(bankEntity);
		LOGGER.info("Successfully created new user entity and saved into database.");
		dto = new UserDTO();

		// Preparing UserDTO Object
		BeanUtils.copyProperties(entity, dto);
		dto.setPassword(password);
		return dto;
	}

	@Override
	public List<UserDTO> findAllUsers() {
		List<UserDTO> listOfUserDto = new ArrayList<UserDTO>();
		for (UserEntity user : repo.findAll()) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(user, userDto);
			listOfUserDto.add(userDto);
		}
		return listOfUserDto;
	}

	private Account generateAccountDetailsForNewUser() {
		Account account = new Account();
		FundUtility utility = new FundUtility();
		account.setAccNo(utility.generateAccountNo(7));
		account.setAccType("SAVING");
		account.setBalance(0d);
		return account;
	}

	@Override
	public AccountDTO transferAmount(String fromAccNo, String toAccNo, Double balance) {
		Account fromAccount;
		Account toAccount = accountRepo.findByAccNo(toAccNo);
		if (toAccount == null) {
			throw new UserServiceException(ErrorMessages.ACCOUNT_NOT_EXISTS.getErrorMessage() + " : " + toAccNo);
		}
		fromAccount = accountRepo.findByAccNo(fromAccNo);
		if (fromAccount == null)
			throw new UserServiceException(ErrorMessages.ACCOUNT_NOT_EXISTS.getErrorMessage() + " : " + fromAccNo);
		if (fromAccount.getBalance() < balance)
			throw new UserServiceException(ErrorMessages.INSUFFICIENT_FUND.getErrorMessage());
		fromAccount.setBalance(fromAccount.getBalance() - balance);
		List<AccountTransaction> list = fromAccount.getAccountTransction();
		String transactionId = new FundUtility().genrateTransactionId(10);
		list.add(FundUtility.generateTransactionDetails(fromAccNo, toAccNo, balance, "Debited", transactionId));
		fromAccount.setAccountTransction(list);

		fromAccount = accountRepo.save(fromAccount);
		AccountDTO dto = new AccountDTO();
		BeanUtils.copyProperties(fromAccount, dto);
		toAccount.setBalance(toAccount.getBalance() + balance);
		List<AccountTransaction> list1 = toAccount.getAccountTransction();
		list1.add(FundUtility.generateTransactionDetails(fromAccNo, toAccNo, balance, "Credited", transactionId));
		toAccount.setAccountTransction(list1);
		accountRepo.save(toAccount);
		return dto;
	}

	@Override
	public AccountDTO depositeAmount(String username, AmountRequest request) {
		UserEntity user = repo.findByUsername(username);
		if (!user.getAccount().getAccNo().equals(request.getAccNo()))
			throw new UserServiceException(
					"User: " + username + " not have account with number: " + request.getAccNo());
		//Account account = accountRepo.findByAccNo(request.getAccNo());
		Account account = user.getAccount();
		if (account == null)
			throw new UserServiceException(
					ErrorMessages.ACCOUNT_NOT_EXISTS.getErrorMessage() + " " + request.getAccNo());
		double balance = account.getBalance() + request.getBalance();
		account.setBalance(balance);
		String transactionId = new FundUtility().genrateTransactionId(10);
		List<AccountTransaction> list = account.getAccountTransction();
		list.add(FundUtility.generateTransactionDetails(request.getAccNo(), "", request.getBalance(), "Deposite",
				transactionId));
		account.setAccountTransction(list);
		accountRepo.save(account);
		AccountDTO dto = new AccountDTO();
		BeanUtils.copyProperties(account, dto);
		return dto;
	}

	@Override
	public AccountDTO withdrawAmount(String username,AmountRequest request) {
		UserEntity user = repo.findByUsername(username);
		if (!user.getAccount().getAccNo().equals(request.getAccNo()))
			throw new UserServiceException(
					"User: " + username + " not have account with number: " + request.getAccNo());
		//Account account = accountRepo.findByAccNo(request.getAccNo());
		Account account = user.getAccount();
		if (account == null) {
			LOGGER.info("This {} account number is not exists", request.getAccNo());
			throw new UserServiceException(
					ErrorMessages.ACCOUNT_NOT_EXISTS.getErrorMessage() + " " + request.getAccNo());
		}
		if (account.getBalance() < request.getBalance()) {
			LOGGER.info("Withdraw amount is greater than actual amount present in account");
			throw new UserServiceException(ErrorMessages.INSUFFICIENT_FUND.getErrorMessage());
		}
		double balance = account.getBalance() - request.getBalance();
		account.setBalance(balance);
		String transactionId = new FundUtility().genrateTransactionId(10);
		List<AccountTransaction> list = account.getAccountTransction();
		list.add(FundUtility.generateTransactionDetails(request.getAccNo(), "", request.getBalance(), "Withdraw",
				transactionId));
		account.setAccountTransction(list);
		accountRepo.save(account);
		AccountDTO dto = new AccountDTO();
		BeanUtils.copyProperties(account, dto);
		return dto;
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		UserDTO dto = new UserDTO();
		UserEntity user = repo.findByUsername(username);
		if (user == null)
			throw new UserServiceException(ErrorMessages.USERNAME_NOT_FOUND.getErrorMessage() + " " + username);
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	@Override
	public UserDTO updateUser(String username, UpdateUserRequest request) {
		UserEntity user = repo.findByUsername(username);
		if (user == null)
			throw new UserServiceException(ErrorMessages.USERNAME_NOT_FOUND.getErrorMessage() + " " + username);
		BeanUtils.copyProperties(request, user);
		user = repo.save(user);
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = repo.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.getErrorMessage() + " : " + username);
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public AccountDTO transferAmount(String username, AmountTransferRequest request) {
		UserEntity user = repo.findByUsername(username);
		if (!user.getAccount().getAccNo().equals(request.getFromAccount()))
			throw new UserServiceException(
					"User: " + username + " not have account with number: " + request.getFromAccount());
		Account account=user.getAccount();
		if(account.getBalance()<request.getBalance())
			throw new UserServiceException(ErrorMessages.INSUFFICIENT_FUND.getErrorMessage()+": "+request.getBalance());
		account.setBalance(account.getBalance()-request.getBalance());
		String transactionId=new FundUtility().genrateTransactionId(10);
		AccountTransaction transaction=FundUtility.generateTransactionDetails(request.getFromAccount(), request.getToAccount(), request.getBalance(), "Debited", transactionId);
		List<AccountTransaction>transactionList=account.getAccountTransction();
		transactionList.add(transaction);
		account.setAccountTransction(transactionList);
		account=accountRepo.save(account);
		AccountDTO dto=new AccountDTO();
		BeanUtils.copyProperties(account, dto);
		account=accountRepo.findByAccNo(request.getToAccount());
		if(account==null)
			throw new UserServiceException("Account not exists: "+request.getToAccount());
		account.setBalance(account.getBalance()+request.getBalance());
		accountRepo.save(account);
		return dto;
	}
}
