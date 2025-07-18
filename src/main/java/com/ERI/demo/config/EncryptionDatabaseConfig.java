package com.ERI.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 암호화 데이터베이스 전용 설정 클래스
 * eri_enc_db 데이터베이스 연결을 위한 별도 설정
 */
@Configuration
@MapperScan(
    basePackages = "com.ERI.demo.mappers.encryption",
    sqlSessionFactoryRef = "encryptionSqlSessionFactory",
    sqlSessionTemplateRef = "encryptionSqlSessionTemplate"
)
public class EncryptionDatabaseConfig {

    /**
     * 암호화 데이터베이스 데이터소스 설정
     */
    @Bean(name = "encryptionDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.encryption")
    public DataSource encryptionDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 암호화 데이터베이스 트랜잭션 매니저
     */
    @Bean(name = "encryptionTransactionManager")
    public DataSourceTransactionManager encryptionTransactionManager(
            @Qualifier("encryptionDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 암호화 데이터베이스 MyBatis SqlSessionFactory
     */
    @Bean(name = "encryptionSqlSessionFactory")
    public SqlSessionFactory encryptionSqlSessionFactory(
            @Qualifier("encryptionDataSource") DataSource dataSource) throws Exception {
        
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // 암호화 관련 매퍼 XML 파일 위치 설정
        sessionFactory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mappers/encryption/*.xml")
        );
        
        // MyBatis 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        sessionFactory.setConfiguration(configuration);
        
        return sessionFactory.getObject();
    }

    /**
     * 암호화 데이터베이스 SqlSessionTemplate
     */
    @Bean(name = "encryptionSqlSessionTemplate")
    public SqlSessionTemplate encryptionSqlSessionTemplate(
            @Qualifier("encryptionSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
} 