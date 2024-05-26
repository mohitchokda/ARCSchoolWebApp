package com.example.arc.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.arc.model.Person;
import com.example.arc.model.Roles;
import com.example.arc.repositories.PersonRepository;

@Component
public class UserPwdAuthentication implements AuthenticationProvider {
	
	@Autowired
	PasswordEncoder pwdEncoder;
	
	@Autowired
	PersonRepository personRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		Person p = personRepository.findByEmail(email);
		//System.out.println(pwd+" "+p.getPwd());
		//We use Matches method of pwd encoder to check for Hashed Pwd value
		if(p!=null && pwdEncoder.matches(pwd,p.getPwd())){//raw ,hashed
			//Authentication is Successful
			return new UsernamePasswordAuthenticationToken(email,null,mapRoles(p.getRoles()));
		}else {
			throw new BadCredentialsException("Invalid Credentials");
		}
	}
	
	private List<GrantedAuthority> mapRoles(Roles role){
		List<GrantedAuthority> list=new ArrayList<>();
		list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		return list;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
