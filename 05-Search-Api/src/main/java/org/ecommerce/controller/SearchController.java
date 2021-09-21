package org.ecommerce.controller;

import java.util.List;

import org.ecommerce.client.ProductServiceFeignInterface;
import org.ecommerce.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
	@Autowired
	private ProductServiceFeignInterface productServiceFeignInterface;
	
	@GetMapping(path = "/products/{pattern}")
	public ResponseEntity<List<ProductDTO>>getProductContains(@PathVariable String pattern){
		return productServiceFeignInterface.getProductContains(pattern);
	}
}
