/**
 * 
 */
package org.finance.fundtransfer.response;

import org.springframework.http.HttpStatus;

/**
 * @author Intel
 * @param <T>
 *
 */
public class GenericResponse<T> {
	private T data;
	private String message;
	HttpStatus status;

	public GenericResponse(T data, String message, HttpStatus status) {
		this.data = data;
		this.message = message;
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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

}
