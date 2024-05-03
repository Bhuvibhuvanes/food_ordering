package com.FoodOrderingSystem.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


	@SuppressWarnings("serial")
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public class NotFoundException extends RuntimeException {
		public NotFoundException(String exception) {
			super(exception);
		}

	}


