package com.example.arc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.arc.model.Person;
import com.example.arc.repositories.PersonRepository;
import com.example.arc.services.PersonService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("public")
public class PublicController {
	
	@Autowired
	private PersonService personService;

	@RequestMapping("/register")
	public String displayRegisterPage(Model model) {
		model.addAttribute("person",new Person());
		return "register.html";	
	}
	
	@RequestMapping("/createUser")
	public String createUser(@Valid @ModelAttribute("person") Person p,Errors errorList){
		if(errorList.hasErrors()) {
			return "register.html";
		}else {
			//If success the redirect to Login page with a message
			//Add this person to  DB
			if(personService.createNewPerson(p))
				return "redirect:/login?register=true";
			else return "register.html";
		}
	}
}
