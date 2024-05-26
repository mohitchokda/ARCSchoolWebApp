package com.example.arc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value = {"","/","/home"})
	public String displaHome(Model model) {
		model.addAttribute("name","Harshit");
		return "home.html";
	}

}
