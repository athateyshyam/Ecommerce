package org.ecommerce.exceptions;

public class CartServiceException extends RuntimeException {

	private static final long serialVersionUID = 1418056528497208684L;

	public CartServiceException(String message) {
		super(message);
	}

}
