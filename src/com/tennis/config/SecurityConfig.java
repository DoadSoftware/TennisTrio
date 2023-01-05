package com.tennis.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
 @SuppressWarnings("unused")
 @Autowired
 private DataSource dataSource;

 @Override
 protected void configure(HttpSecurity http) throws Exception 
 {
	  http
	  	.authorizeRequests()
	  	.antMatchers("/")
	  	.permitAll()
	  	.and()
	  	.csrf().disable();
 }
 
}