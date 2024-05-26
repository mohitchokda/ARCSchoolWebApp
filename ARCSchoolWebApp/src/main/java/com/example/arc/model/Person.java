package com.example.arc.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.example.arc.customAnnotations.FieldMatchValidator;
import com.example.arc.customAnnotations.PasswordStrengthValidator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@FieldMatchValidator.List({
	//Value match for Emails
	@FieldMatchValidator(
		field = "email", 
		fieldToMatch = "confirmEmail",
		message = "Email and Confirm Email does not match"),
	
	//Value Match for Pwds
	@FieldMatchValidator(
			field = "pwd", 
			fieldToMatch = "confirmPwd",
			message = "Password and Confirm Password does not match")
})
public class Person extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native",strategy = "native")
	private int personId;
	
	//Add Validations and Custom validations
	@NotBlank(message = "Name must not be Empty")
	@Size(min = 3 ,message = "Name must contain atleast 3 characters")
	private String name;
	
	@NotBlank(message = "Mobile Number must not be Empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid Mobile Number")
	private String mobileNum;
	
	@NotBlank(message = "Email must not be Empty")
	@Email(message = "Invalid Email")
	private String email;
	
	@NotBlank(message = "Confirm Email must not be Empty")
	@Transient
	private String confirmEmail;
	
	@NotBlank(message = "Password must not be Empty")
	@PasswordStrengthValidator
	private String pwd;
	
	@NotBlank(message = "Confirm Password must not be Empty")
	@Transient
	private String confirmPwd;
	
	//Address and Role One to One Relationship
																	//Optional
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,targetEntity = Address.class)
	@JoinColumn(name="addressId", referencedColumnName = "address_Id",nullable = true)
	private Address address;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST,targetEntity = Roles.class)
	@JoinColumn(name="role_Id", referencedColumnName = "roleId",nullable = true)
	private Roles roles;
	
	//Class (Many to One Relationship)
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="class_Id", referencedColumnName = "classId",nullable=true)
	private ArcClass arcClass;
	
	//Many to many rs with Courses
	//Person can apply be present or apply to multiple courses at the same time
	//So we create an intermediate table which holds personId and courseId mapping
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "person_course",
				joinColumns = {
						@JoinColumn(name="person_id", referencedColumnName = "personId")},
				inverseJoinColumns = {
						@JoinColumn(name="course_id",referencedColumnName = "courseId")
				})
	private Set<Course> courses = new HashSet<>();
	
		
	//Getters and Setters
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
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
	public String getConfirmEmail() {
		return confirmEmail;
	}
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
	public ArcClass getArcClass() {
		return arcClass;
	}
	public void setArcClass(ArcClass arcClass) {
		this.arcClass = arcClass;
	}
	
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", mobileNum=" + mobileNum + ", email=" + email + ", Pwd : "+pwd+"]";
	}
	
	
}
