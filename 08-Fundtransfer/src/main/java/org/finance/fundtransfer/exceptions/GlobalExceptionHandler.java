package org.finance.fundtransfer.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.finance.fundtransfer.response.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, new Date(),
				ex.getBindingResult().getFieldError().getDefaultMessage(), errors);
		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<Object> handleEmployeeServiceException(UserServiceException ex, WebRequest request) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());
		ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, new Date(), ex.getMessage(), errors);
		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { AccountServiceException.class })
	public ResponseEntity<Object> handleAccountServiceException(AccountServiceException ex, WebRequest request) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());
		ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, new Date(), ex.getMessage(), errors);
		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		ArrayList<String> errors = new ArrayList<String>();

		if (ex instanceof ConstraintViolationException) {
			((ConstraintViolationException) ex).getConstraintViolations().forEach(cV -> errors.add(cV.getPropertyPath()
					+ " : " + cV.getMessageTemplate() + "=> " + cV.getInvalidValue()));
		} else {
			errors.add(ex.getMessage());
		}

		ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, new Date(), "", errors);
		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}


}