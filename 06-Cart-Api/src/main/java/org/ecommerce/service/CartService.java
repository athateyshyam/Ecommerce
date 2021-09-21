package org.ecommerce.service;

import org.ecommerce.dto.CartDTO;

public interface CartService {
	public CartDTO saveCart(CartDTO cart);
	public boolean deleteCart(CartDTO cartDto);
	public CartDTO getCartByUsername(String username);
}
