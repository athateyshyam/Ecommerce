package org.ecommerce.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.entity.CartEntity;
import org.ecommerce.entity.ProductEntity;
import org.ecommerce.repository.CartRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class CartServiceImplTset {
	@Mock
	private CartRepository cartRepository;
	@InjectMocks
	private CartServiceImpl cartServiceImpl;
	
	static ModelMapper mapper;
	static CartDTO cartDto;
	static ConcurrentMap<String, ProductDTO> cartproductList;
	static ProductDTO productDto;
	static ProductEntity productEntity;
	static CartEntity cartEntity;
	static List<ProductEntity> productList;
	
	@BeforeAll
	public static void setUp() {
		mapper=new ModelMapper();
		
		productDto=new ProductDTO();
		productDto.setProductName("HP-NOTEBOOK");
		productDto.setProductQuantity(5);
		productDto.setProductPrice(40000d);
		productDto.setProductHsn("HP2546");
		productDto.setProductDescription("Hp Laptop");
		
		cartDto=CartDTO.getCart();
		cartDto.setUsername("virat@cricket.com");
		cartproductList=new ConcurrentHashMap<>();
		cartproductList.put("HP2546", productDto);
		
		cartDto.setCartList(cartproductList);
		productList=new ArrayList<ProductEntity>();
		
		productEntity=new ProductEntity();
		productEntity=mapper.map(productDto, ProductEntity.class);
		productList.add(productEntity);
		cartEntity=new CartEntity();
		cartEntity.setUsername("virat@cricket.com");
		cartEntity.setId(1l);
		cartEntity.setCartList(productList);
		
	}

	@Test
	void testSaveCart() {
		when(cartRepository.findByUsername(cartDto.getUsername())).thenReturn(null);
		when(cartRepository.save(any(CartEntity.class))).thenReturn(cartEntity);
		CartDTO dto=cartServiceImpl.saveCart(cartDto);
		assertEquals("virat@cricket.com", dto.getUsername());
	}

	@Test
	void testDeleteCart() {
		when(cartRepository.findByUsername(cartDto.getUsername())).thenReturn(cartEntity);
		assertTrue(cartServiceImpl.deleteCart(cartDto));
	}
	
	@Test
	void testDeleteCartIfCartNotInDatabase() {
		when(cartRepository.findByUsername(cartDto.getUsername())).thenReturn(null);
		assertFalse(cartServiceImpl.deleteCart(cartDto));
	}

	@Test
	void testGetCartByUsername() {
		when(cartRepository.findByUsername("virat@cricket.com")).thenReturn(cartEntity);
		CartDTO dto=cartServiceImpl.getCartByUsername("virat@cricket.com");
		assertEquals("virat@cricket.com", dto.getUsername());
	}

}
