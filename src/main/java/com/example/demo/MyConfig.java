package com.example.demo;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@ConditionalOnClass(DataSource.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MyConfig {
	@Autowired
	Environment env;//-Dspring.profiles.active=dev
//	@Value("#{systemProperties['spring.datasource.url']}") 
	String url;

	@Bean(name = "dataSource")
	@ConditionalOnMissingBean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		ds.setDriverClassName("org.postgresql.Driver");
		System.out.println(url);
		return ds;
	}

	@Bean
	@ConditionalOnBean(name = "dataSource")
	@ConditionalOnMissingBean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());
		em.setPackagesToScan("com.example.demo");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(additionalProperties());
		return em;
	}

	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		return hibernateProperties;
	}

}
