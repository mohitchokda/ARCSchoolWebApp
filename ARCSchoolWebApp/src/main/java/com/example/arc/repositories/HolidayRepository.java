package com.example.arc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.arc.model.Holiday;

//Spring Data JPA repo uses interfaces
@Repository
public interface HolidayRepository extends CrudRepository<Holiday,String>{
	
//	//This is when we use Spring JDBC
//	private JdbcTemplate jdbcTemplate;
//	
//	@Autowired
//	public HolidayRepository(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}
//	
//	public List<Holiday> getHolidaysList() {
//		String getHolidays = "SELECT * FROM holidays";
//		BeanPropertyRowMapper<Holiday> rm=new BeanPropertyRowMapper<>(Holiday.class);
//		return jdbcTemplate.query(getHolidays,rm);
//	}
}
