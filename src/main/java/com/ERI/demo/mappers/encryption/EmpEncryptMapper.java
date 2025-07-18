package com.ERI.demo.mappers.encryption;

import com.ERI.demo.vo.EmpEncryptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원 암호화 정보 매퍼 (암호화 데이터베이스 전용)
 * eri_enc_db 데이터베이스의 TB_EMP_ENCRYPT 테이블 접근
 */
@Mapper
public interface EmpEncryptMapper {

    /**
     * 직원 암호화 정보 저장
     */
    int insertEmpEncrypt(EmpEncryptVO empEncryptVO);

    /**
     * 직원 암호화 정보 업데이트
     */
    int updateEmpEncrypt(EmpEncryptVO empEncryptVO);

    /**
     * 직원 암호화 정보 조회 (직원번호로)
     */
    EmpEncryptVO selectByEmpNo(@Param("origEmpNo") String origEmpNo);

    /**
     * 직원 암호화 정보 조회 (해시된 직원번호로)
     */
    EmpEncryptVO selectByEncryptEmpNo(@Param("encryptEmpNo") String encryptEmpNo);

    /**
     * 모든 직원 암호화 정보 조회
     */
    List<EmpEncryptVO> selectAllEmpEncrypt();

    /**
     * 직원 암호화 정보 삭제 (논리적 삭제)
     */
    int deleteEmpEncrypt(@Param("empSeq") Long empSeq);

    /**
     * 직원 암호화 정보 복구
     */
    int restoreEmpEncrypt(@Param("empSeq") Long empSeq);

    /**
     * 직원번호로 중복 체크
     */
    int countByEmpNo(@Param("origEmpNo") String origEmpNo);

    /**
     * 해시된 직원번호로 중복 체크
     */
    int countByEncryptEmpNo(@Param("encryptEmpNo") String encryptEmpNo);

    // 추가: 서비스에서 사용하는 모든 메서드 시그니처
    List<EmpEncryptVO> selectAllEmpEncryptWithDeleted();
    EmpEncryptVO selectEmpEncryptBySeq(@Param("empSeq") Long empSeq);
    EmpEncryptVO selectEmpEncryptBySeqWithDeleted(@Param("empSeq") Long empSeq);
    EmpEncryptVO selectEmpEncryptByOrigEmpNo(@Param("origEmpNo") String origEmpNo);
    List<EmpEncryptVO> selectEmpEncryptByOrigEmpNm(@Param("origEmpNm") String origEmpNm);
    List<EmpEncryptVO> selectEmpEncryptByRandomEmpNm(@Param("randomEmpNm") String randomEmpNm);
    int deleteEmpEncryptBySeq(@Param("empSeq") Long empSeq);
    int restoreEmpEncryptBySeq(@Param("empSeq") Long empSeq);
    int deleteEmpEncryptByOrigEmpNo(@Param("origEmpNo") String origEmpNo);
    List<EmpEncryptVO> selectEmpEncryptByAlgorithm(@Param("encryptAlgorithm") String encryptAlgorithm);
    List<EmpEncryptVO> selectEmpEncryptByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
    List<EmpEncryptVO> searchEmpEncrypt(@Param("origEmpNo") String origEmpNo, @Param("origEmpNm") String origEmpNm);
    List<EmpEncryptVO> selectDeletedEmpEncrypt();
    int countAllEmpEncrypt();
    int countAllEmpEncryptWithDeleted();
    int countDeletedEmpEncrypt();
    int countEmpEncryptByAlgorithm(@Param("encryptAlgorithm") String encryptAlgorithm);
    
    // 스케줄러 서비스에서 사용하는 메서드들
    int selectTotalCount();
    int selectMonthlyNewCount();
    int update(EmpEncryptVO empEncryptVO);
    int insert(EmpEncryptVO empEncryptVO);
    
    /**
     * 이메일만 업데이트 (직원번호와 암호화된 직원번호가 동일한 경우)
     */
    int updateEmailOnly(@Param("empSeq") Long empSeq, @Param("origEmpEmail") String origEmpEmail);
} 