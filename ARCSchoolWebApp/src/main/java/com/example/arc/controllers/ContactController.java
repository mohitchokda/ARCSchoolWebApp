package com.example.arc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.example.arc.model.Contact;
import com.example.arc.services.ContactService;

import jakarta.validation.Valid;

@Controller
public class ContactController {
	
	ContactService contactService;
	
	boolean saved = false;
	
	@Autowired
	public ContactController(ContactService contactService) {
		this.contactService=contactService;
	}
	
	//this method redirects user to contact page
	//We have to bind our contact obj whenever contact page is loaded so
	//we pass a new contact bean to the UI
	@RequestMapping("/contact") 
	public String displayContactPage(Model model) {
		model.addAttribute("contactObj",new Contact());
		model.addAttribute("dataSaved",saved);
		saved=false;
		//thymeleaf uses this obj to bind data fields to the Obj fields using th:object and th:field
		return "contact.html";
	}
	
	//We need to use method as we are getting the data from UI to BackEnd
	//@PostMapping is used for post requests
	//Handling Submit using @ReqParam
 	@RequestMapping(value = {"/save"}, method = RequestMethod.POST)
	//All the names specified with @ReqParam has to be the same name present on html name attribute
	public ModelAndView saveMsg(@RequestParam String name,
								@RequestParam(name="mobileNum") String mobile,
								@RequestParam String email,
								@RequestParam String subject,
								@RequestParam String message) {
		//All this data is captured from the UI fields under form
		//One drawback of using @ReqParam is whenever we have many fields its difficult to add all as a parameter to function
		System.out.println(name+"\n"+mobile+"\n"+email+"\n"+subject+"\n"+message);
		
		//this will redirect to the /contact page handler and displays new contact.html
		return new ModelAndView("redirect:/contact");
	}
 	
 	//Controller checks and passed data to Service layer and then service layer stores the data to backend DB 
 	//Handling submit using POJO class
 	//The Contact obj is redirected here and we have to ask Spring to perform validations
 	//we use @Valid annotation along with specifying that Model attribute is used
 	//We need to add Errors as it will store all the binding data and their validation info
 	@RequestMapping(value = "/saveMsg", method = RequestMethod.POST)
 	public String saveMsgbyPOJO(@Valid @ModelAttribute("contactObj")Contact contact,Errors errorList) {
 		
 		if(errorList.hasErrors()) {
 			System.out.println(errorList.toString());
 			//Return same page along with error info
 			return "contact.html";
 		}else {
 		//else save data and redirect to a new contact page
 		//we have to pass the data to service in order to store the values
 			//System.out.println("Redirecting ------ ");
 			//Calling Service to Store Data
 			saved = contactService.saveData(contact);
 			return "redirect:/contact";
 		}
 	}
}
