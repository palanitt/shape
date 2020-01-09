package com.example.shape.exceptions;

public class NameAlreadyPresentException extends RuntimeException {

	private static final long serialVersionUID = 7020107597622458835L;

	public NameAlreadyPresentException(String errorMessage){
		super(errorMessage);
	}
}
