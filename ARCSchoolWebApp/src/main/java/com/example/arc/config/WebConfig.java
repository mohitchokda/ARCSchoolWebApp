package com.example.arc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		// This helps us in mapping url paths to the corresponding view without the need to create Controller.
		// This is used when the page is static and is common to all the users.
		// No dynamic control or does not require much business logic
		registry.addViewController("/courses").setViewName("courses");
		registry.addViewController("/about").setViewName("about");
		//Contact page is more complex and requires data to be passed to backend so it need Controller
		//registry.addViewController("/contact").setViewName("contact");
	}
}
