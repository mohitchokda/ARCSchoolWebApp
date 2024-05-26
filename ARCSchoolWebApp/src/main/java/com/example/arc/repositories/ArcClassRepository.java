package com.example.arc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arc.model.ArcClass;

@Repository
public interface ArcClassRepository extends JpaRepository<ArcClass,Integer>{
	public ArcClass findByName(String className);
}
