package com.graymatter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Bean
	public UserDetailsService getUserDetails(PasswordEncoder encoder) {
		UserDetails admin=User.withUsername("suhani")
				.password(encodePassword().encode("suhani"))
				.roles("admin")
				.build();
		
		UserDetails user1=User.withUsername("user1")
				.password(encodePassword().encode("suhani"))
				.roles("admin","user1")
				.build();
		
		UserDetails user2=User.withUsername("user2")
				.password(encodePassword().encode("suhani"))
				.roles("admin","user2")
				.build();
		
		
		return new InMemoryUserDetailsManager(admin,user1,user2);
	}
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf().disable()
//				.authorizeHttpRequests()
//				.requestMatchers("/demo").permitAll()
//				.and()
//				.authorizeHttpRequests()
//				.requestMatchers("/name/**")
//				.authenticated()
//				.and()
//				.formLogin()
//				.and()
//				.build();
		
		http.authorizeHttpRequests(auth->
		auth.requestMatchers("/demo").hasAnyRole("admin","User")
		.requestMatchers("/**").hasRole("admin")
		.anyRequest().authenticated())
		.formLogin();
		return http.build();
	}
}

