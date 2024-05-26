package com.example.arc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.arc.model.Person;
import com.example.arc.services.PersonService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
	
	@Autowired
	PersonService personService;

	@RequestMapping(value="/dashboard")
	public String displayDashboard(Authentication auth,Model model,HttpSession httpSession) throws Exception {
		
		//Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		//throwing exception explicitly to catch it through @ControllerAdvice method
		//throw new Exception("My Exception occured");
		//Add user details to Session from DB
		Person user = personService.getPersonByEmail(auth.getName());
		//System.out.println("Cuurr User : "+user);
		model.addAttribute("name",user.getName());
		model.addAttribute("role",auth.getAuthorities());
		httpSession.setAttribute("curr_user",user);
		
		return "dashboard.html";
	}
}
