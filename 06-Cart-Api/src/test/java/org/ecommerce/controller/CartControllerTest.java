package org.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ecommerce.client.ProductServiceFeignInterface;
import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.service.CartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
class CartControllerTest {
	@Mock
	private ProductServiceFeignInterface productServiceFeignInterface;
	@Mock
	private CartService cartService;
	@InjectMocks
	private CartController cartController;
	
	static ConcurrentMap<String, ProductDTO> map;
	static CartDTO newCart = null;
	static CartDTO persistedCart;
	static ProductDTO productDto;
	static ResponseEntity<ProductDTO> productServiceResponse;
	
	@BeforeAll
	public static void setUp() {
		map = new ConcurrentHashMap<>();
		
		productDto=new ProductDTO();
		productDto.setProductName("HP-NOTEBOOK");
		productDto.setProductQuantity(5);
		productDto.setProductPrice(40000d);
		productDto.setProductHsn("HP2546");
		productDto.setProductDescription("Hp Laptop");
		
		productServiceResponse=new ResponseEntity<ProductDTO>(productDto,HttpStatus.OK);
		
		newCart=CartDTO.getCart();
		newCart.setCartList(new ConcurrentHashMap<>());
		persistedCart=CartDTO.getCart();
		persistedCart.setUsername("virat@cricket.com");
		map.put("HP2546",productDto);
		persistedCart.setCartList(map);
				
	}

	@Test
	void testAddToCart() {
		when(productServiceFeignInterface.getProductByNameAndQuantity("HP-NOTEBOOK", 5)).thenReturn(productServiceResponse);
		when(cartService.getCartByUsername("virat@cricket.com")).thenReturn(null);
		ResponseEntity<CartDTO>cartDtoResponse=cartController.addToCart("HP-NOTEBOOK", 5, "virat@cricket.com");
		CartDTO cartDto=cartDtoResponse.getBody();
		assertEquals("virat@cricket.com",cartDto.getUsername());
	}

	
	@Test
	void testSaveCart() {
		when(cartService.getCartByUsername("virat@cricket.com")).thenReturn(persistedCart);
		ResponseEntity<String>response=cartController.saveCart("virat@cricket.com");
		assertEquals("Cart saved successfully", response.getBody());
	}
	
	@Test
	void testGetCart() {
		when(cartService.getCartByUsername("virat@cricket.com")).thenReturn(null);
		ResponseEntity<CartDTO>response=cartController.getCart("virat@cricket.com");
		assertEquals("virat@cricket.com", response.getBody().getUsername());
	}

	@Test
	void testDeleteCartByUsername() {
		when(cartService.getCartByUsername("virat@cricket.com")).thenReturn(persistedCart);
		when(cartService.deleteCart(persistedCart)).thenReturn(true);
		assertTrue(cartController.deleteCartByUsername("virat@cricket.com"));
	}

}
