package com.tennis.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({"com.tennis.controller","com.tennis.service","com.tennis.dao","com.tennis.dao.impl",
	"com.tennis.service.impl","com.tennis.model"})
@Import({ SecurityConfig.class, DataSourceConfig.class })
@PropertySource("/WEB-INF/db.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	
 @Resource
 private Environment env;
 
 @Bean
 public InternalResourceViewResolver viewResolver() {
 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
 viewResolver.setViewClass(JstlView.class);
 viewResolver.setPrefix("/WEB-INF/views/");
 viewResolver.setSuffix(".jsp");
 return viewResolver;
 }

 @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
     registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
     registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
} 

 @Bean(name = "multipartResolver")
 public CommonsMultipartResolver getCommonsMultipartResolver() {
     CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
     multipartResolver.setMaxUploadSize(10485760);   // 10MB
     multipartResolver.setMaxInMemorySize(1048576);  // 1MB
     return multipartResolver;
 }  
 }