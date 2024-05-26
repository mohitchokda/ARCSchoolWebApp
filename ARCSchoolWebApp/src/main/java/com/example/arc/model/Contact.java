package com.example.arc.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity{
	
	//All the variable names has to be the same as on html field name attribute
	//Else it would just skip accepting the value
	
	//Acts as a primary key in DB
	@Id
	//@GeneratedValue annotation denotes that a value for a column,which must be annotated with @Id is generated. 
	//The elements strategy and generator on the annotation describe how the generated value is obtained.
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native",strategy = "native")
	//@GenericGenerator is a hibernate annotation used to denote a custom generator
	@Column(name = "contact_id")
	private int contactId;
	
	//Added all validations to occur
	
	@Size(min = 3, message = "Name must be min 3 characters")
	@NotBlank(message = "Name must not be empty")
	private String name;
	
	@NotBlank(message = "Mobile number must not be empty")
	@Pattern(regexp = "^$|[0-9]{10}",message = "Please enter a valid mobile number")
	private String mobileNum;
	
	@NotBlank(message = "Email must not be empty")
	@Email(message = "Please enter a valid Email Id")
	private String email;
	
	@NotBlank(message = "Please specify the Subject")
	private String subject;
	
	@NotBlank(message = "Please enter some message")
	private String message;
	
	private String status;
	
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Contact [name=" + name + ", mobileNum=" + mobileNum + ", email=" + email + ", subject=" + subject
				+ ", message=" + message + "]";
	}
}
