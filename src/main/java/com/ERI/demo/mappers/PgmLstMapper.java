package com.ERI.demo.mappers;

import com.ERI.demo.vo.PgmLstVO;
import com.ERI.demo.dto.PageRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PgmLstMapper {
    
    /**
     * 프로그램 목록 조회 (페이징)
     */
    List<PgmLstVO> selectPgmLstListWithPaging(@Param("pgmLstVO") PgmLstVO pgmLstVO, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 프로그램 목록 수 조회
     */
    long selectPgmLstListCount(@Param("pgmLstVO") PgmLstVO pgmLstVO, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 프로그램 목록 조회 (페이징 없음)
     */
    List<PgmLstVO> selectPgmLstList(PgmLstVO pgmLstVO);
    
    /**
     * 프로그램 상세 조회
     */
    PgmLstVO selectPgmLst(Long seq);
    
    /**
     * 프로그램 등록
     */
    int insertPgmLst(PgmLstVO pgmLstVO);
    
    /**
     * 프로그램 수정
     */
    int updatePgmLst(PgmLstVO pgmLstVO);
    
    /**
     * 프로그램 삭제
     */
    int deletePgmLst(Long seq);
} 