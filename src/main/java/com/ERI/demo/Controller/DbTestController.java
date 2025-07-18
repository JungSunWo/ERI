package com.ERI.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 데이터베이스 연결 테스트 API Controller
 */
@RestController
@RequestMapping("/api/db-test")
public class DbTestController {

    @Autowired
    @Qualifier("mainDataSource")
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    /**
     * 데이터베이스 연결 테스트
     */
    @PostMapping("/connection-test")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 1. DataSource 연결 테스트
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            
            Map<String, Object> connectionInfo = new HashMap<>();
            connectionInfo.put("databaseProductName", metaData.getDatabaseProductName());
            connectionInfo.put("databaseProductVersion", metaData.getDatabaseProductVersion());
            connectionInfo.put("driverName", metaData.getDriverName());
            connectionInfo.put("driverVersion", metaData.getDriverVersion());
            connectionInfo.put("url", metaData.getURL());
            connectionInfo.put("userName", metaData.getUserName());
            connectionInfo.put("isReadOnly", connection.isReadOnly());
            connectionInfo.put("autoCommit", connection.getAutoCommit());
            
            response.put("success", true);
            response.put("message", "데이터베이스 연결 성공");
            response.put("connectionInfo", connectionInfo);
            
            connection.close();
            
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "데이터베이스 연결 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Spring Batch 메타데이터 테이블 존재 여부 확인
     */
    @PostMapping(value = "/batch-tables-test", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testBatchTables() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String[] batchTables = {
                "BATCH_JOB_INSTANCE",
                "BATCH_JOB_EXECUTION", 
                "BATCH_JOB_EXECUTION_PARAMS",
                "BATCH_STEP_EXECUTION",
                "BATCH_STEP_EXECUTION_SEQUENCE",
                "BATCH_STEP_EXECUTION_CONTEXT",
                "BATCH_JOB_EXECUTION_CONTEXT"
            };
            
            Map<String, Boolean> tableStatus = new HashMap<>();
            
            for (String tableName : batchTables) {
                try {
                    String sql = "SELECT COUNT(*) FROM " + tableName;
                    jdbcTemplate.queryForObject(sql, Integer.class);
                    tableStatus.put(tableName, true);
                } catch (Exception e) {
                    tableStatus.put(tableName, false);
                }
            }
            
            response.put("success", true);
            response.put("message", "Spring Batch 테이블 확인 완료");
            response.put("tableStatus", tableStatus);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "테이블 확인 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * ERI 애플리케이션 테이블 존재 여부 확인
     */
    @PostMapping(value = "/eri-tables-test", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testEriTables() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String[] eriTables = {
                "TB_EMP_LST",
                "TB_EMP_ENCRYPT",
                "TB_CMN_GRP_CD",
                "TB_CMN_DTL_CD",
                "TB_DEPT_LST"
            };
            
            Map<String, Boolean> tableStatus = new HashMap<>();
            
            for (String tableName : eriTables) {
                try {
                    String sql = "SELECT COUNT(*) FROM " + tableName;
                    jdbcTemplate.queryForObject(sql, Integer.class);
                    tableStatus.put(tableName, true);
                } catch (Exception e) {
                    tableStatus.put(tableName, false);
                }
            }
            
            response.put("success", true);
            response.put("message", "ERI 애플리케이션 테이블 확인 완료");
            response.put("tableStatus", tableStatus);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "테이블 확인 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 간단한 쿼리 실행 테스트
     */
    @PostMapping(value = "/query-test", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testQuery() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 현재 시간 조회
            String currentTimeSql = "SELECT NOW() as current_time";
            String currentTime = jdbcTemplate.queryForObject(currentTimeSql, String.class);
            
            // 데이터베이스 버전 조회
            String versionSql = "SELECT version() as db_version";
            String version = jdbcTemplate.queryForObject(versionSql, String.class);
            
            // 활성 연결 수 조회
            String connectionsSql = "SELECT count(*) as active_connections FROM pg_stat_activity WHERE state = 'active'";
            Integer activeConnections = jdbcTemplate.queryForObject(connectionsSql, Integer.class);
            
            Map<String, Object> queryResults = new HashMap<>();
            queryResults.put("currentTime", currentTime);
            queryResults.put("version", version);
            queryResults.put("activeConnections", activeConnections);
            
            response.put("success", true);
            response.put("message", "쿼리 실행 성공");
            response.put("queryResults", queryResults);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "쿼리 실행 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
        }
        
        return ResponseEntity.ok(response);
    }
} 