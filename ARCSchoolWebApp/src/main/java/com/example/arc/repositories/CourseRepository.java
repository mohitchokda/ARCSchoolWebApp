package com.example.arc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arc.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

	Course findByCourseName(String courseName);

}
