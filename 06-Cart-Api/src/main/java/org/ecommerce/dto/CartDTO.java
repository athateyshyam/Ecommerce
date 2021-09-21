package org.ecommerce.dto;

import java.io.Serializable;
import java.util.concurrent.ConcurrentMap;

public class CartDTO implements Serializable {

	private static final long serialVersionUID = -3941585738647658946L;
	private static CartDTO cart=null;
	private double total;
	private String username;
	private ConcurrentMap<String, ProductDTO> cartList;
	
	private CartDTO() {}
	
	public ConcurrentMap<String, ProductDTO> getCartList() {
		return cartList;
	}
	public void setCartList(ConcurrentMap<String, ProductDTO> cartList) {
		this.cartList = cartList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public static CartDTO getCart() {
		if(cart==null)
			cart=new CartDTO();
		return cart;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	

	
}
