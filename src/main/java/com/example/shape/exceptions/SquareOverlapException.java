package com.example.shape.exceptions;

public class SquareOverlapException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SquareOverlapException (String errorMessage) {
		super(errorMessage);
	}

}
