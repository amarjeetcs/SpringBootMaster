package com.springboot.master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<String> handleEmailAlreadyExistException(EmailAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
	}

	@ExceptionHandler(NumberAlreadyExistsException.class)
	public ResponseEntity<String> handleEmailAlreadyExistException(NumberAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
	}

	@ExceptionHandler(EmailAndNumberAlreadyExistException.class)
	public ResponseEntity<String> handleEmailAlreadyExistException(EmailAndNumberAlreadyExistException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
	}

}
