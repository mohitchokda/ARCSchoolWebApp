package com.example.arc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.arc.model.Holiday;
import com.example.arc.model.Holiday.Type;
import com.example.arc.repositories.HolidayRepository;


@Controller
public class HolidayController {
	
	@Autowired
	HolidayRepository holidayRepository;
	
	//@RequestMapping(value="/holidays",method=RequestMethod.GET)
	//@RequestParam handles query params which we get from URL names must be the same are req param
	//public String displayHolidays(@RequestParam(required = false)boolean festival,
									//@RequestParam(required = false) boolean govt,
	
	//Here we are using Path variables
	@GetMapping(value = {"/holidays/{holidayType}"})								
	public String displayHolidays(@PathVariable(required = false) String holidayType,Model model) {
		//Handle req param values
		//model.addAttribute("fes",festival);
		//model.addAttribute("govt",govt);
		
		//Handling through Path variable
		if(holidayType!=null && holidayType.equals("govt")) {
			model.addAttribute("govt",true);
			model.addAttribute("fes",false);
		}else if(holidayType!=null && holidayType.equals("festival")) {
			model.addAttribute("fes",true);
			model.addAttribute("govt",false);
		}else {
			model.addAttribute("fes",true);
			model.addAttribute("govt",true);
		}
		
		
		//Get Holidays from DB
		List<Holiday> holidaysList = (List<Holiday>) holidayRepository.findAll();
		//Add them to Model as attribute
		//We can create 2 type of attriute one for Govt holiday and Festival
		//mapping all festival holidays to festival list using stream API
		//We can use for loop based on enum types as well
		model.addAttribute("festivalList",holidaysList.stream().filter(h -> h.getType().equals(Type.Festival)).toList());
		model.addAttribute("govtList",holidaysList.stream().filter(h -> h.getType().equals(Type.Govt)).toList());
		
		return "holiday.html";
	}
}
