package org.ecommerce.entity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cart_tbl")
public class CartEntity implements Serializable {

	private static final long serialVersionUID = 3569109012382261847L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private double total;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_item")
	private List<ProductEntity> cartList;

	public CartEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ProductEntity> getCartList() {
		return cartList;
	}

	public void setCartList(List<ProductEntity> cartList) {
		this.cartList = cartList;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
