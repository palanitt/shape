package com.example.shape.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.example.shape.dto.ShapeDTO;
import com.example.shape.service.ShapeService;

@RunWith(MockitoJUnitRunner.class)
public class ShapeResourceTest {
	
	@Mock ShapeService shapeService;
	@InjectMocks ShapeResource shapeResource;
	ShapeDTO shapeDTO;
	
	@Before
	public void setUp()
	{
		shapeDTO = ShapeDTO
						.builder()
						.type("square")
						.name("square1")
						.shapeCoordinates("0,0:5,4")
						.build();
	}
	
	@Test
	public void givenAShape_whenPostRequestIsMade_storeSquareShape()
	{
		
		ResponseEntity<String> response =  shapeResource.persistShape(shapeDTO);
		verify(shapeService, times(1)).validateInputShape(shapeDTO);
		verify(shapeService, times(1)).persistShape(shapeDTO);
		assertThat(response.getStatusCodeValue(), is(201));
	}
	
	@Test
	public void givenARequest_whenGetRequestIsMade_retrieveAllSquareShapes()
	{
		List<ShapeDTO> shapeDTOList=  shapeResource.retrieveShapes();
		verify(shapeService, times(1)).retrieveShapes();
	}

}
