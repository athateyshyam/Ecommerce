package org.ecommerce.exceptions;

public class ProductServiceException extends RuntimeException {

	private static final long serialVersionUID = 1418056528497208684L;

	public ProductServiceException(String message) {
		super(message);
	}

}
