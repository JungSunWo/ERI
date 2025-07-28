package com.ERI.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 직원 정보 데이터베이스 전용 MyBatis 설정 클래스
 * eri_employee_db 데이터베이스 연결을 위한 설정
 */
@Configuration
@MapperScan(
    basePackages = "com.ERI.demo.mappers.employee",
    sqlSessionFactoryRef = "employeeSqlSessionFactory"
)
public class EmployeeMyBatisConfig {

    /**
     * 직원 정보 데이터베이스 트랜잭션 매니저
     */
    @Bean(name = "employeeTransactionManager")
    public DataSourceTransactionManager employeeTransactionManager(@Qualifier("employeeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 직원 정보 데이터베이스 MyBatis SqlSessionFactory
     */
    @Bean(name = "employeeSqlSessionFactory")
    public SqlSessionFactory employeeSqlSessionFactory(@Qualifier("employeeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // 직원 정보 데이터베이스 매퍼 XML 파일 위치 설정
        sessionFactory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mappers/employee/*.xml")
        );
        
        // VO 클래스 패키지 설정 (직원 DB 전용)
        sessionFactory.setTypeAliasesPackage("com.ERI.demo.vo.employee");
        
        // MyBatis 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        
        // SQL 로깅 설정 개선
        configuration.setLogPrefix("==> EMPLOYEE DB: ");
        configuration.setCallSettersOnNulls(true);
        configuration.setJdbcTypeForNull(null);
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setAggressiveLazyLoading(false);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseColumnLabel(true);
        configuration.setUseGeneratedKeys(false);
        configuration.setAutoMappingBehavior(org.apache.ibatis.session.AutoMappingBehavior.PARTIAL);
        configuration.setAutoMappingUnknownColumnBehavior(org.apache.ibatis.session.AutoMappingUnknownColumnBehavior.WARNING);
        configuration.setDefaultExecutorType(org.apache.ibatis.session.ExecutorType.SIMPLE);
        configuration.setDefaultStatementTimeout(25);
        configuration.setDefaultFetchSize(100);
        configuration.setSafeRowBoundsEnabled(false);
        configuration.setSafeResultHandlerEnabled(true);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLocalCacheScope(org.apache.ibatis.session.LocalCacheScope.SESSION);
        configuration.setJdbcTypeForNull(null);
        configuration.setLazyLoadTriggerMethods(Set.of("equals", "clone", "hashCode", "toString"));
        configuration.setReturnInstanceForEmptyRow(false);
        
        sessionFactory.setConfiguration(configuration);
        
        return sessionFactory.getObject();
    }

    /**
     * 직원 정보 데이터베이스 SqlSessionTemplate
     */
    @Bean(name = "employeeSqlSessionTemplate")
    public SqlSessionTemplate employeeSqlSessionTemplate(
            @Qualifier("employeeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

} 