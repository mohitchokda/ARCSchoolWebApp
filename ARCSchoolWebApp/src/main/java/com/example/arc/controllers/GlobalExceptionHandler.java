package com.example.arc.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView excpetionHandler(Exception e) {
		ModelAndView errorPage=new ModelAndView();
		//this will redirect to error.html page
		errorPage.setViewName("error");
		//Adding this attribute to display on the webpage
		errorPage.addObject("errorMsg",e.getMessage());
		
		return errorPage;
	}
}
