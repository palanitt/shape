package com.example.shape.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.shape.model.ShapeEntity;

@Repository
public interface ShapeRepository extends CrudRepository<ShapeEntity, Long>{
	
	public List<ShapeEntity> findAll();
}
