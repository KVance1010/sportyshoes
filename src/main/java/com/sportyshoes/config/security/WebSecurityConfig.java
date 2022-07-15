package com.sportyshoes.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsSecurityService();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/users/**").hasAuthority("Admin").antMatchers("/categories/**")
				.hasAnyAuthority("Admin", "Manager").anyRequest().authenticated().and().formLogin().loginPage("/login")
				.usernameParameter("email").permitAll().and().logout().permitAll().and().rememberMe()
				.key("AbcDefgHijKlmnOpqrs_1234567890").tokenValiditySeconds(7 * 24 * 60 * 60);
		;

		// http.authorizeRequests().antMatchers("/users/**").hasAuthority("Admin").antMatchers("/categories/**")
//				.hasAnyAuthority("Admin", "Manager").anyRequest().authenticated().and().formLogin().loginPage("/login")
//				.usernameParameter("email").permitAll().and().logout().permitAll().and().rememberMe()
//				.key("AbcDefgHijKlmnOpqrs_1234567890").tokenValiditySeconds(7 * 24 * 60 * 60);
//		;

//	    http.authorizeRequests().anyRequest().permitAll();
		return http.build();

	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
}
