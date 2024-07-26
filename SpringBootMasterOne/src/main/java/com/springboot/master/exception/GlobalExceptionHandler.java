package com.springboot.master.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<ValidationError> errors = ex.getBindingResult().getAllErrors().stream()
				.map(error -> new ValidationError(((FieldError) error).getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"Validation Failed", "Invalid input data", errors);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

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
