package com.capstone.schoolmanagement.errors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManagement {
	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<String> manageEntityNotFoundException(EntityNotFoundException e){
		return new ResponseEntity<String>(e.getMessage() + " --- from ExceptionManagement", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityExistsException.class)
	protected ResponseEntity<String> manageEntityExistsException(EntityExistsException e) {
		return new ResponseEntity<String>(e.getMessage()  + " --- from ExceptionManagement", HttpStatus.FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<String> manageIllegalArgumentException(IllegalArgumentException e) {
		return new ResponseEntity<String>(e.getMessage()  + " --- from ExceptionManagement", HttpStatus.BAD_REQUEST);
	}
	
}
