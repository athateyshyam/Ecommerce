package org.ecommerce.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import org.ecommerce.dto.CategoryDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.entity.ProductEntity;
import org.ecommerce.exceptions.ProductServiceException;
import org.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class ProductServiceImplTest {
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	static ProductDTO productDto;
	static CategoryDTO categoryDto;
	static ProductEntity productEntity;
	static ModelMapper mapper;
	static List<ProductEntity>productEntityList;
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

		productEntityList=new ArrayList<ProductEntity>();
		productEntity = new ProductEntity();
		mapper=new ModelMapper();
		productEntity = mapper.map(productDto, ProductEntity.class);
		productEntity.setId(1001L);
		productEntityList.add(productEntity);
	}

	@Test
	@Order(1)
	void testSave() {
		when(productRepository.findByProductHsn(productDto.getProductHsn())).thenReturn(null);
		when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
		ProductDTO dto = productServiceImpl.save(productDto);
		assertEquals(1001, dto.getId());
	}
	
	@Test
	@Order(3)
	void testSaveProductAlreadyExistInDatabase() {
		when(productRepository.findByProductHsn(productDto.getProductHsn())).thenReturn(productEntity);
		when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
		ProductDTO dto = productServiceImpl.save(productDto);
		assertEquals(100,dto.getProductQuantity());
	}

	@Test
	@Order(2)
	void testGetProductByName() {
		when(productRepository.findByProductName("HP-LAPTOP")).thenReturn(productEntity);
		ProductDTO dto= productServiceImpl.getProductByName("HP-LAPTOP");
		assertEquals("HP2526", dto.getProductHsn());
	}

	@Test
	@Order(7)
	void testGetProductByNameProductNotFound() {
		when(productRepository.findByProductName("HP-LAPTOP")).thenReturn(null);
		assertThrows(ProductServiceException.class, ()->productServiceImpl.getProductByName("HP-LAPTOP"));
	}
	
	@Test
	@Order(4)
	void testGetProductListContains() {
		when(productRepository.findByProductNameLike("%HP-%")).thenReturn(productEntityList);
		List<ProductDTO>productDtoList= productServiceImpl.getProductListContains("HP-");
		assertEquals(1, productDtoList.size());
	}

	@Test
	@Order(5)
	void testGetProductByNameAndQuantity() {
		when(productRepository.findByProductName("HP-LAPTOP")).thenReturn(productEntity);
		ProductDTO dto= productServiceImpl.getProductByNameAndQuantity("HP-LAPTOP", 1);
		assertEquals(1, dto.getProductQuantity());
	}
	
	@Test
	@Order(6)
	void testGetProductByNameAndQuantityExceedingQuantity() {
		when(productRepository.findByProductName("HP-LAPTOP")).thenReturn(productEntity);
		assertThrows(ProductServiceException.class, ()->productServiceImpl.getProductByNameAndQuantity("HP-LAPTOP", 200));
	}

}
