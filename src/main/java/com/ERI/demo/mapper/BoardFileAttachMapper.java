package com.ERI.demo.mapper;

import com.ERI.demo.vo.BoardFileAttachVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 게시판 파일 첨부 Mapper
 * TB_BRD_FILE_ATT 테이블과 매핑
 */
@Mapper
public interface BoardFileAttachMapper {
    
    /**
     * 파일 첨부 목록 조회 (페이징/검색)
     * @param boardFileAttach 조회 조건
     * @return 파일 첨부 목록
     */
    List<BoardFileAttachVO> selectBoardFileAttachList(BoardFileAttachVO boardFileAttach);
    
    /**
     * 파일 첨부 목록 개수 조회
     * @param boardFileAttach 조회 조건
     * @return 파일 첨부 목록 개수
     */
    int selectBoardFileAttachCount(BoardFileAttachVO boardFileAttach);
    
    /**
     * 파일 첨부 상세 조회
     * @param fileSeq 파일 시퀀스
     * @return 파일 첨부 정보
     */
    BoardFileAttachVO selectBoardFileAttachBySeq(@Param("fileSeq") Long fileSeq);
    
    /**
     * 게시글별 파일 첨부 목록 조회
     * @param brdSeq 게시글 시퀀스
     * @return 파일 첨부 목록
     */
    List<BoardFileAttachVO> selectBoardFileAttachByBrdSeq(@Param("brdSeq") Long brdSeq);
    
    /**
     * 파일 첨부 등록
     * @param boardFileAttach 파일 첨부 정보
     * @return 등록된 행 수
     */
    int insertBoardFileAttach(BoardFileAttachVO boardFileAttach);
    
    /**
     * 파일 첨부 수정
     * @param boardFileAttach 파일 첨부 정보
     * @return 수정된 행 수
     */
    int updateBoardFileAttach(BoardFileAttachVO boardFileAttach);
    
    /**
     * 파일 첨부 삭제 (논리 삭제)
     * @param fileSeq 파일 시퀀스
     * @return 삭제된 행 수
     */
    int deleteBoardFileAttach(@Param("fileSeq") Long fileSeq);
    
    /**
     * 게시글별 파일 첨부 삭제 (논리 삭제)
     * @param brdSeq 게시글 시퀀스
     * @return 삭제된 행 수
     */
    int deleteBoardFileAttachByBrdSeq(@Param("brdSeq") Long brdSeq);
    
    /**
     * 다운로드 횟수 증가
     * @param fileSeq 파일 시퀀스
     * @return 업데이트된 행 수
     */
    int incrementDownloadCount(@Param("fileSeq") Long fileSeq);
    
    /**
     * 이미지 링크 정보 업데이트
     * @param fileSeq 파일 시퀀스
     * @param imgLinks 이미지 링크 정보
     * @return 업데이트된 행 수
     */
    int updateImgLinks(@Param("fileSeq") Long fileSeq, @Param("imgLinks") String imgLinks);
} 