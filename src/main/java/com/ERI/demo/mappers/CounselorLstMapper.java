package com.ERI.demo.mappers;

import com.ERI.demo.vo.CounselorLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담사 목록 Mapper
 * TB_CNSLR_LST 테이블 관련 데이터베이스 작업
 */
@Mapper
public interface CounselorLstMapper {
    
    /**
     * 전체 상담사 목록 조회
     */
    List<CounselorLstVO> selectAllCounselorLst();
    
    /**
     * 페이징을 위한 상담사 목록 조회
     */
    List<CounselorLstVO> selectCounselorLstWithPaging(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size, @Param("sort") String sort);
    
    /**
     * 상담사 직원 ID로 상담사 정보 조회
     */
    CounselorLstVO selectCounselorByEmpId(@Param("counselorEmpId") String counselorEmpId);
    
    /**
     * 상담사 직원 ID로 상담사 정보 조회 (삭제된 데이터 포함)
     */
    CounselorLstVO selectCounselorByEmpIdWithDeleted(@Param("counselorEmpId") String counselorEmpId);
    
    /**
     * 상담자정보구분 코드로 상담사 목록 조회
     */
    List<CounselorLstVO> selectByCounselorInfoClsfCd(@Param("counselorInfoClsfCd") String counselorInfoClsfCd);
    
    /**
     * 상담사 정보 등록
     */
    int insertCounselor(CounselorLstVO counselorLst);
    
    /**
     * 상담사 정보 수정
     */
    int updateCounselor(CounselorLstVO counselorLst);
    
    /**
     * 상담사 직원 ID로 논리적 삭제
     */
    int deleteCounselor(@Param("counselorEmpId") String counselorEmpId);
    
    /**
     * 상담사 직원 ID로 복구
     */
    int restoreCounselorByEmpId(@Param("counselorEmpId") String counselorEmpId);
    
    /**
     * 전체 상담사 수 조회
     */
    int countAllCounselorLst();
    
    /**
     * 검색 조건에 따른 상담사 수 조회
     */
    int countCounselorLstWithSearch(@Param("keyword") String keyword);
    
    /**
     * 상담자정보구분 코드별 상담사 수 조회
     */
    int countByCounselorInfoClsfCd(@Param("counselorInfoClsfCd") String counselorInfoClsfCd);
    
    /**
     * 상담사 직원 ID로 상담사 수 조회
     */
    int countByCounselorEmpId(@Param("counselorEmpId") String counselorEmpId);
} 