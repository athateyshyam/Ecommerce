package org.ecommerce.client;

import java.util.List;

import org.ecommerce.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("search-api/search")
public interface SearchClientFeignInterface {
	@GetMapping(path = "/products/{pattern}")
	public ResponseEntity<List<ProductDTO>>getProductContains(@PathVariable String pattern);
}
