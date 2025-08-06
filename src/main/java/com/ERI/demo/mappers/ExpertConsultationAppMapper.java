package com.ERI.demo.mappers;

import com.ERI.demo.vo.ExpertConsultationAppVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 전문가 상담 신청 Mapper
 */
@Mapper
public interface ExpertConsultationAppMapper {

    /**
     * 전문가 상담 신청 목록 조회 (페이징)
     */
    List<ExpertConsultationAppVO> selectExpertConsultationAppList(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("searchCondition") ExpertConsultationAppVO searchCondition,
        @Param("sortBy") String sortBy,
        @Param("sortDirection") String sortDirection
    );

    /**
     * 전문가 상담 신청 총 개수 조회
     */
    int selectExpertConsultationAppCount(@Param("searchCondition") ExpertConsultationAppVO searchCondition);

    /**
     * 전문가 상담 신청 상세 조회
     */
    ExpertConsultationAppVO selectExpertConsultationAppBySeq(@Param("appSeq") Long appSeq);

    /**
     * 전문가 상담 신청 등록
     */
    int insertExpertConsultationApp(ExpertConsultationAppVO expertConsultationApp);

    /**
     * 전문가 상담 신청 수정
     */
    int updateExpertConsultationApp(ExpertConsultationAppVO expertConsultationApp);

    /**
     * 전문가 상담 신청 삭제 (논리 삭제)
     */
    int deleteExpertConsultationApp(@Param("appSeq") Long appSeq, @Param("empId") String empId);

    /**
     * 내 전문가 상담 신청 목록 조회
     */
    List<ExpertConsultationAppVO> selectMyExpertConsultationAppList(
        @Param("empId") String empId,
        @Param("offset") int offset,
        @Param("size") int size
    );

    /**
     * 내 전문가 상담 신청 총 개수 조회
     */
    int selectMyExpertConsultationAppCount(@Param("empId") String empId);
} 