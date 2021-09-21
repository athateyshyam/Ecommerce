package org.ecommerce.exceptions.errormessages;

public enum ErrorMessages {
	CART_IS_EMPTY("Your cart is empty. Please add atleast 1 product to place the order.");

	private String errorMessage;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
