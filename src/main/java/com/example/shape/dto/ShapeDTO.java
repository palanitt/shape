package com.example.shape.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShapeDTO {
	
	private String type;
	private String name;
	private String shapeCoordinates; // It will be in the form "0,0:5,4" - 
									 // here 0,0 represents bottomLeft square coordinate
									 // and 5,4 represents topRight square coordinate and 
									 // : is the delimiter used to split and process the 2 coordinates
}
