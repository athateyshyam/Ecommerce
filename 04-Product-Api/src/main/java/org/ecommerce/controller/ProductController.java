package org.ecommerce.controller;

import java.util.List;

import org.ecommerce.dto.ProductDTO;
import org.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping(path = "/products", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDto) {
		productDto = productService.save(productDto);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
	
	@GetMapping(path = "/products/{pattern}")
	public ResponseEntity<List<ProductDTO>>getProductContains(@PathVariable String pattern){
		List<ProductDTO> productDtoList = productService.getProductListContains(pattern);
		return new ResponseEntity<>(productDtoList,HttpStatus.OK);
	}
	
	@GetMapping(path = "/product/{productName}/quantity/{quantity}")
	public ResponseEntity<ProductDTO>getProductByNameAndQuantity(@PathVariable String productName,@PathVariable int quantity){
		ProductDTO productDto=productService.getProductByNameAndQuantity(productName,quantity);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
}
