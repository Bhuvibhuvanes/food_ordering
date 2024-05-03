package com.FoodOrderingSystem.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShopNotFoundException extends Exception {

	public ShopNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
