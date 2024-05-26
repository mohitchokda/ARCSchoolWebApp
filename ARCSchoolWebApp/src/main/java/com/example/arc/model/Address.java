package com.example.arc.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Address extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
	private int address_Id;
	
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
	@Pattern(regexp="(^$|[0-9]{5})",message = "Zip Code must be 5 digits")
	private String zip_code;
	
	public int getAddress_Id() {
		return address_Id;
	}
	public void setAddress_Id(int address_Id) {
		this.address_Id = address_Id;
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
	
	@Override
	public String toString() {
		return "Address [address1=" + address1 + ", city=" + city + "]";
	}

}
