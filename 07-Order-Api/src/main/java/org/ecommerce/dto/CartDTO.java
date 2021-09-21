package org.ecommerce.dto;

import java.io.Serializable;
import java.util.concurrent.ConcurrentMap;

public class CartDTO implements Serializable {

	private static final long serialVersionUID = -3941585738647658946L;
	private String username;
	private ConcurrentMap<String, ProductDTO> cartList;
	private double total;
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
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
