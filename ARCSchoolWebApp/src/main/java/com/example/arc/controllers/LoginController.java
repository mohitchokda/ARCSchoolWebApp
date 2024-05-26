package com.example.arc.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	
	//Handling Login
	@RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
	//error to check if login errors out and logout to check if log out happened
	//These are mentioned under spring security mapping
	//Model is used to set the out put message on the html screen
	public String handleLogin(@RequestParam(value="error",required = false)String error,
							@RequestParam(value="logout",required = false)String logout,
							@RequestParam(value="register",required=false)String register,
							Model model) {
		String errorMsg="";
		//System.out.println(error+" "+logout);
		if(error!=null) {
			errorMsg="Invalid credentials!!";
		}
		if(logout!=null) {
			errorMsg="logout successful";
		}
		if(register!=null) {
			errorMsg = "Registration Successful. Please Login";
		}
		model.addAttribute("error", errorMsg);
		return "login.html";
	}
	
	
	//Handling Logout
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		
		//Getting current authentication details
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null) {
			//An authenticated user has logged out so handle logout using Spring
			//This will trigger logout method specified in Spring Security
			//and reset the Auth and Session details
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	
		return "redirect:/login?logout=true";
	}
}
