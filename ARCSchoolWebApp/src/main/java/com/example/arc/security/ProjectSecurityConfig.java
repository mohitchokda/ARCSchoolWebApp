package com.example.arc.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig{
	//Before this we use to take help of WebSecurityConfigurerAdapter 
	
	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	public SecurityFilterChain applySecurity(HttpSecurity http) throws Exception {
		
		//Handle CSRF using Spring
		//By default it is implemented for all post put reqs
		//we can modify by using below logic
		http.csrf(c -> c.ignoringRequestMatchers("/saveMsg"))//ignoringRequestMatchers(PathRequest.toH2Console()))
		.authorizeHttpRequests(req-> req.requestMatchers("/assets/**").permitAll()
											.requestMatchers("","/","/home").permitAll()
											.requestMatchers("/login").permitAll()
											.requestMatchers("/courses").permitAll()
											//.requestMatchers("/h2-console/**").permitAll()
											.requestMatchers("/saveMsg").permitAll()
											.requestMatchers("/logout").permitAll()
											.requestMatchers("/holidays/**").permitAll()
											.requestMatchers("/public/**").permitAll()
											.requestMatchers("/dashboard").authenticated()
											.requestMatchers("/displayProfile").authenticated()
											.requestMatchers("admin/**").hasRole("ADMIN")
											.requestMatchers("admin/closeMsg/**").hasRole("ADMIN")
											.requestMatchers("admin/displayMessages/**").hasRole("ADMIN")
											.anyRequest().permitAll())
		//Basic authentication
		.httpBasic(Customizer.withDefaults())
		//specifying custom login URL handlers
		.formLogin(form->form.loginPage("/login")
					.defaultSuccessUrl("/dashboard")
					.failureUrl("/login?error=true")
					.permitAll())
		//Logout details and mapping to Url Handler (through controller)
		.logout(logout-> logout.logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true).permitAll());
		
		http.headers(headers -> headers.frameOptions( fo -> fo.disable()));
		
		return http.build();
	}
	//before we had to override configure method and use configure(AuthenticationManagerBuilder auth)
	
	
	//In Memory Authentication for custom user details along with roles
	public UserDetailsService usersDetails() {
		UserDetails user = User.withUsername("user")
			.password(passwordEncoder().encode("password"))
			.roles("USER")
			.build();
		UserDetails admin = User.withUsername("admin")
			.password(passwordEncoder().encode("admin"))
			.roles("ADMIN")
			.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	
	//For password encoding we can use PasswordEncoder and this can be called under users data
	//To Store and safeguard pwds we using Hashing 
	//This is done using PwdEncoder in Spring
	@Bean
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
}
