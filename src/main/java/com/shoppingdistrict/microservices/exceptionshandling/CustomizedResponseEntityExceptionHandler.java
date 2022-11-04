package com.shoppingdistrict.microservices.exceptionshandling;

import java.util.Date;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	private Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		logger.info("Entry to handleAllExceptions");
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		logger.info("Returning the exception message {} and exiting from handleAllExceptions", ex.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public final ResponseEntity<Object> handleNoSuchElementExceptions(Exception ex, WebRequest request) throws Exception {
		logger.info("Entry to handleNoSuchElementExceptions");
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		logger.info("Returning the exception message {} and exiting from handleNoSuchElementExceptions", ex.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Entry to handleNoSuchElementExceptions");
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().getFieldError().getDefaultMessage());
		logger.info("Returning the exception message {} and exiting from handleNoSuchElementExceptions", ex.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}


}
