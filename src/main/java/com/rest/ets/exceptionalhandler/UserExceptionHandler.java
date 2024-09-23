package com.rest.ets.exceptionalhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rest.ets.exception.UserNotFoundByIdException;
import com.rest.ets.util.AppResponseBuilder;
import com.rest.ets.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class UserExceptionHandler {
	private AppResponseBuilder builder;

	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundById(UserNotFoundByIdException exception){
		return builder.error(HttpStatus.NOT_FOUND,exception.getMessage(),"user not found by given id");
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorStructure<String>> handleUserNameNotFound(UserNotFoundByIdException exception){
		return builder.error(HttpStatus.NOT_FOUND,exception.getMessage(),"user not found by given email");
	}
	

}
