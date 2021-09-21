package org.ecommerce.client;

import org.ecommerce.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("order-api/order")
public interface OrderServiceFeignInterface {
	@GetMapping(path="/place-order")
	public ResponseEntity<OrderDTO> placeOrder();
}
