package com.example.arc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.arc.model.Person;

@Repository //Spring recognizes the repositories by the fact that they extend one of the predefined Repository interfaces
public interface PersonRepository extends JpaRepository<Person,Integer> {
	
	public Person findByEmail(String email);
}
