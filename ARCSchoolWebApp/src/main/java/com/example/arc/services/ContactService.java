package com.example.arc.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.example.arc.constants.ARCConstants;
import com.example.arc.model.Contact;
import com.example.arc.repositories.ContactRepository;

@Service
//@RequestScope       //-> For each Http request new contact service bean is created
//@SessionScope     //-> For each Http Session new contact service bean is created
//@ApplicationScope //-> Single contact service bean is created at the start of the server and it remains same throughout the application regardless of sessions and requests
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	public ContactService(){
		//System.out.println("Contact Service bean is created");
	}
	
	//this method is going to map values and store it in the back end database
	public boolean saveData(Contact contact) {
		//Update Data and call to repo to save the data
		contact.setStatus(ARCConstants.OPEN);
		//contact.setCreatedBy(ARCConstants.ANONYMOUS);
		//contact.setCreatedAt(LocalDateTime.now());
		
		System.out.println(contact +" Stored");
		return contactRepository.save(contact) != null;
	}

	public List<Contact> getAllOpenMessages() {
		// TODO Auto-generated method stub
		//return contactRepository(ARCConstants.OPEN);
		return contactRepository.findByStatus(ARCConstants.OPEN);
	}

	public boolean closeMsg(int id){
		//To update any Row in DB (Object) we need to first retrieve the Object
		//and the update the same and save it back to DB
		//Spring automatically generates SQL stmts based on the change in Objects
		
		//get Obj
		//Returns an Optional value
		Optional<Contact> contact = contactRepository.findById(id);
		//check if c is present in Optional
		contact.ifPresent(c -> {
			//update vals
			c.setStatus(ARCConstants.CLOSE);
			//c.setUpdatedBy(updateBy);
			c.setUpdatedAt(LocalDateTime.now());
		});
		//save back
		return contactRepository.save(contact.get())!=null;
		//return contactRepository.(id,ARCConstants.CLOSE,updateBy);
	}
}
