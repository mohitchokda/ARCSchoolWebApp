package com.example.arc.customAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
//Which class provides implementation
@Constraint(validatedBy = PwdStrengthValidiator.class)
//this Annotation can be used on
//Both Fields and Methods
@Target({ElementType.FIELD , ElementType.METHOD}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrengthValidator {
	//Attributes for Annotations
	//Message attribute
	String message() default "Weak Password detected,Please choose a Strong Password";
	//Groups attribute
	Class<?>[] groups() default {};//default group is empty
	
	Class<? extends Payload>[] payload() default {}; 
}
