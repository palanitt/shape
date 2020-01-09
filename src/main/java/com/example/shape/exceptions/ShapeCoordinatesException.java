package com.example.shape.exceptions;

public class ShapeCoordinatesException extends RuntimeException {
	
	private static final long serialVersionUID = -1619283081173975818L;

	public ShapeCoordinatesException(String errorMsg)
	{
		super(errorMsg);
	}

}
