package com.ERI.demo.mappers;

import com.ERI.demo.vo.ConsultationAssignmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담 배정 Mapper
 */
@Mapper
public interface ConsultationAssignmentMapper {

    /**
     * 상담 배정 목록 조회
     */
    List<ConsultationAssignmentVO> selectConsultationAssignmentList(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("searchCondition") ConsultationAssignmentVO searchCondition
    );

    /**
     * 상담 배정 총 개수 조회
     */
    int selectConsultationAssignmentCount(@Param("searchCondition") ConsultationAssignmentVO searchCondition);

    /**
     * 상담 배정 상세 조회
     */
    ConsultationAssignmentVO selectConsultationAssignmentBySeq(@Param("asgnSeq") Long asgnSeq);

    /**
     * 신청별 상담 배정 조회
     */
    ConsultationAssignmentVO selectConsultationAssignmentByAppSeq(@Param("appSeq") Long appSeq);

    /**
     * 상담사별 상담 배정 목록 조회
     */
    List<ConsultationAssignmentVO> selectConsultationAssignmentByCounselor(
        @Param("cnslrEmpId") String cnslrEmpId
    );

    /**
     * 상담 배정 등록
     */
    int insertConsultationAssignment(ConsultationAssignmentVO consultationAssignment);

    /**
     * 상담 배정 수정
     */
    int updateConsultationAssignment(ConsultationAssignmentVO consultationAssignment);

    /**
     * 상담 배정 삭제 (논리 삭제)
     */
    int deleteConsultationAssignment(@Param("asgnSeq") Long asgnSeq, @Param("empId") String empId);
} 