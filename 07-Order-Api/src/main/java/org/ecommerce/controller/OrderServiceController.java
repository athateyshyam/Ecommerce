package org.ecommerce.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ecommerce.client.CartServiceFeignInterface;
import org.ecommerce.dto.AmountTransferRequest;
import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;
import org.ecommerce.dto.UserBankLoginRequest;
import org.ecommerce.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderServiceController {
	@Autowired
	private CartServiceFeignInterface cartServiceFeignInterface;
	@Autowired
	private OrderService orderService;

	@PostMapping(path = "/place-order")
	public ResponseEntity<OrderDTO> placeOrder(@RequestBody PaymentDTO paymentDto) {
		CartDTO cart = cartServiceFeignInterface.getCart("emptycart").getBody();
		OrderDTO orderDto = orderService.placeOrder(cart,paymentDto);
		if(orderDto.getPaymentStatus()!=null) {
			cartServiceFeignInterface.deleteCartByUsername(orderDto.getUsername());
			cartServiceFeignInterface.cleanCart(true);
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}

	@GetMapping("/get-order-details/{orderId}")
	public ResponseEntity<OrderDTO> getOrderByOrderId(@PathVariable String orderId) {
		OrderDTO orderDto = orderService.getOrderByOrderId(orderId);
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}
	
	@GetMapping("/my-order-history/{username}/{fromDate}/to/{toDate}")
	public ResponseEntity<List<OrderDTO>> getOrderHistory(@PathVariable String username,@PathVariable String fromDate,@PathVariable String toDate)throws ParseException{
		List<OrderDTO> orderHistory=orderService.getOrderHistory(username, fromDate, toDate);
		return new ResponseEntity<>(orderHistory,HttpStatus.OK);
	}
}
