package com.ERI.demo.mappers;

import com.ERI.demo.vo.ConsultationAnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담 답변 Mapper
 */
@Mapper
public interface ConsultationAnswerMapper {
    
    /**
     * 상담 답변 조회
     */
    ConsultationAnswerVO selectConsultationAnswerByBoardSeq(@Param("boardSeq") Long boardSeq);
    
    /**
     * 상담 답변 등록
     */
    int insertConsultationAnswer(ConsultationAnswerVO consultationAnswer);
    
    /**
     * 상담 답변 수정
     */
    int updateConsultationAnswer(ConsultationAnswerVO consultationAnswer);
    
    /**
     * 상담 답변 삭제 (논리 삭제)
     */
    int deleteConsultationAnswer(@Param("seq") Long seq, @Param("empId") String empId);
    
    /**
     * 상담 게시글별 답변 삭제 (논리 삭제)
     */
    int deleteConsultationAnswerByBoardSeq(@Param("boardSeq") Long boardSeq, @Param("empId") String empId);
} 