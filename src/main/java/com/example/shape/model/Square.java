package com.example.shape.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Square {

    private Point bottomLeft;
    private Point topRight;
    
    public Square(Point bottomLeft, Point topRight)
    {
    	this.bottomLeft = bottomLeft;
    	this.topRight = topRight;
    }
 
    public boolean isOverlapping(Square other) {
    	if (this.topRight.getY() <= other.bottomLeft.getY() 
	      || this.bottomLeft.getY() >= other.topRight.getY()) {
	        return false;
	    }
	    if (this.topRight.getX() <= other.bottomLeft.getX() 
	      || this.bottomLeft.getX() >= other.topRight.getX()) {
	        return false;
	    }
	    return true;
    }

}
