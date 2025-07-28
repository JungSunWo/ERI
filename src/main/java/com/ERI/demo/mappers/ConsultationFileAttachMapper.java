package com.ERI.demo.mappers;

import com.ERI.demo.vo.ConsultationFileAttachVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담 파일 첨부 Mapper
 */
@Mapper
public interface ConsultationFileAttachMapper {
    
    /**
     * 상담 게시글별 파일 첨부 목록 조회
     */
    List<ConsultationFileAttachVO> selectConsultationFileAttachByBoardSeq(@Param("boardSeq") Long boardSeq);
    
    /**
     * 상담 답변별 파일 첨부 목록 조회
     */
    List<ConsultationFileAttachVO> selectConsultationFileAttachByAnswerSeq(@Param("answerSeq") Long answerSeq);
    
    /**
     * 파일 첨부 상세 조회
     */
    ConsultationFileAttachVO selectConsultationFileAttachBySeq(@Param("fileSeq") Long fileSeq);
    
    /**
     * 파일 첨부 등록
     */
    int insertConsultationFileAttach(ConsultationFileAttachVO consultationFileAttach);
    
    /**
     * 파일 첨부 삭제 (논리 삭제)
     */
    int deleteConsultationFileAttach(@Param("fileSeq") Long fileSeq, @Param("empId") String empId);
    
    /**
     * 상담 게시글별 파일 첨부 전체 삭제 (논리 삭제)
     */
    int deleteConsultationFileAttachByBoardSeq(@Param("boardSeq") Long boardSeq, @Param("empId") String empId);
    
    /**
     * 상담 답변별 파일 첨부 전체 삭제 (논리 삭제)
     */
    int deleteConsultationFileAttachByAnswerSeq(@Param("answerSeq") Long answerSeq, @Param("empId") String empId);
    
    /**
     * 다운로드 횟수 증가
     */
    int incrementDownloadCount(@Param("fileSeq") Long fileSeq);
} 