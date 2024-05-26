package com.example.arc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAspectJAutoProxy
@SpringBootApplication
//Required for JPA repos and Model class mapping 
@EnableJpaRepositories("com.example.arc.repositories")
@EntityScan("com.example.arc.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ARCSchoolApplication {

	public static void main(String[] args) {
		//My Projects
		SpringApplication.run(ARCSchoolApplication.class, args);
	}

}
