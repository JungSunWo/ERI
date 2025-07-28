package com.ERI.demo.mappers;

import com.ERI.demo.vo.ConsultationBoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담 게시판 Mapper
 */
@Mapper
public interface ConsultationBoardMapper {
    
    /**
     * 상담 게시글 목록 조회 (페이징)
     */
    List<ConsultationBoardVO> selectConsultationBoardList(@Param("offset") int offset, 
                                                          @Param("size") int size,
                                                          @Param("searchCondition") ConsultationBoardVO searchCondition,
                                                          @Param("sortBy") String sortBy,
                                                          @Param("sortDirection") String sortDirection);
    
    /**
     * 상담 게시글 총 개수 조회
     */
    int selectConsultationBoardCount(@Param("searchCondition") ConsultationBoardVO searchCondition);
    
    /**
     * 상담 게시글 상세 조회
     */
    ConsultationBoardVO selectConsultationBoardBySeq(@Param("seq") Long seq);
    
    /**
     * 상담 게시글 등록
     */
    int insertConsultationBoard(ConsultationBoardVO consultationBoard);
    
    /**
     * 상담 게시글 수정
     */
    int updateConsultationBoard(ConsultationBoardVO consultationBoard);
    
    /**
     * 상담 게시글 삭제 (논리 삭제)
     */
    int deleteConsultationBoard(@Param("seq") Long seq, @Param("empId") String empId);
    
    /**
     * 조회수 증가
     */
    int incrementViewCount(@Param("seq") Long seq);
    
    /**
     * 답변 상태 업데이트
     */
    int updateAnswerStatus(@Param("seq") Long seq, @Param("answerYn") String answerYn, 
                          @Param("answerEmpId") String answerEmpId);
    
    /**
     * 내가 작성한 상담 게시글 목록 조회
     */
    List<ConsultationBoardVO> selectMyConsultationBoardList(@Param("empId") String empId,
                                                            @Param("offset") int offset,
                                                            @Param("size") int size);
    
    /**
     * 내가 작성한 상담 게시글 총 개수 조회
     */
    int selectMyConsultationBoardCount(@Param("empId") String empId);
} 