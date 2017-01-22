package com.student.room.server.config;
import java.sql.SQLException;

import javax.sql.DataSource;


import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
@Configuration  
@EnableTransactionManagement 
public class DruidDataSourceConfig {
	
	 @Bean
	    public DataSource dataSource() {
	        System.out.println("注入druid！！！");
	        DruidDataSource datasource = new DruidDataSource();
	        try {
				datasource.setFilters("stat,wall");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        return datasource;
	    }

}
