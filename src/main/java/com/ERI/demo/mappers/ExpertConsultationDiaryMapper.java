package com.ERI.demo.mappers;

import com.ERI.demo.vo.ExpertConsultationDiaryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 전문가 상담 일지 Mapper
 */
@Mapper
public interface ExpertConsultationDiaryMapper {

    /**
     * 전문가 상담 일지 목록 조회
     */
    List<ExpertConsultationDiaryVO> selectExpertConsultationDiaryList(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("searchCondition") ExpertConsultationDiaryVO searchCondition
    );

    /**
     * 전문가 상담 일지 총 개수 조회
     */
    int selectExpertConsultationDiaryCount(@Param("searchCondition") ExpertConsultationDiaryVO searchCondition);

    /**
     * 전문가 상담 일지 상세 조회
     */
    ExpertConsultationDiaryVO selectExpertConsultationDiaryBySeq(@Param("drySeq") Long drySeq);

    /**
     * 신청별 전문가 상담 일지 조회
     */
    ExpertConsultationDiaryVO selectExpertConsultationDiaryByAppSeq(@Param("appSeq") Long appSeq);

    /**
     * 전문가 상담 일지 등록
     */
    int insertExpertConsultationDiary(ExpertConsultationDiaryVO expertConsultationDiary);

    /**
     * 전문가 상담 일지 수정
     */
    int updateExpertConsultationDiary(ExpertConsultationDiaryVO expertConsultationDiary);

    /**
     * 전문가 상담 일지 삭제 (논리 삭제)
     */
    int deleteExpertConsultationDiary(@Param("drySeq") Long drySeq, @Param("empId") String empId);
} 