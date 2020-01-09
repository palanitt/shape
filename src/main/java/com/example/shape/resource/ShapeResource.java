package com.example.shape.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shape.dto.ShapeDTO;
import com.example.shape.exceptions.InvalidSquareCoordinates;
import com.example.shape.exceptions.NameAlreadyPresentException;
import com.example.shape.exceptions.ShapeCoordinatesException;
import com.example.shape.exceptions.SquareOverlapException;
import com.example.shape.service.ShapeService;

@RestController
public class ShapeResource {
	
	@Autowired ShapeService shapeService;
	
	//Post a shape
	@RequestMapping(value = "/shape", method = RequestMethod.POST)
	public ResponseEntity < String > persistShape(@RequestBody ShapeDTO shapeDTO) {
		try
		{
			shapeService.validateInputShape(shapeDTO); //Validate the input shape properties
			shapeService.persistShape(shapeDTO);
		}
		catch(NameAlreadyPresentException e)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		catch(SquareOverlapException e)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		catch(ShapeCoordinatesException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch(InvalidSquareCoordinates e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Square shape stored successfully");
	}
	
	// Retrieve All shapes
	@RequestMapping(value = "/shape", method = RequestMethod.GET)
	public @ResponseBody List<ShapeDTO> retrieveShapes() {	
		List<ShapeDTO> listShapeDTO = shapeService.retrieveShapes();
		return listShapeDTO;
	}

}
