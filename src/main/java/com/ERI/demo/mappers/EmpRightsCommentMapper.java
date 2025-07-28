package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpRightsCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원권익게시판 댓글 Mapper
 */
@Mapper
public interface EmpRightsCommentMapper {

    /**
     * 댓글 목록 조회
     */
    List<EmpRightsCommentVO> selectCommentList(@Param("boardSeq") Long boardSeq);

    /**
     * 댓글 상세 조회
     */
    EmpRightsCommentVO selectCommentBySeq(@Param("seq") Long seq);

    /**
     * 하위 답글들 조회
     */
    List<EmpRightsCommentVO> selectChildComments(@Param("parentSeq") Long parentSeq);

    /**
     * 댓글 등록
     */
    int insertComment(EmpRightsCommentVO comment);

    /**
     * 댓글 수정
     */
    int updateComment(EmpRightsCommentVO comment);

    /**
     * 댓글 삭제 (논리 삭제)
     */
    int deleteComment(@Param("seq") Long seq, @Param("empId") String empId);

    /**
     * 하위 답글들 삭제 (논리 삭제)
     */
    int deleteChildComments(@Param("parentSeq") Long parentSeq, @Param("empId") String empId);

    /**
     * 댓글 좋아요 수 증가
     */
    int incrementCommentLikeCount(@Param("seq") Long seq);

    /**
     * 댓글 좋아요 수 감소
     */
    int decrementCommentLikeCount(@Param("seq") Long seq);

    /**
     * 댓글 싫어요 수 증가
     */
    int incrementCommentDislikeCount(@Param("seq") Long seq);

    /**
     * 댓글 싫어요 수 감소
     */
    int decrementCommentDislikeCount(@Param("seq") Long seq);
} 