package com.example.arc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//POJO class for displaying Holidays

@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity{
	
	@Id
	private String day;
	
	private String reason;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
	public enum Type{
		Festival,Govt;
	}
	
	public Holiday() {
	}
	
	public Holiday(String day, String reason, Type type) {
		this.day = day;
		this.reason = reason;
		this.type = type;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
