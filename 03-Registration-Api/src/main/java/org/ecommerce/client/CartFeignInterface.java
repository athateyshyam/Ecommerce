package org.ecommerce.client;

import org.ecommerce.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cart-api/cart")
public interface CartFeignInterface {
	@GetMapping(path = "/product/{productName}/quantity/{quantity}/{username}")
	public ResponseEntity<CartDTO> addToCart(@PathVariable String productName, @PathVariable Integer quantity,
			@PathVariable String username);
	
	@GetMapping(path = "/get-cart/{username}")
	public ResponseEntity<CartDTO> getCart(@PathVariable String username);
	
	@GetMapping(path = "/save-cart/{username}")
	public ResponseEntity<String>saveCart(@PathVariable String username);
}
