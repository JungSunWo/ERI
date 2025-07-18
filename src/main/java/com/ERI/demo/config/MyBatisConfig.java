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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 메인 데이터베이스 전용 MyBatis 설정 클래스
 * eri_db 데이터베이스 연결을 위한 설정
 */
@Configuration
@MapperScan(
    basePackages = "com.ERI.demo.mappers",
    sqlSessionFactoryRef = "mainSqlSessionFactory",
    sqlSessionTemplateRef = "mainSqlSessionTemplate"
)
public class MyBatisConfig {

    /**
     * 메인 데이터베이스 데이터소스 설정
     */
    @Bean(name = "mainDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 메인 데이터베이스 트랜잭션 매니저
     */
    @Bean(name = "mainTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("mainDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 메인 데이터베이스 MyBatis SqlSessionFactory
     */
    @Bean(name = "mainSqlSessionFactory")
    @Primary
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // 메인 데이터베이스 매퍼 XML 파일 위치 설정 (암호화 관련 제외)
        sessionFactory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml")
        );
        
        // VO 클래스 패키지 설정
        sessionFactory.setTypeAliasesPackage("com.ERI.demo.vo");
        
        // MyBatis 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        
        // SQL 로깅 설정 개선
        configuration.setLogPrefix("==> ");
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
        configuration.setLogPrefix("==> ");
        
        sessionFactory.setConfiguration(configuration);
        
        return sessionFactory.getObject();
    }

    /**
     * 메인 데이터베이스 SqlSessionTemplate
     */
    @Bean(name = "mainSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mainSqlSessionTemplate(
            @Qualifier("mainSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
} 