package org.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ecommerce.client.ProductServiceFeignInterface;
import org.ecommerce.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class SearchControllerTest {
	@Mock
	private ProductServiceFeignInterface productServiceFeignInterface;
	@InjectMocks
	SearchController searchController;
	
	static List<ProductDTO>productDtoList;
	static ProductDTO productDto;
	static ResponseEntity<List<ProductDTO>>responseProductDtoList;
	
	@BeforeAll
	public static void setUp() {
		productDto = new ProductDTO();
		productDto.setProductName("HP-LAPTOP");
		productDto.setProductQuantity(50);
		productDto.setProductDescription("It is HP laptop");
		productDto.setProductHsn("HP2526");
		productDto.setProductPrice(40000.00);
		
		productDtoList=new ArrayList<ProductDTO>();
		productDtoList.add(productDto);
		
		responseProductDtoList=new ResponseEntity<List<ProductDTO>>(productDtoList,HttpStatus.OK);
	}

	@Test
	void testGetProductContains() {
		when(productServiceFeignInterface.getProductContains("HP-LAP")).thenReturn(responseProductDtoList);
		ResponseEntity<List<ProductDTO>>response=searchController.getProductContains("HP-LAP");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
