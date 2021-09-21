package org.ecommerce.exceptions;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 1418056528497208684L;

	public UserServiceException(String message) {
		super(message);
	}

}
