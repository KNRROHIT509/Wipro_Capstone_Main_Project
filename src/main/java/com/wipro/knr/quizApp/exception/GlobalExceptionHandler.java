package com.wipro.knr.quizApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(QuestionNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleUserNotFoundException(QuestionNotFoundException ex){
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),"UserNotFoundException");
		return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	}
}
