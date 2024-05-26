package com.example.arc.customAnnotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = FieldMatcher.class)
@Target(ElementType.TYPE) //Works on the whole class as we need 2 attributes of class
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(com.example.arc.customAnnotations.FieldMatchValidator.List.class)
public @interface FieldMatchValidator {
	//Annotation Attributes
	String message() default "Field values doesn't match";
	
	String field();   		//First field to Match
	String fieldToMatch();	//Second field to Match
	
	Class<?>[] groups() default {};//default group is empty
	
	Class<? extends Payload>[] payload() default {};
	
	@Target(ElementType.TYPE)
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		FieldMatchValidator[] value();
	}
	
}
