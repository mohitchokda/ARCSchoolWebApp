package com.example.arc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.arc.model.Address;
import com.example.arc.model.Person;
import com.example.arc.model.Profile;
import com.example.arc.repositories.PersonRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProfileController {
	
	@Autowired
	PersonRepository personRepository;

	@RequestMapping("/displayProfile")
	public String displayProfile(Model model,HttpSession httpSession) {
		
		Person p = (Person) httpSession.getAttribute("curr_user");
		
		Profile profile = new Profile();
		profile.setName(p.getName());
		profile.setEmail(p.getEmail());
		profile.setMobileNum(p.getMobileNum());
		//if Address is available
		if(p.getAddress()!=null && p.getAddress().getAddress_Id()>0) {
			profile.setAddress1(p.getAddress().getAddress1());
			profile.setAddress2(p.getAddress().getAddress2());
			profile.setCity(p.getAddress().getCity());
			profile.setState(p.getAddress().getState());
			profile.setZip_code(p.getAddress().getZip_code());
		}
		model.addAttribute("profile",profile);
		return "profile.html";
	}
	
	@RequestMapping("/updateProfile")
	public String updateProfile(@Valid @ModelAttribute("profile") Profile p,Errors errors,HttpSession httpSession) {
	
		if(errors.hasErrors()) {
			//model.addAttribute("errorList",errorList);
			return "profile.html";
		}
			//Save User data
			Person person = (Person) httpSession.getAttribute("curr_user");
			person.setName(p.getName());
			person.setEmail(p.getEmail());
			person.setMobileNum(p.getMobileNum());
			
			if(person.getAddress()== null || !(person.getAddress().getAddress_Id()>0)) {
				person.setAddress(new Address());
			}
			
			System.out.println(p.getAddress1());
			person.getAddress().setAddress1(p.getAddress1());
			person.getAddress().setAddress2(p.getAddress2());
			person.getAddress().setCity(p.getCity());
			person.getAddress().setState(p.getState());
			person.getAddress().setZip_code(p.getZip_code());
			
			Person savedPerson = personRepository.save(person);
			httpSession.setAttribute("curr_user",savedPerson);
			System.out.println("SAVED");
			return "redirect:/displayProfile";
	}
}
