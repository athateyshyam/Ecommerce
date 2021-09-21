/**
 * 
 */
package org.finance.fundtransfer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.finance.fundtransfer.dto.DispensaryAccountDTO;
import org.finance.fundtransfer.request.AmountTransferRequest;
import org.finance.fundtransfer.service.DispensaryAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

/**
 * @author Athatey Shyam
 *
 */
@RestController
public class DispensaryController {
	@Autowired
	private DispensaryAccountService service;

	@ApiImplicitParams({ @ApiImplicitParam(name = "authorization", value = "Bearer JWT token", paramType = "header") })
	@RequestMapping(value = "/dispensary", method = RequestMethod.POST)
	public ResponseEntity<String> addDispensaryAccount(HttpServletRequest request,
			@RequestBody DispensaryAccountDTO requestDto) {
		if (requestDto == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		// System.out.println(request.getUserPrincipal().getName());
		String username = request.getUserPrincipal().getName();
		requestDto = service.addDispensaryAccount(username, requestDto);
		return new ResponseEntity<String>("Successfully added dispensary", HttpStatus.OK);
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "authorization", value = "Bearer JWT token", paramType = "header") })
	@RequestMapping(value = "/deposite-dispensary", method = RequestMethod.POST)
	public ResponseEntity<String> depositAmountInDispensary(HttpServletRequest request, @RequestBody AmountTransferRequest amountTransferRequest) {

		String str = service.depositAmountInDispensary(request.getUserPrincipal().getName(),amountTransferRequest);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
}
