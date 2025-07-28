package com.ERI.demo.service;

import com.ERI.demo.mappers.employee.EmpLstMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLstTableCheckService {

    private static final Logger logger = LoggerFactory.getLogger(EmpLstTableCheckService.class);

    @Autowired
    private EmpLstMapper empLstMapper;

    @Transactional(transactionManager = "employeeTransactionManager", rollbackFor = Exception.class)
    public int getEmpLstCount() {
        try {
            logger.info("eri_employee_db TB_EMP_LST 테이블 카운트 조회 시작");
            int count = empLstMapper.countEmployees();
            logger.info("eri_employee_db TB_EMP_LST 테이블 카운트: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("eri_employee_db TB_EMP_LST 테이블 카운트 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("eri_employee_db TB_EMP_LST 테이블 카운트 조회 실패: " + e.getMessage(), e);
        }
    }
} 