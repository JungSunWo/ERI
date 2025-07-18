package com.ERI.demo.service;

import com.ERI.demo.mappers.EmpLstMapper;
import com.ERI.demo.vo.EmpLstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 직원 목록 서비스 (메인 데이터베이스 전용)
 * eri_db 데이터베이스의 TB_EMP_LST 테이블 접근
 */
@Service
@Transactional(transactionManager = "mainTransactionManager", rollbackFor = Exception.class)
public class EmpLstService {

    private static final Logger logger = LoggerFactory.getLogger(EmpLstService.class);

    @Autowired
    private EmpLstMapper empLstMapper;

    /**
     * 전체 직원 목록 조회 (캐싱용 - ID와 이름만)
     */
    public List<EmpLstVO> getAllEmployeesForCache() {
        try {
            logger.info("전체 직원 목록 조회 시작 (캐싱용)");
            
            // 삭제되지 않은 모든 직원 조회 (ID와 이름만)
            EmpLstVO searchCondition = new EmpLstVO();
            searchCondition.setDelYn("N"); // 삭제되지 않은 데이터만
            
            List<EmpLstVO> employees = empLstMapper.selectEmpLstList(searchCondition);
            
            logger.info("전체 직원 목록 조회 완료: {} 명", employees.size());
            return employees;
            
        } catch (Exception e) {
            logger.error("전체 직원 목록 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("전체 직원 목록 조회 실패", e);
        }
    }

    /**
     * 암호화된 직원정보를 TB_EMP_LST 테이블에 저장
     */
    public void saveEncryptedEmpInfo(EmpLstVO originalEmp, String hashedEmpId, String randomEmpNm) {
        try {
            logger.info("=== TB_EMP_LST 저장 시작 (메인 데이터베이스) ===");
            logger.info("원본번호: {}, 해시번호: {}, 랜덤이름: {}", originalEmp.getEmpId(), hashedEmpId, randomEmpNm);
            
            // 기존 데이터 확인 (해시된 직원번호로 조회)
            EmpLstVO existingEmp = empLstMapper.selectByEmpId(hashedEmpId);
            logger.info("기존 TB_EMP_LST 데이터 조회 결과: {}", existingEmp != null ? "존재" : "없음");
            
            // 새로운 직원정보 객체 생성 (암호화된 정보로)
            EmpLstVO encryptedEmp = new EmpLstVO();
            encryptedEmp.setEmpId(hashedEmpId);                    // 암호화된 직원번호
            encryptedEmp.setEmpNm(randomEmpNm);                    // 랜덤 변형 한글명
            encryptedEmp.setEmpDeptCd(originalEmp.getEmpDeptCd()); // 부서코드 (원본 유지)
            encryptedEmp.setEmpPosCd(originalEmp.getEmpPosCd());   // 직급코드 (원본 유지)
            encryptedEmp.setEmpStsCd(originalEmp.getEmpStsCd());   // 직원상태 (원본 유지)
            // TB_EMP_LST 테이블에는 EMP_EMAIL 컬럼이 없으므로 제외
            
            // 등록/수정 정보 설정
            encryptedEmp.setRgstDt(java.time.LocalDate.now());
            encryptedEmp.setRgstTm(java.time.LocalTime.now());
            encryptedEmp.setRgstEmpId("SCHEDULER_SYSTEM");
            encryptedEmp.setUpdtDt(java.time.LocalDate.now());
            encryptedEmp.setUpdtTm(java.time.LocalTime.now());
            encryptedEmp.setUpdtEmpId("SCHEDULER_SYSTEM");
            encryptedEmp.setDelYn("N");
            
            logger.info("TB_EMP_LST 정보 객체 생성 완료: 해시번호={}, 랜덤이름={}", hashedEmpId, randomEmpNm);
            
            if (existingEmp != null) {
                // 기존 데이터 업데이트
                logger.info("TB_EMP_LST 업데이트 쿼리 실행 시작...");
                int updateResult = empLstMapper.update(encryptedEmp);
                logger.info("TB_EMP_LST 업데이트 완료: {} (결과: {})", hashedEmpId, updateResult);
            } else {
                // 새 데이터 삽입
                logger.info("TB_EMP_LST 삽입 쿼리 실행 시작...");
                int insertResult = empLstMapper.insert(encryptedEmp);
                logger.info("TB_EMP_LST 삽입 완료: {} (결과: {})", hashedEmpId, insertResult);
            }
            
            logger.info("=== TB_EMP_LST 저장 완료 (메인 데이터베이스) ===");
            
        } catch (Exception e) {
            logger.error("TB_EMP_LST 저장 실패: {}, 오류: {}", hashedEmpId, e.getMessage(), e);
            throw new RuntimeException("TB_EMP_LST 저장 실패: " + hashedEmpId, e);
        }
    }
} 