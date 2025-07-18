package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpNoEncryptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원번호 암호화 Mapper
 */
@Mapper
public interface EmpNoEncryptMapper {
    
    /**
     * 전체 직원번호 암호화 데이터 조회
     */
    List<EmpNoEncryptVO> selectAll();
    
    /**
     * 직원 ID로 직원번호 암호화 데이터 조회
     */
    EmpNoEncryptVO selectByEmpId(@Param("empId") String empId);
    
    /**
     * 직원번호 암호화 데이터 등록
     */
    int insert(EmpNoEncryptVO empNoEncryptVO);
    
    /**
     * 직원번호 암호화 데이터 수정
     */
    int update(EmpNoEncryptVO empNoEncryptVO);
    
    /**
     * 직원번호 암호화 데이터 삭제
     */
    int deleteByEmpId(@Param("empId") String empId);
    
    /**
     * 암호화된 직원번호로 원본 직원번호 조회
     */
    EmpNoEncryptVO selectByEncryptEmpNo(@Param("encryptEmpNo") String encryptEmpNo);
    
    /**
     * 원본 직원번호로 암호화 데이터 조회
     */
    EmpNoEncryptVO selectByOrigEmpNo(@Param("origEmpNo") String origEmpNo);
    
    /**
     * 암호화 알고리즘별 데이터 조회
     */
    List<EmpNoEncryptVO> selectByAlgorithm(@Param("algorithm") String algorithm);
    
    /**
     * 암호화 키 ID별 데이터 조회
     */
    List<EmpNoEncryptVO> selectByKeyId(@Param("keyId") String keyId);
} 