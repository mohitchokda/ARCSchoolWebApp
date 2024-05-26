package com.example.arc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.arc.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

}
