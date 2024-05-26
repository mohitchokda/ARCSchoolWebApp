package com.example.arc.customAnnotations;

import org.springframework.beans.BeanWrapperImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatcher implements ConstraintValidator<FieldMatchValidator, Object> {

	String field1="",field2="";
	
	@Override
	public void initialize(FieldMatchValidator constraintAnnotation) {
		field1 = constraintAnnotation.field();
		field2 = constraintAnnotation.fieldToMatch();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(value==null) return false;
		//To get Bean properties at runtime we use BeanWrapperImpl
		Object val1 = new BeanWrapperImpl(value).getPropertyValue(field1);
		Object val2 = new BeanWrapperImpl(value).getPropertyValue(field2);
		if(val1!=null) {
			//To Bypass Hashed Value for Pwd when JPA Validation is Enabled
			//if(val1.toString().startsWith("$2a")) return true;
			return val1.equals(val2);
		}
		return val2==null;
	}

}
