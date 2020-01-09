package com.example.shape.exceptions;

public class InvalidSquareCoordinates extends RuntimeException{
	
	private static final long serialVersionUID = 6557137032422191342L;

	public InvalidSquareCoordinates(String errorMessage)
	{
		super(errorMessage);
	}

}
