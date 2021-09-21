package org.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.ecommerce.dto.ProductDTO;
import org.ecommerce.entity.ProductEntity;
import org.ecommerce.exceptions.ProductServiceException;
import org.ecommerce.exceptions.errormessages.ErrorMessages;
import org.ecommerce.repository.ProductRepository;
import org.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDTO save(ProductDTO productDto) {
		ProductEntity productEntity = productRepository.findByProductHsn(productDto.getProductHsn());
		ModelMapper mapper = new ModelMapper();
		if (productEntity == null) {
			ProductEntity product = mapper.map(productDto, ProductEntity.class);
			product = productRepository.save(product);
			productDto = mapper.map(product, ProductDTO.class);
			return productDto;
		}
		Integer quantity = productEntity.getProductQuantity() + productDto.getProductQuantity();
		productEntity.setProductQuantity(quantity);
		productEntity = productRepository.save(productEntity);
		productDto = mapper.map(productEntity, ProductDTO.class);
		return productDto;
	}

	@Override
	public ProductDTO getProductByName(String productName) {
		ProductEntity product = productRepository.findByProductName(productName);
		if (product == null)
			throw new ProductServiceException(ErrorMessages.PRODUCT_NOT_FOUND.getErrorMessage());
		return new ModelMapper().map(product, ProductDTO.class);
	}

	@Override
	public List<ProductDTO> getProductListContains(String pattern) {
		List<ProductEntity> productList = productRepository.findByProductNameLike("%" + pattern + "%");
		if (productList.isEmpty())
			return new ArrayList<ProductDTO>();
		List<ProductDTO> productDtoList = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for (ProductEntity product : productList) {
			ProductDTO dto = mapper.map(product, ProductDTO.class);
			productDtoList.add(dto);
		}
		return productDtoList;
	}

	@Override
	public ProductDTO getProductByNameAndQuantity(String productName, int quantity) {
		ProductEntity product = productRepository.findByProductName(productName);
		if (product == null)
			throw new ProductServiceException(ErrorMessages.PRODUCT_NOT_FOUND.getErrorMessage());
		else if (product.getProductQuantity() < quantity || quantity < 1)
			throw new ProductServiceException(ErrorMessages.INSUFFICIENT_QUANTITY.getErrorMessage());
		ProductDTO productDto = new ModelMapper().map(product, ProductDTO.class);
		product.setProductQuantity(product.getProductQuantity() - quantity);
		productDto.setProductQuantity(quantity);
		return productDto;
	}

}
