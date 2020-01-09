package com.example.shape.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.shape.dto.ShapeDTO;
import com.example.shape.exceptions.InvalidSquareCoordinates;
import com.example.shape.exceptions.NameAlreadyPresentException;
import com.example.shape.exceptions.ShapeCoordinatesException;
import com.example.shape.exceptions.SquareOverlapException;
import com.example.shape.model.Point;
import com.example.shape.model.ShapeEntity;
import com.example.shape.model.Square;
import com.example.shape.repository.ShapeRepository;

@Service
public class ShapeService {
	
	private static final String PROPER_SQUARE_COORDINATES = "Square Shape coordinates should contian a BottomLeft and a TopRight (x,y) coordinate values";
	private static final String ONLY_NUMBERS = "Shape coordinates should contain only numbers";
	private static final String COORDINATES_NOT_VALID = "Coordinates are not valid for a square shape";
	private static final String ALREADY_EXISTS = "Shape name already exists";
	
	@Autowired ShapeRepository shapeRepository;
	
	//private ModelMapper modelMapper = new ModelMapper();
	
	public void persistShape(ShapeDTO shapeDTO)
	{
		ModelMapper modelMapper = new ModelMapper();
		ShapeEntity shape = modelMapper.map(shapeDTO, ShapeEntity.class);
		try
		{
			shapeRepository.save(shape);
		}
		catch(DataIntegrityViolationException e)
		{
			throw new NameAlreadyPresentException(ALREADY_EXISTS);
		}
	}
	
	// validate the input shape properties
	public void validateInputShape(ShapeDTO shapeDTO)
	{
		validateShapeCoordinates(shapeDTO);
		List<ShapeEntity> shapeEntityList = shapeRepository.findAll();		
		if(shapeEntityList.size() >= 1)
		{
			shapeOverlapCheck(shapeEntityList,shapeDTO);
		}
		
	}
	
	// validate the coordinates for square shape. A square shape contains a bottomLeft coordinate e.g. (0,2)
	// and a topRight coordinate, e.g. (5,4)
	public void validateShapeCoordinates(ShapeDTO shapeDTO)
	{

		String[] shapeCoordinates = shapeDTO.getShapeCoordinates().split(":");
		if (shapeCoordinates.length != 2)
		{
			throw new ShapeCoordinatesException(PROPER_SQUARE_COORDINATES);
		}
		String[] bottomLeft = shapeCoordinates[0].split(",");
		String[] topRight = shapeCoordinates[1].split(",");
		if (bottomLeft.length != 2 || topRight.length != 2)
		{
			throw new ShapeCoordinatesException(PROPER_SQUARE_COORDINATES);
		}
		try
		{ // convert the string to integer values
			int x1 = Integer.parseInt(bottomLeft[0]);
			int y1 = Integer.parseInt(bottomLeft[1]);
			int x2 = Integer.parseInt(topRight[0]);
			int y2 = Integer.parseInt(topRight[1]);
			if((x2-x1) <= 0 || (y2-y1) <=0 || (x2-x1) != (y2-y1))
			{
				throw new InvalidSquareCoordinates(COORDINATES_NOT_VALID);  // Conditions to validate a proper square coordinate
			}
			/*
			 * if(x1==x2 || y1==y2 || (x1==x2 && y1==y2) || (x1==y1 && x2==y2)) //
			 * Conditions to validate a proper square coordinate { throw new
			 * InvalidSquareCoordinates(COORDINATES_NOT_VALID); }
			 */
		}
		catch(NumberFormatException e)
		{
			throw new ShapeCoordinatesException(ONLY_NUMBERS);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new ShapeCoordinatesException(PROPER_SQUARE_COORDINATES);
		}
		
	}
	
	// check if there is any overlap between the input square shape and any of the stored square shapes
	public void shapeOverlapCheck(List<ShapeEntity> shapeEntityList, ShapeDTO shapeDTO)
	{
		Square inputSquare = buildSquareModel(shapeDTO.getShapeCoordinates());
		long overlapCount = shapeEntityList
				.stream()
				.filter(s -> checkOverlap(buildSquareModel(s.getShapeCoordinates()), inputSquare))
				.count();
		if(overlapCount >= 1)
		{
			throw new SquareOverlapException("Square Overlap deducted");
		}
	}
	
	// validate if there is an overlap between an input square shape and a stored square shape
	public boolean checkOverlap(Square storedSquare, Square inputSquare)
	{
		return inputSquare.isOverlapping(storedSquare);
	}
	
	// build a square object using the coordinate values
	public Square buildSquareModel(String coordinates)
	{
		String[] square_coordinates = coordinates.split(":");
		// build the bottom right coordinate object for a square
		Point square_bottomLeft = new Point();
		square_bottomLeft.setX(Integer.parseInt(square_coordinates[0].split(",")[0]));
		square_bottomLeft.setY(Integer.parseInt(square_coordinates[0].split(",")[1]));
		
		// build the top left coordinate object for a square
		Point square_topRight = new Point();
		square_topRight.setX(Integer.parseInt(square_coordinates[1].split(",")[0]));
		square_topRight.setY(Integer.parseInt(square_coordinates[1].split(",")[1]));
		return new Square(square_bottomLeft, square_topRight);	
	}
	
	// retrieve all the shapes from the 
	public List<ShapeDTO> retrieveShapes()
	{
		ModelMapper modelMapper = new ModelMapper();
		List<ShapeEntity> shapeEntityList = shapeRepository.findAll();
		return modelMapper.map(shapeEntityList, new TypeToken<List<ShapeDTO>>(){}.getType()); 
	}
	
}
