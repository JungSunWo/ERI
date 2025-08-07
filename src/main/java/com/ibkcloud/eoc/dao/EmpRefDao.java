package com.ibkcloud.eoc.dao;

import com.ibkcloud.eoc.dao.dto.EmpRefDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일명 : EmpRefDao
 * @논리명 : 직원 참조 DAO
 * @작성자 : 시스템
 * @설명   : 직원 참조 정보를 처리하는 DAO 인터페이스
 */
@Mapper
public interface EmpRefDao {
    
    /**
     * 전체 직원 참조 목록 조회
     */
    List<EmpRefDto> selectAllEmpRefs();
    
    /**
     * 직원 ID로 직원 참조 정보 조회
     */
    EmpRefDto selectEmpRefById(String empId);
    
    /**
     * 직원 참조 정보 등록
     */
    int insertEmpRef(EmpRefDto empRef);
    
    /**
     * 직원 참조 정보 수정
     */
    int updateEmpRef(EmpRefDto empRef);
    
    /**
     * 직원 참조 정보 삭제
     */
    int deleteEmpRef(String empId);
} 