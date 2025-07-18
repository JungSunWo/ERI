package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpLstVO;
import com.ERI.demo.dto.PageRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpLstMapper {
    
    /**
     * 직원 목록 조회 (페이징) - 삭제되지 않은 데이터만
     */
    List<EmpLstVO> selectEmpLstListWithPaging(@Param("empLstVO") EmpLstVO empLstVO, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 직원 목록 수 조회 - 삭제되지 않은 데이터만
     */
    long selectEmpLstListCount(@Param("empLstVO") EmpLstVO empLstVO, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 직원 목록 조회 (페이징 없음) - 삭제되지 않은 데이터만
     */
    List<EmpLstVO> selectEmpLstList(EmpLstVO empLstVO);
    
    /**
     * 직원 상세 조회 - 삭제되지 않은 데이터만
     */
    EmpLstVO selectEmpLst(String empId);
    
    /**
     * 직원 등록
     */
    int insertEmpLst(EmpLstVO empLstVO);
    
    /**
     * 직원 수정
     */
    int updateEmpLst(EmpLstVO empLstVO);
    
    /**
     * 직원 논리적 삭제
     */
    int deleteEmpLst(String empId);
    
    /**
     * 직원 삭제 복구
     */
    int restoreEmpLst(String empId);
    
    /**
     * 삭제된 직원 목록 조회
     */
    List<EmpLstVO> selectDeletedEmpLstList(EmpLstVO empLstVO);
    
    /**
     * 삭제된 직원 상세 조회
     */
    EmpLstVO selectDeletedEmpLst(String empId);
    
    /**
     * 직원번호로 직원 조회 (삭제 여부 무관)
     */
    EmpLstVO selectByEmpId(String empId);
    
    /**
     * 직원 등록 (Batch용)
     */
    int insert(EmpLstVO empLstVO);
    
    /**
     * 직원 수정 (Batch용)
     */
    int update(EmpLstVO empLstVO);
    
    /**
     * 전체 직원 수 조회 (삭제되지 않은 데이터만)
     */
    int selectTotalCount();
} 