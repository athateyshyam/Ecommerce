package org.ecommerce.exceptions.errormessages;

public enum ErrorMessages {
	PRODUCT_NOT_FOUND("Product not found in inventory."), INSUFFICIENT_QUANTITY("Insufficient product quantity.");

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
