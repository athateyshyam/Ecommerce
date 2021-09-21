package org.ecommerce.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 7014520683265782049L;
	private Long id;
	private String productName;
	private String productHsn;
	private Double productPrice;
	private String productDescription;
	private Integer productQuantity;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductHsn() {
		return productHsn;
	}

	public void setProductHsn(String productHsn) {
		this.productHsn = productHsn;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}
}