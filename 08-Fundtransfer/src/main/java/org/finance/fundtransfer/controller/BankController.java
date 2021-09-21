/**
 * 
 */
package org.finance.fundtransfer.controller;

import org.finance.fundtransfer.dto.BankDTO;
import org.finance.fundtransfer.entity.BankEntity;
import org.finance.fundtransfer.request.BankDetailsRequest;
import org.finance.fundtransfer.response.BankDetailsResponse;
import org.finance.fundtransfer.service.BankDetailsService;
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
public class BankController {
	@Autowired
	private BankDetailsService service;

	@RequestMapping(value = "/bank", method = RequestMethod.POST)
	public ResponseEntity<BankDetailsResponse> addBankDetails(@RequestBody BankDetailsRequest request) {
		BankDetailsResponse response=null;
		if (request == null)
			return new ResponseEntity<BankDetailsResponse>(HttpStatus.BAD_REQUEST);
		BankDTO bank=new BankDTO();
		BeanUtils.copyProperties(request, bank);
		bank = service.addBankDetails(bank);
		response=new BankDetailsResponse();
		BeanUtils.copyProperties(bank, response);
		return new ResponseEntity<BankDetailsResponse>(response, HttpStatus.OK);
	}
}
