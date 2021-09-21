package org.ecommerce.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ecommerce.client.ProductServiceFeignInterface;
import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.exceptions.CartServiceException;
import org.ecommerce.exceptions.errormessages.ErrorMessages;
import org.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductServiceFeignInterface productServiceFeignInterface;

	private ConcurrentMap<String, ProductDTO> map = new ConcurrentHashMap<>();
	private CartDTO cart = null;

	@GetMapping(path = "/product/{productName}/quantity/{quantity}/{username}")
	public ResponseEntity<CartDTO> addToCart(@PathVariable String productName, @PathVariable Integer quantity,
			@PathVariable String username) {
		ResponseEntity<ProductDTO> response = productServiceFeignInterface.getProductByNameAndQuantity(productName,
				quantity);
		ProductDTO productDto = response.getBody();
		if (productDto == null)
			throw new CartServiceException(ErrorMessages.PRODUCT_NOT_FOUND.getErrorMessage());
		if (cart == null)
			cart = cartService.getCartByUsername(username);
		if (cart != null) {
			map = cart.getCartList();
		}

		if (cart == null)
			cart = CartDTO.getCart();

		if (!map.isEmpty()) {
			boolean flag = true;
			for (Map.Entry<String, ProductDTO> entry : map.entrySet()) {
				if (entry.getKey().equals(productDto.getProductHsn())) {
					flag = false;
					ProductDTO dto = entry.getValue();
					dto.setProductQuantity(dto.getProductQuantity() + quantity);
					map.put(dto.getProductHsn(), dto);
				}
			}

			if (flag)
				map.put(productDto.getProductHsn(), productDto);
		} else {
			map.put(productDto.getProductHsn(), productDto);
		}

		if (cart == null)
			cart = CartDTO.getCart();
		if (cart.getUsername() == null || cart.getUsername().equals("emptycart"))
			cart.setUsername(username);

		cart.setCartList(map);

		double total = 0;
		if (!cart.getCartList().isEmpty()) {
			for (Map.Entry<String, ProductDTO> entry : cart.getCartList().entrySet()) {
				ProductDTO cartProduct = entry.getValue();
				total = total + (cartProduct.getProductQuantity() * cartProduct.getProductPrice());
			}
		}
		cart.setTotal(total);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping(path = "/save-cart/{username}")
	public ResponseEntity<String> saveCart(@PathVariable String username) {
		CartDTO currentCart = getCart(username).getBody();
		if (currentCart.getCartList().isEmpty())
			return new ResponseEntity<>("Your cart is empty.", HttpStatus.BAD_REQUEST);
		cartService.saveCart(currentCart);
		return new ResponseEntity<>("Cart saved successfully", HttpStatus.OK);
	}

	@GetMapping(path = "/get-cart/{username}")
	public ResponseEntity<CartDTO> getCart(@PathVariable String username) {
		if (cart == null)
			cart = cartService.getCartByUsername(username);
		if (cart == null)
			cart = CartDTO.getCart();

		if (cart.getUsername() == null || cart.getUsername().equals("emptycart")) {
			cart.setUsername(username);
			cart.setCartList(map);
		}
		if (!map.isEmpty())
			cart.setCartList(map);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PostMapping(path = "/clean-cart")
	public void cleanCart(@RequestBody boolean flag) {
		if (flag) {
			map = new ConcurrentHashMap<>();
			cart.setCartList(map);
		}
	}

	@DeleteMapping("/delete/{username}")
	public boolean deleteCartByUsername(@PathVariable String username) {
		cart = cartService.getCartByUsername(username);
		return cartService.deleteCart(cart);
	}
}
