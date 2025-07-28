package com.ERI.demo.mappers.employee;

import com.ERI.demo.vo.employee.EmpLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원 정보 DB의 TB_EMP_LST 테이블 Mapper
 * eri_employee_db 연결을 사용
 */
@Mapper
public interface EmpLstMapper {

    /**
     * 전체 직원 목록 조회
     */
    List<EmpLstVO> selectAllEmployees();

    /**
     * ERI_EMP_ID로 직원 정보 조회
     */
    EmpLstVO selectEmployeeByEriId(@Param("eriEmpId") String eriEmpId);

    /**
     * EMP_ID로 직원 정보 조회
     */
    EmpLstVO selectEmployeeByEmpId(@Param("empId") String empId);

    /**
     * 부점별 직원 목록 조회
     */
    List<EmpLstVO> selectEmployeesByBranch(@Param("branchCd") String branchCd);

    /**
     * 직원 정보 등록
     */
    int insertEmployee(EmpLstVO employee);

    /**
     * 직원 정보 수정
     */
    int updateEmployee(EmpLstVO employee);

    /**
     * 직원 정보 삭제 (논리 삭제)
     */
    int deleteEmployee(@Param("eriEmpId") String eriEmpId);

    /**
     * 전체 직원 수 조회
     */
    int countAllEmployees();

    /**
     * 조건별 직원 검색
     */
    List<EmpLstVO> searchEmployees(@Param("searchKeyword") String searchKeyword, 
                                   @Param("branchCd") String branchCd, 
                                   @Param("jbclCd") String jbclCd,
                                   @Param("jbttCd") String jbttCd,
                                   @Param("hlofYn") String hlofYn);

    /**
     * 페이징된 직원 목록 조회 (관리자 등록용)
     */
    List<EmpLstVO> selectEmployeesWithPaging(@Param("searchKeyword") String searchKeyword,
                                             @Param("offset") int offset,
                                             @Param("size") int size,
                                             @Param("sort") String sort);

    /**
     * 검색 조건별 직원 수 조회 (관리자 등록용)
     */
    int countEmployees(@Param("searchKeyword") String searchKeyword);

    /**
     * 직원 ID로 직원 정보 조회 (관리자 등록용)
     */
    EmpLstVO selectEmployeeById(@Param("empId") String empId);
} 