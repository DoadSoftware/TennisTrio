package com.tennis.config;

import java.sql.SQLException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tennis.model.NameSuper;
import com.tennis.model.Player;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

 private static final String PROPERTY_NAME_DATABASE_DRIVER = "hibernate.connection.driver_class";
 private static final String PROPERTY_NAME_DATABASE_URL = "hibernate.connection.url";

 @Resource
 private Environment env;
 
@Bean
 public DataSource dataSource() {
	 
	 DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
	 dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
	 return dataSource;
 }
 
 @Bean
 public SessionFactory sessionFactory() 
 {
 LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
 builder.scanPackages("com.tennis.model").addProperties(getHibernateProperties());
 builder.addAnnotatedClasses(Player.class);
 builder.addAnnotatedClasses(NameSuper.class);
 return builder.buildSessionFactory();
 }
 
 private Properties getHibernateProperties() {
 Properties prop = new Properties();
 prop.put("hibernate.show_sql", "true");
 prop.put("hibernate.connection.pool_size", "5");
 prop.put("hibernate.hbm2ddl.auto", "update");
 prop.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
 return prop;
 }

 @Bean
 public HibernateTransactionManager transactionManager() throws SQLException {
 return new HibernateTransactionManager(sessionFactory());
 }
 
}