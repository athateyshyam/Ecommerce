/**
 * 
 */
package org.finance.fundtransfer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.finance.fundtransfer.dto.AccountDTO;
import org.finance.fundtransfer.dto.UserDTO;
import org.finance.fundtransfer.request.AmountRequest;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.finance.fundtransfer.request.UserRegistrationRequest;
import org.finance.fundtransfer.response.UserRegistrationResponse;
import org.finance.fundtransfer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * @author Athatey Shyam
 *
 */
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService service;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
		if (request == null)
			return new ResponseEntity<UserRegistrationResponse>(HttpStatus.BAD_REQUEST);

		UserRegistrationResponse response = new UserRegistrationResponse();
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(request, userDto);
		UserDTO createdUser = service.addUser(userDto);
		BeanUtils.copyProperties(createdUser, response);
		response.setAccountNo(createdUser.getAccount().getAccNo());
		return new ResponseEntity<UserRegistrationResponse>(response, HttpStatus.OK);
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "authorization", value = "Bearer JWT token", paramType = "header") })
	@RequestMapping(value = "/account-deposite", method = RequestMethod.PUT)
	public ResponseEntity<AccountDTO> depositeAmount(HttpServletRequest http, @RequestBody AmountRequest request) {
		AccountDTO dto = service.depositeAmount(http.getUserPrincipal().getName(), request);
		return new ResponseEntity<AccountDTO>(dto, HttpStatus.OK);
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "authorization", value = "Bearer JWT token", paramType = "header") })
	@RequestMapping(value = "/account-withdraw", method = RequestMethod.PUT)
	public ResponseEntity<AccountDTO> withdrawAmount(HttpServletRequest http, @RequestBody AmountRequest request) {
		AccountDTO dto = service.withdrawAmount(http.getUserPrincipal().getName(), request);
		return new ResponseEntity<AccountDTO>(dto, HttpStatus.OK);
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "authorization", value = "Bearer JWT token", paramType = "header") })
	@RequestMapping(value = "/transfer-amount", method = RequestMethod.POST)
	public ResponseEntity<AccountDTO> transferAmount(HttpServletRequest http,
			@RequestBody AmountTransferRequest request) {
		AccountDTO dto = service.transferAmount(http.getUserPrincipal().getName(), request);
		return new ResponseEntity<AccountDTO>(dto, HttpStatus.OK);
	}
}
