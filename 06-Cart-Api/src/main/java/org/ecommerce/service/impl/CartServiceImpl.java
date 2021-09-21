package org.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.entity.CartEntity;
import org.ecommerce.entity.ProductEntity;
import org.ecommerce.exceptions.CartServiceException;
import org.ecommerce.exceptions.errormessages.ErrorMessages;
import org.ecommerce.repository.CartRepository;
import org.ecommerce.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;

	@Override
	public CartDTO saveCart(CartDTO cartDto) {
		if (cartDto.getCartList().isEmpty())
			throw new CartServiceException(ErrorMessages.CART_IS_EMPTY.getErrorMessage());
		CartEntity entity=cartRepository.findByUsername(cartDto.getUsername());
		if(entity!=null)
			cartRepository.delete(entity);
		ModelMapper mapper = new ModelMapper();
		List<ProductEntity> productList = new ArrayList<>();
		CartEntity cartEntity = mapper.map(cartDto, CartEntity.class);
		
		cartDto.getCartList().entrySet().stream().forEach(e->{
			ProductEntity product = mapper.map(e.getValue(), ProductEntity.class);
			product.setId(null);
			productList.add(product);
		});
		
		cartEntity.setCartList(productList);
		cartEntity = cartRepository.save(cartEntity);
		cartDto = mapper.map(cartEntity, CartDTO.class);
		return cartDto;
	}
	
	@Override
	public boolean deleteCart(CartDTO cartDto) {
		CartEntity cartEntity = cartRepository.findByUsername(cartDto.getUsername());
		if (cartEntity != null) {
			cartRepository.delete(cartEntity);
			return true;
		}
		return false;
	}

	@Override
	public CartDTO getCartByUsername(String username) {
		ModelMapper mapper = new ModelMapper();
		ConcurrentHashMap<String,ProductDTO>productList=new ConcurrentHashMap<>();
		CartEntity cartEntity = cartRepository.findByUsername(username);
		if (cartEntity == null)
			return null;
		CartDTO cartDto=mapper.map(cartEntity, CartDTO.class);
		for(ProductEntity product:cartEntity.getCartList()) {
			ProductDTO productDto=mapper.map(product, ProductDTO.class);
			productList.put(productDto.getProductHsn(),productDto);
		}
		cartDto.setCartList(productList);
		return cartDto;
	}

}
