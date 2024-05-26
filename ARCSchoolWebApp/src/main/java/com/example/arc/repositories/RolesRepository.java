package com.example.arc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.arc.model.Roles;

@Repository
public interface RolesRepository extends CrudRepository<Roles,Integer> {
	
	public Roles findByRoleName(String role_name);
	
}
