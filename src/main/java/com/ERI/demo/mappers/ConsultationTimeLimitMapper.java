package com.ERI.demo.mappers;

import com.ERI.demo.vo.ConsultationTimeLimitVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 상담 시간 제한 Mapper
 */
@Mapper
public interface ConsultationTimeLimitMapper {

    /**
     * 상담 시간 제한 목록 조회
     */
    List<ConsultationTimeLimitVO> selectConsultationTimeLimitList(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("searchCondition") ConsultationTimeLimitVO searchCondition
    );

    /**
     * 상담 시간 제한 총 개수 조회
     */
    int selectConsultationTimeLimitCount(@Param("searchCondition") ConsultationTimeLimitVO searchCondition);

    /**
     * 상담 시간 제한 상세 조회
     */
    ConsultationTimeLimitVO selectConsultationTimeLimitBySeq(@Param("limitSeq") Long limitSeq);

    /**
     * 특정 날짜/시간의 상담 시간 제한 조회
     */
    ConsultationTimeLimitVO selectConsultationTimeLimitByDateTime(
        @Param("cnslDt") LocalDate cnslDt,
        @Param("cnslTm") LocalTime cnslTm
    );

    /**
     * 상담 시간 제한 등록
     */
    int insertConsultationTimeLimit(ConsultationTimeLimitVO consultationTimeLimit);

    /**
     * 상담 시간 제한 수정
     */
    int updateConsultationTimeLimit(ConsultationTimeLimitVO consultationTimeLimit);

    /**
     * 상담 시간 제한 삭제 (논리 삭제)
     */
    int deleteConsultationTimeLimit(@Param("limitSeq") Long limitSeq, @Param("empId") String empId);

    /**
     * 현재 신청 수 증가
     */
    int incrementCurrentCount(@Param("limitSeq") Long limitSeq);

    /**
     * 현재 신청 수 감소
     */
    int decrementCurrentCount(@Param("limitSeq") Long limitSeq);
} 