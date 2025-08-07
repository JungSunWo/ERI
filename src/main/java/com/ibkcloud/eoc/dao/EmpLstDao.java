package com.ibkcloud.eoc.dao;

import com.ibkcloud.eoc.dao.dto.EmpLstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일명 : EmpLstDao
 * @논리명 : 직원 목록 DAO
 * @작성자 : 시스템
 * @설명   : 직원 정보 DB의 TB_EMP_LST 테이블을 처리하는 DAO 인터페이스
 */
@Mapper
public interface EmpLstDao {
    
    /**
     * 전체 직원 목록 조회
     */
    List<EmpLstDto> selectAllEmployees();
    
    /**
     * ERI 직원 ID로 직원 정보 조회
     */
    EmpLstDto selectEmployeeByEriId(String eriEmpId);
    
    /**
     * 직원 ID로 직원 정보 조회
     */
    EmpLstDto selectEmployeeByEmpId(String empId);
    
    /**
     * 직원 정보 등록
     */
    int insertEmployee(EmpLstDto employee);
    
    /**
     * 직원 정보 수정
     */
    int updateEmployee(EmpLstDto employee);
    
    /**
     * 직원 정보 삭제
     */
    int deleteEmployee(String eriEmpId);
    
    /**
     * 직원 수 조회
     */
    int selectEmployeeCount();
    
    /**
     * 지점별 직원 목록 조회
     */
    List<EmpLstDto> selectEmployeesByBranch(String branchCd);
} 