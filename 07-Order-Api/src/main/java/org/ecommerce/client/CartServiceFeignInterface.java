package org.ecommerce.client;

import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("cart-api/cart")
public interface CartServiceFeignInterface {
	@GetMapping(path = "/get-cart/{username}")
	public ResponseEntity<CartDTO> getCart(@PathVariable String username);
	
	@PostMapping(path = "/clean-cart")
	public ResponseEntity<OrderDTO> cleanCart(@RequestBody boolean flag);
	
	@DeleteMapping("/delete/{username}")
	public void deleteCartByUsername(@PathVariable String username);
}
