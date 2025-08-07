package com.ibkcloud.eoc.dao;

import com.ibkcloud.eoc.dao.dto.CounselorLstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일명 : CounselorLstDao
 * @논리명 : 상담사 목록 DAO
 * @작성자 : 시스템
 * @설명   : 상담사 정보를 처리하는 DAO 인터페이스
 */
@Mapper
public interface CounselorLstDao {
    
    /**
     * 전체 상담사 목록 조회
     */
    List<CounselorLstDto> selectAllCounselors();
    
    /**
     * 상담사 ID로 상담사 정보 조회
     */
    CounselorLstDto selectCounselorById(String counselorId);
    
    /**
     * 상담사 정보 등록
     */
    int insertCounselor(CounselorLstDto counselor);
    
    /**
     * 상담사 정보 수정
     */
    int updateCounselor(CounselorLstDto counselor);
    
    /**
     * 상담사 정보 삭제
     */
    int deleteCounselor(String counselorId);
} 