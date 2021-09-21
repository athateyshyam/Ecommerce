package org.ecommerce.client;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("order-api/order")
public interface OrderFeignClientInterface {
	@PostMapping(path = "/place-order")
	public ResponseEntity<OrderDTO> placeOrder(@RequestBody PaymentDTO paymentDto);
	
	@GetMapping("/get-order-details/{orderId}")
	public ResponseEntity<OrderDTO> getOrderByOrderId(@PathVariable String orderId);
	
	@GetMapping("/my-order-history/{username}/{fromDate}/to/{toDate}")
	public ResponseEntity<List<OrderDTO>> getOrderHistory(@PathVariable String username,@PathVariable String fromDate,@PathVariable String toDate)throws ParseException;
}
