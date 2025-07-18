package com.ERI.demo.mappers.encryption;

import com.ERI.demo.vo.EncryptHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 암호화 이력 매퍼 (암호화 데이터베이스 전용)
 * eri_enc_db 데이터베이스의 TB_ENCRYPT_HISTORY 테이블 접근
 */
@Mapper
public interface EncryptHistoryMapper {

    /**
     * 암호화 이력 저장
     */
    int insertEncryptHistory(EncryptHistoryVO encryptHistoryVO);

    /**
     * 직원별 암호화 이력 조회
     */
    List<EncryptHistoryVO> selectHistoryByEmpSeq(@Param("empSeq") Long empSeq);

    /**
     * 전체 암호화 이력 조회
     */
    List<EncryptHistoryVO> selectAllEncryptHistory();

    /**
     * 기간별 암호화 이력 조회
     */
    List<EncryptHistoryVO> selectHistoryByDateRange(
        @Param("startDate") String startDate, 
        @Param("endDate") String endDate
    );

    /**
     * 작업 타입별 암호화 이력 조회
     */
    List<EncryptHistoryVO> selectHistoryByOperationType(@Param("operationType") String operationType);

    /**
     * 최근 암호화 이력 조회 (최대 100건)
     */
    List<EncryptHistoryVO> selectRecentEncryptHistory(@Param("limit") int limit);

    /**
     * 운영자별 암호화 이력 조회
     */
    List<EncryptHistoryVO> selectEncryptHistoryByOperatorSeq(@Param("operatorSeq") Long operatorSeq);
} 