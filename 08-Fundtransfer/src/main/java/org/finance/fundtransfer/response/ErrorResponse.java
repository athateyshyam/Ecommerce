package org.finance.fundtransfer.response;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private HttpStatus status;
	private Date timeStamp;
	private String message;
	private List<String> errors;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(Date timeStamp, String message) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
	}

	public ErrorResponse(HttpStatus status, List<String> errors) {
		super();
		this.status = status;
		this.errors = errors;
	}

	public ErrorResponse(HttpStatus status, Date timeStamp, String message, List<String> errors) {
		super();
		this.status = status;
		this.timeStamp = timeStamp;
		this.message = message;
		this.errors = errors;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
