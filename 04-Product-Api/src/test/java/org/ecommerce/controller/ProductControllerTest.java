package org.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ecommerce.dto.CategoryDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.service.ProductService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class ProductControllerTest {
	@Mock
	private ProductService productService;
	@InjectMocks
	ProductController productController;
	static ProductDTO productDto;
	static CategoryDTO categoryDto;
	static List<ProductDTO> productDtoList;

	@BeforeAll
	public static void setUp() {
		categoryDto = new CategoryDTO();
		categoryDto.setCategoryName("Electronics");
		productDto = new ProductDTO();
		productDto.setProductName("HP-LAPTOP");
		productDto.setProductQuantity(50);
		productDto.setProductDescription("It is HP laptop");
		productDto.setProductHsn("HP2526");
		productDto.setProductPrice(40000.00);
		productDto.setCategory(categoryDto);
		
		productDtoList=new ArrayList<ProductDTO>();
		productDtoList.add(productDto);
	}

	@Test
	void testSaveProduct() {
		when(productService.save(productDto)).thenReturn(productDto);
		ResponseEntity<ProductDTO>response=productController.saveProduct(productDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetProductContains() {
		when(productService.getProductListContains("HP-")).thenReturn(productDtoList);
		ResponseEntity<List<ProductDTO>>response=productController.getProductContains("HP-");
		assertEquals(1, response.getBody().size());
	}

	@Test
	void testGetProductByNameAndQuantity() {
		when(productService.getProductByNameAndQuantity("HP-LAPTOP", 50)).thenReturn(productDto);
		ResponseEntity<ProductDTO>response=productController.getProductByNameAndQuantity("HP-LAPTOP", 50);
		assertEquals(50, response.getBody().getProductQuantity());
		
	}

}
