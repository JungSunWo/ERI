package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpNmEncryptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원명 암호화 Mapper
 */
@Mapper
public interface EmpNmEncryptMapper {
    
    /**
     * 전체 직원명 암호화 데이터 조회
     */
    List<EmpNmEncryptVO> selectAll();
    
    /**
     * 직원 ID로 직원명 암호화 데이터 조회
     */
    EmpNmEncryptVO selectByEmpId(@Param("empId") String empId);
    
    /**
     * 직원명 암호화 데이터 등록
     */
    int insert(EmpNmEncryptVO empNmEncryptVO);
    
    /**
     * 직원명 암호화 데이터 수정
     */
    int update(EmpNmEncryptVO empNmEncryptVO);
    
    /**
     * 직원명 암호화 데이터 삭제
     */
    int deleteByEmpId(@Param("empId") String empId);
    
    /**
     * 암호화된 직원명으로 원본 직원명 조회
     */
    EmpNmEncryptVO selectByEncryptNm(@Param("encryptNm") String encryptNm);
    
    /**
     * 원본 직원명으로 암호화 데이터 조회
     */
    EmpNmEncryptVO selectByOrigNm(@Param("origNm") String origNm);
    
    /**
     * 암호화 알고리즘별 데이터 조회
     */
    List<EmpNmEncryptVO> selectByAlgorithm(@Param("algorithm") String algorithm);
    
    /**
     * 암호화 키 ID별 데이터 조회
     */
    List<EmpNmEncryptVO> selectByKeyId(@Param("keyId") String keyId);
} 