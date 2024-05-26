package com.example.arc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.arc.constants.ARCConstants;
import com.example.arc.model.Person;
import com.example.arc.model.Roles;
import com.example.arc.repositories.PersonRepository;
import com.example.arc.repositories.RolesRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	public boolean createNewPerson(Person p) {
		Roles r=rolesRepository.findByRoleName(ARCConstants.STUDENT);
		p.setPwd(pwdEncoder.encode(p.getPwd()));
		p.setRoles(r);
		p = personRepository.save(p);
		if(p!=null && p.getPersonId() !=0) return true;
		return false;
	}

	public Person getPersonByEmail(String name) {
		return personRepository.findByEmail(name);
	}
}
