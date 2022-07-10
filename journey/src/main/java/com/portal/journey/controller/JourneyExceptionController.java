package com.portal.journey.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.portal.journey.exception.JourneyCustomException;
import com.portal.journey.exception.JourneyRunTimeException;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@ControllerAdvice
public class JourneyExceptionController {

	/**
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = JourneyRunTimeException.class)
	public ResponseEntity<Object> exception(JourneyRunTimeException exception) {
		return new ResponseEntity<>("Insuffient parameter.. Please provide the required parameter",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = JourneyCustomException.class)
	public ResponseEntity<Object> exception(JourneyCustomException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
