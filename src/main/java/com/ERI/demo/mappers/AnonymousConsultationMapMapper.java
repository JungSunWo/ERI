package com.ERI.demo.mappers;

import com.ERI.demo.vo.AnonymousConsultationMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 익명 상담 연관 Mapper
 */
@Mapper
public interface AnonymousConsultationMapMapper {
    
    /**
     * 익명 상담 연관 등록
     */
    int insert(AnonymousConsultationMapVO map);
    
    /**
     * 익명 사용자의 상담 목록 조회
     */
    List<AnonymousConsultationMapVO> selectByAnonymousId(@Param("anonymousId") Long anonymousId);
    
    /**
     * 상담의 익명 사용자 정보 조회
     */
    AnonymousConsultationMapVO selectByCnslAppSeq(@Param("cnslAppSeq") Long cnslAppSeq);
    
    /**
     * 익명 상담 연관 삭제
     */
    int deleteByMapSeq(@Param("mapSeq") Long mapSeq);
    
    /**
     * 상담별 익명 연관 삭제
     */
    int deleteByCnslAppSeq(@Param("cnslAppSeq") Long cnslAppSeq);
} 