package org.ecommerce.exceptions.errormessages;

public enum ErrorMessages {
	CART_IS_EMPTY("Your cart is empty."),
	PRODUCT_NOT_FOUND("Product not found in inventory.");

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
