package org.finance.fundtransfer.service;

import java.util.List;

import org.finance.fundtransfer.dto.AccountDTO;
import org.finance.fundtransfer.dto.UserDTO;
import org.finance.fundtransfer.request.AmountRequest;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.finance.fundtransfer.request.UpdateUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService{
	public UserDTO addUser(UserDTO userDto);
	public List<UserDTO>findAllUsers();
	public AccountDTO transferAmount(String fromAccNo,String toAccNo,Double balance);
	/**
	 * @param request
	 * @return
	 */
	public AccountDTO depositeAmount(String username,AmountRequest request);
	/**
	 * @param request
	 * @return
	 */
	public AccountDTO withdrawAmount(String username,AmountRequest request);
	/**
	 * @param username
	 * @return
	 */
	public UserDTO getUserByUsername(String username);
	/**
	 * @param request
	 * @return
	 */
	public UserDTO updateUser(String username,UpdateUserRequest request);
	/**
	 * @param name
	 * @param request
	 * @return
	 */
	public AccountDTO transferAmount(String username, AmountTransferRequest request);
}
