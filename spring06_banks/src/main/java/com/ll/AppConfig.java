package com.ll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages="com.ll")
@EnableAspectJAutoProxy
public class AppConfig {

	@Bean    //      id: dataSource    value:  DriverManagerDataSource
	public  DriverManagerDataSource   dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:ORCL");
		dataSource.setUsername("scott");
		dataSource.setPassword("a");
		return dataSource;
	}
	
	//@Bean
	public  DriverManagerDataSource   mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/bank");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
	
}