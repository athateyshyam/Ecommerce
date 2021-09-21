package org.finance.fundtransfer.exceptions.errormessages;

public enum ErrorMessages {
	EMAIL_ALREADY_EXISTS("Email already exists."),
	PAN_NO_ALREADY_EXISTS("PAN Number already exists."),
	INSUFFICIENT_FUND("Insufficient fund balance"),
	ACCOUNT_NOT_EXISTS("Account not exists"),
	USERNAME_NOT_FOUND("Username not found in database"),
	AGE_BEYOND_LIMIT("Age must greater than 0 and less than 150 Yrs");

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
