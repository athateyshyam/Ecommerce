package org.ecommerce.service;

import java.util.List;

import org.ecommerce.dto.ProductDTO;

public interface ProductService {
	public ProductDTO save(ProductDTO productDto);
	public ProductDTO getProductByName(String productName);
	public List<ProductDTO>getProductListContains(String pattern);
	public ProductDTO getProductByNameAndQuantity(String productName, int quantity);
}
