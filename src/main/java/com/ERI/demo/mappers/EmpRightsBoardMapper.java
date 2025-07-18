package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpRightsBoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원권익게시판 Mapper
 */
@Mapper
public interface EmpRightsBoardMapper {

    /**
     * 게시글 목록 조회 (페이징)
     */
    List<EmpRightsBoardVO> selectBoardList(EmpRightsBoardVO searchCondition);

    /**
     * 게시글 총 개수 조회
     */
    int selectBoardCount(EmpRightsBoardVO searchCondition);

    /**
     * 게시글 상세 조회
     */
    EmpRightsBoardVO selectBoardBySeq(@Param("seq") Long seq);

    /**
     * 게시글 등록
     */
    int insertBoard(EmpRightsBoardVO board);

    /**
     * 게시글 수정
     */
    int updateBoard(EmpRightsBoardVO board);

    /**
     * 게시글 삭제 (논리 삭제)
     */
    int deleteBoard(@Param("seq") Long seq, @Param("empId") String empId);

    /**
     * 조회수 증가
     */
    int incrementViewCount(@Param("seq") Long seq);

    /**
     * 좋아요 수 증가
     */
    int incrementLikeCount(@Param("seq") Long seq);

    /**
     * 좋아요 수 감소
     */
    int decrementLikeCount(@Param("seq") Long seq);

    /**
     * 싫어요 수 증가
     */
    int incrementDislikeCount(@Param("seq") Long seq);

    /**
     * 싫어요 수 감소
     */
    int decrementDislikeCount(@Param("seq") Long seq);

    /**
     * 공지글 목록 조회
     */
    List<EmpRightsBoardVO> selectNoticeList();

    /**
     * 내가 작성한 게시글 목록 조회
     */
    List<EmpRightsBoardVO> selectMyBoardList(EmpRightsBoardVO searchCondition);

    /**
     * 내가 작성한 게시글 총 개수 조회
     */
    int selectMyBoardCount(EmpRightsBoardVO searchCondition);
} 