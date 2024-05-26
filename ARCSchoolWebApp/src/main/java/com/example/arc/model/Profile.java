package com.example.arc.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Profile {
	
	@NotBlank(message = "Name must not be Empty")
	@Size(min = 3 ,message = "Name must contain atleast 3 characters")
	private String name;
	
	@NotBlank(message = "Mobile Number must not be Empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid Mobile Number")
	private String mobileNum;
	
	@NotBlank(message = "Email must not be Empty")
	@Email(message = "Invalid Email")
	private String email;
	
	@NotBlank(message = "Address1 cannot be Empty")
	@Size(min=5, message="Address1 must be at least 5 characters long")
	private String address1;
	
	private String address2;
	
	@NotBlank(message="City must not be blank")
    @Size(min=5, message="City must be at least 5 characters long")
	private String city;
	
	@NotBlank(message="State must not be blank")
    @Size(min=5, message="State must be at least 5 characters long")
	private String state;
	
	@NotBlank(message="Zip Code must not be blank")
	@Pattern(regexp="(^$|[0-9]{6})",message = "Zip Code must be 6 digits")
	private String zip_code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	
}
