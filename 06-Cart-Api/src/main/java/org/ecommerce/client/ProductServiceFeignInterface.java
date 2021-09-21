package org.ecommerce.client;

import org.ecommerce.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("product-api/product")
public interface ProductServiceFeignInterface {
	@GetMapping(path = "/product/{productName}/quantity/{quantity}")
	public ResponseEntity<ProductDTO> getProductByNameAndQuantity(@PathVariable String productName,
			@PathVariable int quantity);
}
