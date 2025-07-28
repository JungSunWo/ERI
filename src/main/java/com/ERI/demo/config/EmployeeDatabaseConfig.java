package com.ERI.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 직원 정보 데이터베이스 연결 설정
 * eri_employee_db (포트: 5433) 연결을 위한 설정
 */
@Configuration
public class EmployeeDatabaseConfig {

    /**
     * 직원 정보 데이터베이스 연결 (eri_employee_db)
     */
    @Bean(name = "employeeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.employee")
    public DataSource employeeDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 직원 정보 DB JdbcTemplate
     */
    @Bean(name = "employeeJdbcTemplate")
    public JdbcTemplate employeeJdbcTemplate(@Qualifier("employeeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
} 