package org.ecommerce.exceptions;

public class SearchServiceException extends RuntimeException {

	private static final long serialVersionUID = 1418056528497208684L;

	public SearchServiceException(String message) {
		super(message);
	}

}
