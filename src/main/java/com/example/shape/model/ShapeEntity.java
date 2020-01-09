package com.example.shape.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShapeEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    private String type;
    @Column(name="name", nullable=false, unique=true)
    private String name;
    private String shapeCoordinates;
}


