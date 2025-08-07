package com.ibkcloud.eoc.service;

import com.ibkcloud.eoc.dao.EmpLstDao;
import com.ibkcloud.eoc.dao.EmpRefDao;
import com.ibkcloud.eoc.dao.dto.EmpLstDto;
import com.ibkcloud.eoc.dao.dto.EmpRefDto;
import com.ibkcloud.eoc.cmm.util.EncryptionUtil;
import com.ibkcloud.eoc.cmm.exception.BizException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @파일명 : EmpLstService
 * @논리명 : 직원 목록 서비스
 * @작성자 : 시스템
 * @설명   : 직원 정보 DB의 TB_EMP_LST 테이블을 처리하는 Service 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmpLstService {
    
    private final EmpLstDao empLstDao;
    private final EmpRefDao empRefDao;
    private final JdbcTemplate employeeJdbcTemplate;
    
    /**
     * @메서드명 : inqAllEmployees
     * @논리명 : 전체 직원 목록 조회
     * @설명 : 전체 직원 목록을 조회 (복호화된 데이터)
     * @return : List<EmpLstDto> - 직원 목록
     */
    public List<EmpLstDto> inqAllEmployees() {
        log.info("전체 직원 목록 조회 시작");
        
        try {
            List<EmpLstDto> employees = empLstDao.selectAllEmployees();
            
            // 이메일과 전화번호 복호화
            for (EmpLstDto employee : employees) {
                decryptEmployeeData(employee);
            }
            
            log.info("전체 직원 목록 조회 완료 - 건수: {}", employees.size());
            return employees;
            
        } catch (Exception e) {
            log.error("전체 직원 목록 조회 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : inqEmployeeByEriId
     * @논리명 : ERI 직원 ID로 직원 정보 조회
     * @설명 : ERI_EMP_ID로 직원 정보를 조회 (복호화된 데이터)
     * @param : eriEmpId - ERI 직원 ID
     * @return : EmpLstDto - 직원 정보
     */
    public EmpLstDto inqEmployeeByEriId(String eriEmpId) {
        log.info("ERI 직원 ID로 직원 정보 조회 시작 - ERI_EMP_ID: {}", eriEmpId);
        
        try {
            EmpLstDto employee = empLstDao.selectEmployeeByEriId(eriEmpId);
            if (employee != null) {
                decryptEmployeeData(employee);
            }
            
            log.info("ERI 직원 ID로 직원 정보 조회 완료 - ERI_EMP_ID: {}", eriEmpId);
            return employee;
            
        } catch (Exception e) {
            log.error("ERI 직원 ID로 직원 정보 조회 중 오류 발생 - ERI_EMP_ID: {}", eriEmpId, e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : inqEmployeeByEmpId
     * @논리명 : 직원 ID로 직원 정보 조회
     * @설명 : EMP_ID로 직원 정보를 조회 (복호화된 데이터)
     * @param : empId - 직원 ID
     * @return : EmpLstDto - 직원 정보
     */
    public EmpLstDto inqEmployeeByEmpId(String empId) {
        log.info("직원 ID로 직원 정보 조회 시작 - EMP_ID: {}", empId);
        
        try {
            EmpLstDto employee = empLstDao.selectEmployeeByEmpId(empId);
            if (employee != null) {
                decryptEmployeeData(employee);
            }
            
            log.info("직원 ID로 직원 정보 조회 완료 - EMP_ID: {}", empId);
            return employee;
            
        } catch (Exception e) {
            log.error("직원 ID로 직원 정보 조회 중 오류 발생 - EMP_ID: {}", empId, e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : rgsnEmployee
     * @논리명 : 직원 정보 등록
     * @설명 : 새로운 직원 정보를 등록
     * @param : employee - 등록할 직원 정보
     * @return : int - 등록된 건수
     */
    @Transactional(rollbackFor = Exception.class)
    public int rgsnEmployee(EmpLstDto employee) {
        log.info("직원 정보 등록 시작 - EMP_ID: {}", employee.getEmpId());
        
        try {
            // 이메일과 전화번호 암호화
            encryptEmployeeData(employee);
            
            int result = empLstDao.insertEmployee(employee);
            
            log.info("직원 정보 등록 완료 - EMP_ID: {}, 결과: {}", employee.getEmpId(), result);
            return result;
            
        } catch (Exception e) {
            log.error("직원 정보 등록 중 오류 발생 - EMP_ID: {}", employee.getEmpId(), e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : mdfcEmployee
     * @논리명 : 직원 정보 수정
     * @설명 : 기존 직원 정보를 수정
     * @param : employee - 수정할 직원 정보
     * @return : int - 수정된 건수
     */
    @Transactional(rollbackFor = Exception.class)
    public int mdfcEmployee(EmpLstDto employee) {
        log.info("직원 정보 수정 시작 - EMP_ID: {}", employee.getEmpId());
        
        try {
            // 이메일과 전화번호 암호화
            encryptEmployeeData(employee);
            
            int result = empLstDao.updateEmployee(employee);
            
            log.info("직원 정보 수정 완료 - EMP_ID: {}, 결과: {}", employee.getEmpId(), result);
            return result;
            
        } catch (Exception e) {
            log.error("직원 정보 수정 중 오류 발생 - EMP_ID: {}", employee.getEmpId(), e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : delEmployee
     * @논리명 : 직원 정보 삭제
     * @설명 : 직원 정보를 삭제
     * @param : eriEmpId - 삭제할 ERI 직원 ID
     * @return : int - 삭제된 건수
     */
    @Transactional(rollbackFor = Exception.class)
    public int delEmployee(String eriEmpId) {
        log.info("직원 정보 삭제 시작 - ERI_EMP_ID: {}", eriEmpId);
        
        try {
            int result = empLstDao.deleteEmployee(eriEmpId);
            
            log.info("직원 정보 삭제 완료 - ERI_EMP_ID: {}, 결과: {}", eriEmpId, result);
            return result;
            
        } catch (Exception e) {
            log.error("직원 정보 삭제 중 오류 발생 - ERI_EMP_ID: {}", eriEmpId, e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : inqEmployeeCount
     * @논리명 : 직원 수 조회
     * @설명 : 전체 직원 수를 조회
     * @return : int - 직원 수
     */
    public int inqEmployeeCount() {
        log.info("직원 수 조회 시작");
        
        try {
            int count = empLstDao.selectEmployeeCount();
            
            log.info("직원 수 조회 완료 - 건수: {}", count);
            return count;
            
        } catch (Exception e) {
            log.error("직원 수 조회 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : inqEmployeesByBranch
     * @논리명 : 지점별 직원 목록 조회
     * @설명 : 특정 지점의 직원 목록을 조회
     * @param : branchCd - 지점 코드
     * @return : List<EmpLstDto> - 직원 목록
     */
    public List<EmpLstDto> inqEmployeesByBranch(String branchCd) {
        log.info("지점별 직원 목록 조회 시작 - 지점코드: {}", branchCd);
        
        try {
            List<EmpLstDto> employees = empLstDao.selectEmployeesByBranch(branchCd);
            
            // 이메일과 전화번호 복호화
            for (EmpLstDto employee : employees) {
                decryptEmployeeData(employee);
            }
            
            log.info("지점별 직원 목록 조회 완료 - 지점코드: {}, 건수: {}", branchCd, employees.size());
            return employees;
            
        } catch (Exception e) {
            log.error("지점별 직원 목록 조회 중 오류 발생 - 지점코드: {}", branchCd, e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * 직원 데이터 복호화 헬퍼 메서드
     */
    private void decryptEmployeeData(EmpLstDto employee) {
        if (employee.getEad() != null && EncryptionUtil.isEncrypted(employee.getEad())) {
            employee.setEad(EncryptionUtil.decryptEmail(employee.getEad()));
        }
        if (employee.getEmpCpn() != null && EncryptionUtil.isEncrypted(employee.getEmpCpn())) {
            employee.setEmpCpn(EncryptionUtil.decryptPhone(employee.getEmpCpn()));
        }
    }
    
    /**
     * 직원 데이터 암호화 헬퍼 메서드
     */
    private void encryptEmployeeData(EmpLstDto employee) {
        if (employee.getEad() != null && !EncryptionUtil.isEncrypted(employee.getEad())) {
            employee.setEad(EncryptionUtil.encryptEmail(employee.getEad()));
        }
        if (employee.getEmpCpn() != null && !EncryptionUtil.isEncrypted(employee.getEmpCpn())) {
            employee.setEmpCpn(EncryptionUtil.encryptPhone(employee.getEmpCpn()));
        }
    }
} 