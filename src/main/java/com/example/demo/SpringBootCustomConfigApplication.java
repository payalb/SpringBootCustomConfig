package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
//@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
public class SpringBootCustomConfigApplication {

	public static void main(String[] args) {
		ApplicationContext cx=SpringApplication.run(SpringBootCustomConfigApplication.class, args);
		System.out.println(cx.getBean(DataSource.class));
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer cfg() {
		PropertySourcesPlaceholderConfigurer cfg= new PropertySourcesPlaceholderConfigurer();
		return cfg;
	}
	
	/*@Bean
	public DataSource ds() {
		
	}
*/
}
