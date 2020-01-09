package com.example.shape.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.example.shape.dto.ShapeDTO;
import com.example.shape.exceptions.InvalidSquareCoordinates;
import com.example.shape.exceptions.ShapeCoordinatesException;
import com.example.shape.model.ShapeEntity;
import com.example.shape.model.Square;
import com.example.shape.repository.ShapeRepository;

@RunWith(MockitoJUnitRunner.class)
public class ShapeServiceTest {
	
	@Mock private ShapeRepository shapeRepository;
	private ShapeDTO shapeDTO;
	private ShapeEntity shapeEntity;
	@Mock private ModelMapper mapper;
	@InjectMocks private ShapeService shapeService;
	
	@Before
	public void setup()
	{
		shapeDTO = ShapeDTO	
					.builder()
					.type("square")
					.name("square1")
					.shapeCoordinates("0,0:5,4")
					.build();
	}
	
	@Test
	public void givenRetrieveAllShapesRequest_whenAvailable_thenRetrieve()
	{
		//List<ShapeEntity> shapeEntityList = new ArrayList<>();
		//shapeEntityList.add(ShapeEntity.builder().id(0).type("square").name("square1").shapeCoordinates("0,0:5,4").build());
		//shapeEntityList.add(ShapeEntity.builder().id(1).type("square").name("square2").shapeCoordinates("2,0:6,5").build());
		shapeService.retrieveShapes();
		verify(shapeRepository, times(1)).findAll();
	}
	
	@Test
	public void givenInputShape_whenValid_thenPersist()
	{
		shapeEntity = ShapeEntity
						.builder()
						.id(0)
						.type("square")
						.name("square1")
						.shapeCoordinates("0,0:5,4")
						.build();
		shapeService.persistShape(shapeDTO);
		verify(shapeRepository, times(1)).save(shapeEntity);
	}
	
	@Test(expected = ShapeCoordinatesException.class)
	public void givenInputShape_whenCoordinatesContainNonNumericValues_thenthrowException()
	{
		shapeDTO.setShapeCoordinates("0,4:5,test");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test(expected = ShapeCoordinatesException.class)
	public void givenInputShape_whenCoordinatesdontContain2XYValues_thenthrowException()
	{
		shapeDTO.setShapeCoordinates("0,4:5");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test(expected = ShapeCoordinatesException.class)
	public void givenInputShape_whenCoordinatesDontContainProperXYValues_thenthrowException()
	{
		shapeDTO.setShapeCoordinates("0,4");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test
	public void givenInputShape_whenCoordinatesContainProperXYValues_thenNoExceptionThrown()
	{
		shapeDTO.setShapeCoordinates("4,6:5,7");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test(expected = InvalidSquareCoordinates.class)
	public void givenInputShape_whenCoordinatesDontContainProperSquareCoordinates_thenthrowException()
	{
		shapeDTO.setShapeCoordinates("0,4:0,4");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test(expected = InvalidSquareCoordinates.class)
	public void givenInputShape_whenCoordinatesDontContainProperSquareCoordinates_thenthrowException2()
	{
		shapeDTO.setShapeCoordinates("4,4:4,4");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test(expected = InvalidSquareCoordinates.class)
	public void givenInputShape_whenCoordinatesContainSameYvalues_thenthrowException()
	{
		shapeDTO.setShapeCoordinates("4,5:3,5");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test(expected = InvalidSquareCoordinates.class)
	public void givenInputShape_whenCoordinatesContainSameXvalues_thenthrowException()
	{
		shapeDTO.setShapeCoordinates("4,6:4,8");
		shapeService.validateShapeCoordinates(shapeDTO);
	}
	
	@Test
	public void givenInputShape_whenCoordinatesOverlap_thenReturnTrue()
	{
		shapeEntity = ShapeEntity
						.builder()
						.id(0)
						.type("square")
						.name("square2")
						.shapeCoordinates("1,0:5,4")
						.build();
		Square square1 = shapeService.buildSquareModel(shapeDTO.getShapeCoordinates());
		Square square2 = shapeService.buildSquareModel(shapeEntity.getShapeCoordinates());
		assertTrue(shapeService.checkOverlap(square1, square2));
	}
	
	@Test
	public void givenInputShape_whenCoordinatesDontOverlap_thenReturnFalse()
	{
		shapeEntity = ShapeEntity
						.builder()
						.id(0)
						.type("square")
						.name("square2")
						.shapeCoordinates("0,5:5,7")
						.build();
		Square square1 = shapeService.buildSquareModel(shapeDTO.getShapeCoordinates());
		Square square2 = shapeService.buildSquareModel(shapeEntity.getShapeCoordinates());
		assertFalse(shapeService.checkOverlap(square1, square2));
	}

}
