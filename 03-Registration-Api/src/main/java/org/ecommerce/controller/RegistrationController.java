package org.ecommerce.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ecommerce.client.CartFeignInterface;
import org.ecommerce.client.OrderFeignClientInterface;
import org.ecommerce.client.SearchClientFeignInterface;
import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.dto.UserDTO;
import org.ecommerce.request.UserRegistrationRequest;
import org.ecommerce.response.GenericResponse;
import org.ecommerce.response.UserRegistrationResponse;
import org.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {
	@Autowired
	private UserService userService;
	@Autowired
	private SearchClientFeignInterface searchClientFeignInterface;
	@Autowired
	private CartFeignInterface cartFeignInterface;
	@Autowired
	private OrderFeignClientInterface orderFeignClientInterface;
	@SuppressWarnings("rawtypes")
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	

	@PostMapping(path = "/register", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest request) {
		ModelMapper mapper = new ModelMapper();
		UserDTO userDto = mapper.map(request, UserDTO.class);
		userDto = userService.registerUser(userDto);
		UserRegistrationResponse response = mapper.map(userDto, UserRegistrationResponse.class);
		return new ResponseEntity<>(response, null, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/products/{pattern}")
	public ResponseEntity<GenericResponse<List<ProductDTO>>> getProductContains(@PathVariable String pattern) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		
		return circuitBreaker.run(() ->{ 
			GenericResponse<List<ProductDTO>>response=new GenericResponse<>();
			List<ProductDTO>productDtoList=searchClientFeignInterface.getProductContains(pattern).getBody();
			if(productDtoList.isEmpty()) {
				response.setData(productDtoList);
				response.setMessage("No Product found in database");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setData(productDtoList);
			response.setMessage("Products match with enter pattern "+pattern);
			return new ResponseEntity<>(response, HttpStatus.OK);
			}, throwable -> getDefaultInfo());
	}
	
	@GetMapping(path = "/get-cart")
	public ResponseEntity<CartDTO> getCart(Principal principal) {
		String username = principal.getName();
		return cartFeignInterface.getCart(username);
	}

	@GetMapping(path = "/add-to-cart/{productName}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addToCart(@PathVariable String productName, @PathVariable Integer quantity,
			Principal principal) {
		return cartFeignInterface.addToCart(productName, quantity, principal.getName());
	}

	@PostMapping(path = "/place-order")
	public ResponseEntity<OrderDTO> placeOrder(@RequestBody PaymentDTO paymentDto) {
		return orderFeignClientInterface.placeOrder(paymentDto);
	}
	
	@GetMapping("/get-order-details/{orderId}")
	public ResponseEntity<OrderDTO> getOrderByOrderId(@PathVariable String orderId){
		return orderFeignClientInterface.getOrderByOrderId(orderId);
	}
	
	@GetMapping(path = "/save-cart")
	public ResponseEntity<String>saveCart(Principal principal){
		return cartFeignInterface.saveCart(principal.getName());
	}
	
	@GetMapping("/my-order-history/{fromDate}/to/{toDate}")
	public ResponseEntity<List<OrderDTO>> getOrderHistory(Principal principal, @PathVariable String fromDate,@PathVariable String toDate)throws ParseException{
		List<OrderDTO>orderHistoryList=orderFeignClientInterface.getOrderHistory(principal.getName(), fromDate, toDate).getBody();
		return new ResponseEntity<>(orderHistoryList,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity getDefaultInfo() {
		return new ResponseEntity<>("Service not found",null,HttpStatus.NOT_FOUND);
	}
}
