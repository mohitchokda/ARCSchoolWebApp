package com.example.arc.customAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PwdStrengthValidiator implements ConstraintValidator<PasswordStrengthValidator,String> {

	Set<String> weakPwds = new HashSet<>();
	
	@Override
	public void initialize(PasswordStrengthValidator an) {
		// TODO Auto-generated method stub
		weakPwds.addAll(Arrays.asList("password","12345","12345678","qwerty"));
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value!=null && !weakPwds.contains(value);
	}

}
