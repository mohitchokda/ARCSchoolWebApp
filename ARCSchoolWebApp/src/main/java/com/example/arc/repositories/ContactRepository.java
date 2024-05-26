package com.example.arc.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.arc.model.Contact;

@Repository //->used to store data to DB
public interface ContactRepository extends CrudRepository<Contact,Integer>{
	
	
	//Derived Query Method
	List<Contact> findByStatus(String status);
	
	
	/*
	//Get JDBC Template Bean
	private JdbcTemplate jdbc;
	
	//this will get the bean which is already created by Spring Boot and we are mapping that here
	@Autowired
	public ContactRepository(JdbcTemplate jdbcTemplate) {
		this.jdbc=jdbcTemplate;
	}
	
	public int storeContactDetails(Contact contact) {
		//As we are creating new Contact Req we no need to update fields Update by and time.
		//We dont have to enter contact Id as it autotgenerated in DB
		String insertContact = "INSERT INTO CONTACT_MSG (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS,CREATED_AT,CREATED_BY) "
				+ "				VALUES (?,?,?,?,?,?,?,?)";
		//It returns num of rows impacted
		return jdbc.update(insertContact , contact.getName(),contact.getMobileNum(),contact.getEmail(),contact.getSubject(),contact.getMessage(),contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
	}

	public List<Contact> findMsgsWithStatus(String status) {
		// TODO Auto-generated method stub
		String getOpenMsgs="SELECT * FROM CONTACT_MSG C WHERE C.STATUS = '"+status+"'";
		//Mapping of Fields
		return jdbc.query(getOpenMsgs,new RowMapper<Contact> (){
			@Override
			public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
				//RowMapper is an Interface which converts DB data to POJO class Object 
				//We need to map each field of DB to POJO Class row-wise
				Contact c = new Contact();
				c.setContactId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setMobileNum(rs.getString(3));
				c.setEmail(rs.getString(4));
				c.setSubject(rs.getString(5));
				c.setMessage(rs.getString(6));
				c.setStatus(rs.getString(7));
				c.setCreatedAt(rs.getTimestamp(8).toLocalDateTime());
				c.setCreatedBy(rs.getString(9));
				if(rs.getTimestamp(10)!=null)
					c.setUpdatedAt(rs.getTimestamp(10).toLocalDateTime());
				
				c.setUpdatedBy(rs.getString(11));
				return c;
			}
		});
	}

	public int updateMsgStatusByContactID(int id,String status,String updateBy) {
		String closeQuery = "UPDATE CONTACT_MSG SET STATUS = ?, UPDATED_AT = ?, UPDATED_BY = ? WHERE CONTACT_ID = ?";
		return jdbc.update(closeQuery,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1,status);
				ps.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
				ps.setString(3, updateBy);
				ps.setInt(4,id);
			}
		});
	}*/
}
