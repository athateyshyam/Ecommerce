package org.ecommerce.exceptions;

public class OrderServiceException extends RuntimeException {

	private static final long serialVersionUID = 1418056528497208684L;

	public OrderServiceException(String message) {
		super(message);
	}

}
